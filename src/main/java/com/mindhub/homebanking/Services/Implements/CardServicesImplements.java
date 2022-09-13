package com.mindhub.homebanking.Services.Implements;

import com.mindhub.homebanking.Models.Card;
import com.mindhub.homebanking.Services.CardServices;
import com.mindhub.homebanking.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardServicesImplements implements CardServices {

    @Autowired
    private CardRepository cardRepository;

    @Override
    public List<Card> getCards() {
        return cardRepository.findAll();
    }

    @Override
    public Card getCardById(long id) {
        return cardRepository.findById(id);
    }

    @Override
    public void saveCard(Card card) {
        cardRepository.save(card);
    }

    @Override
    public Card findById(long id) {
        return cardRepository.findById(id);
    }
    @Override
    public Card findByNumber(String number) {
        return cardRepository.findByNumber(number);
    }

}
