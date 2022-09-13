package com.mindhub.homebanking.Services;

import com.mindhub.homebanking.Models.Card;

import java.util.List;

public interface CardServices {

    public List<Card> getCards();

    public Card getCardById(long id);
    public void saveCard(Card card);

    public Card findById(long id);
    public Card findByNumber(String number);



}
