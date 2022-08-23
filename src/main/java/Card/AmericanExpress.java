package Card;

public class AmericanExpress extends Card {
    public AmericanExpress(int cardNumber, String cardHolder, String cardBrand, String cardExpirationDate) {
        super(cardNumber, cardHolder, cardBrand, cardExpirationDate);
    }

    @Override
    public float serviceFee() {
        return 0;
    }


}
