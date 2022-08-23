package Card;

import java.util.Calendar;

public class VisaCard extends Card {
    public VisaCard(int cardNumber, String cardHolder, String cardBrand, String cardExpirationDate) {
        super(cardNumber, cardHolder, cardBrand, cardExpirationDate);
    }

    @Override
    public float serviceFee() {
        Calendar cal = Calendar.getInstance();
        float year = cal.get(Calendar.YEAR);
        float month = cal.get(Calendar.MONTH) + 1; // Calendar month is 0 base (january == 0)

        return month/(year-2000);
    }

}
