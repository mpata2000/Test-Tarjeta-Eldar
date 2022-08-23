package Card;

public class NaranjaCard extends Card {
    public NaranjaCard(int cardNumber, String cardHolder, String cardBrand, String cardExpirationDate) {
        super(cardNumber, cardHolder, cardBrand, cardExpirationDate);
    }

    @Override
    public float serviceFee() {
        return 0;
    }
}
