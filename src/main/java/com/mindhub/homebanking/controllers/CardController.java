package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.Models.Card;
import com.mindhub.homebanking.Models.CardColor;
import com.mindhub.homebanking.Models.CardType;
import com.mindhub.homebanking.Models.Client;
import com.mindhub.homebanking.Services.CardServices;
import com.mindhub.homebanking.Services.ClientServices;
import com.mindhub.homebanking.Utils.CardUtils;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
public class CardController {

    @Autowired
    private ClientServices clientServices;

    @Autowired
    private CardServices cardServices;


    @PostMapping("/api/clients/current/cards")
    public ResponseEntity<Object> createNewCard(Authentication authentication, @RequestParam CardType cardType, @RequestParam CardColor cardColor) {

        Client clientCard = clientServices.findByEmail(authentication.getName());
        int cardCvv = CardUtils.getCardCvv();
        String cardNumber = CardUtils.getCardNumber();

        //clientCard.getCards().stream().anyMatch(card -> card.getCardColor().equals(cardColor) && card.getCardType().equals(cardType)) and card could be active true
        if (clientCard.getCards().stream().anyMatch(card -> card.getCardColor().equals(cardColor) && card.getCardType().equals(cardType) && card.isActive())) {
            return new ResponseEntity<>("You already have a card of this type and color", HttpStatus.BAD_REQUEST);
        }

//        if (clientCard.getCards().stream().anyMatch(card -> card.getCardColor().equals(cardColor) && card.getCardType().equals(cardType))  ) {
//            return new ResponseEntity<>("You already have a card with this color and type", HttpStatus.FORBIDDEN);
//        }

        if (cardServices.getCards().stream().anyMatch(card -> card.getNumber().equals(cardNumber))) {
            return new ResponseEntity<>("This card number already exists", HttpStatus.FORBIDDEN);
        }

        if (clientCard.getCards().stream().filter(card -> card.getCardType().equals(cardType) && card.isActive()).count() >= 3  ) {
            return new ResponseEntity<>("Too much card poor-boy", HttpStatus.FORBIDDEN);
        }

        cardServices.saveCard(new Card(clientCard, clientCard.toString(), cardNumber, cardCvv, LocalDateTime.now(), LocalDateTime.now().plusYears(5), cardType, cardColor, true));
        return new ResponseEntity<>("Card created", HttpStatus.CREATED);
    }


    @PatchMapping("/api/clients/current/cards/state")
    public ResponseEntity<Object> updateCardState(Authentication authentication, @RequestParam String number) {
        Client clientCard = clientServices.findByEmail(authentication.getName());
        Card card = cardServices.findByNumber(number);

        if (card == null) {
            return new ResponseEntity<>("Card not found", HttpStatus.NOT_FOUND);
        }
        if(clientCard == null){
            return new ResponseEntity<>("Client not found", HttpStatus.NOT_FOUND);
        }
        if (number.isEmpty()){
            return new ResponseEntity<>("Card number is empty", HttpStatus.BAD_REQUEST);
        }
        if (!clientCard.getCards().contains(card)) {
            return new ResponseEntity<>("You can't update this card", HttpStatus.FORBIDDEN);
        }
        card.setActive(false);
        cardServices.saveCard(card);
        return new ResponseEntity<>("Card updated", HttpStatus.OK);
    }

}
