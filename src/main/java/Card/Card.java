package Card;

public abstract class Card {
    private final int cardNumber;
    private final String cardHolder;
    private final String cardBrand;
    private final String cardExpirationDate;

    public static Card CardConstructor(int cardNumber, String cardHolder, String cardBrand, String cardExpirationDate) {
        Card card = null;

        if (cardBrand.equals("VISA")) {
            card = new VisaCard(cardNumber, cardHolder, cardBrand, cardExpirationDate);
        } else if (cardBrand.equals("NARA")) {
            card = new NaranjaCard(cardNumber, cardHolder, cardBrand, cardExpirationDate);
        } else if (cardBrand.equals("AMEX")) {
            card = new AmericanExpress(cardNumber, cardHolder, cardBrand, cardExpirationDate);
        } else {
            System.out.println("Invalid card brand");
        }

        return card;
    }

    private Card(int cardNumber, String cardHolder, String cardBrand, String cardExpirationDate) {
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
        this.cardBrand = cardBrand;
        this.cardExpirationDate = cardExpirationDate;
    }

    public abstract int serviceFee();

    public boolean validOperation(int operationAmount) {
        return true;
    }

    public int operation() {
        return cardNumber;
    }
    
}
