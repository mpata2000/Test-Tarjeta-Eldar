package Card;

import java.util.Calendar;

public class AmericanExpress extends Card {
    public AmericanExpress(int cardNumber, String cardHolder, String cardBrand, String cardExpirationDate) {
        super(cardNumber, cardHolder, cardBrand, cardExpirationDate);
    }

    @Override
    public float serviceFee() {
        float month = Calendar.getInstance().get(Calendar.MONTH) + 1; // Calendar month is 0 base (january == 0)

        return month*01;
    }


}
