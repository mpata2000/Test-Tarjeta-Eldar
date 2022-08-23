package Card;

public class VisaCard extends Card {
    public VisaCard(int cardNumber, String cardHolder, String cardBrand, String cardExpirationDate) {
        super(cardNumber, cardHolder, cardBrand, cardExpirationDate);
    }

    @Override
    public int serviceFee() {
        return 0;
    }

}
