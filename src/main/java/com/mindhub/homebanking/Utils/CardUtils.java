package com.mindhub.homebanking.Utils;

public final class CardUtils {

    private CardUtils() {
    }
    public static String getCardNumber() {
        String cardNumber;
        cardNumber = Integer.toString(getRandomNumber(1000, 9999));
        cardNumber += " " + getRandomNumber(1000, 9999);
        cardNumber += " " + getRandomNumber(1000, 9999);
        cardNumber += " " + getRandomNumber(1000, 9999);
        return cardNumber;
    }


    public static int getCardCvv() {//static es un metodo que no necesita de una instancia de la clase para poder ser llamado
        return getRandomNumber(100, 999);
    }
    public static int getRandomNumber (int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

}
