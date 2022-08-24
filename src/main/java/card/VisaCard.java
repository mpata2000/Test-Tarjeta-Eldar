package card;

import java.util.Calendar;

public class VisaCard extends Card {
    public VisaCard(int cardNumber, String cardHolder, String cardBrand, String cardExpirationDate) {
        super(cardNumber, cardHolder, cardBrand, cardExpirationDate);
    }

    @Override
    public double serviceFee() {
        Calendar cal = Calendar.getInstance();
        double year = cal.get(Calendar.YEAR);
        double month = cal.get(Calendar.MONTH) + 1; // Calendar month is 0 base (january == 0)

        return  serviceFeeLimit(month/(year%100));
    }

}
