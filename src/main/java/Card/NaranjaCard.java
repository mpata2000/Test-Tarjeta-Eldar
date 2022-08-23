package Card;

import java.util.Calendar;

public class NaranjaCard extends Card {
    public NaranjaCard(int cardNumber, String cardHolder, String cardBrand, String cardExpirationDate) {
        super(cardNumber, cardHolder, cardBrand, cardExpirationDate);
    }

    @Override
    public float serviceFee() {
        float dayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        return dayOfMonth*05;
    }
}
