package Card;

import Card.Exceptions.NotValidCardBrand;

public abstract class Card {

    public static final double MIN_FEE = 0.3;
    public static final double MAX_FEE = 5.0;

    private final int cardNumber;
    private final String cardHolder;
    private final String cardBrand;
    private final String cardExpirationDate;

    public static Card CardConstructor(int cardNumber, String cardHolder, String cardBrand, String cardExpirationDate) {
        Card card = null;
        cardBrand = cardBrand.toUpperCase();
        if (cardBrand.equals("VISA")) {
            card = new VisaCard(cardNumber, cardHolder, cardBrand, cardExpirationDate);
        } else if (cardBrand.equals("NARA")) {
            card = new NaranjaCard(cardNumber, cardHolder, cardBrand, cardExpirationDate);
        } else if (cardBrand.equals("AMEX")) {
            card = new AmericanExpress(cardNumber, cardHolder, cardBrand, cardExpirationDate);
        } else {
            throw new NotValidCardBrand();
        }

        return card;
    }

    protected Card(int cardNumber, String cardHolder, String cardBrand, String cardExpirationDate) {
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
        this.cardBrand = cardBrand;
        this.cardExpirationDate = cardExpirationDate;
    }

    protected double serviceFeeLimit(double fee){
        return Math.min(Math.max(fee,MIN_FEE),MAX_FEE);
    }
    public abstract double serviceFee();

    public boolean validOperation(int operationAmount) {
        return true;
    }

    public int operation() {
        return cardNumber;
    }
    
}
