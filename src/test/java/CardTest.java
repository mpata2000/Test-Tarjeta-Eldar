import Card.Card;
import org.junit.jupiter.api.Test;


import java.time.Year;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class CardTest {

    @Test
    public void CardCreate(){
        Card card = Card.CardConstructor(5,"Martin","AMEX","7/22");
        assertNotNull(card);
    }

    @Test
    public void AmexServiceFee(){
        Card card = Card.CardConstructor(5,"Martin","AMEX","9/22");
        float month = Calendar.getInstance().get(Calendar.MONTH) + 1; // Calendar month is 0 base (january == 0)

        assertEquals(month*01,card.serviceFee());
    }

    @Test
    public void VisaServiceFee(){
        Card card = Card.CardConstructor(5,"Martin","VISA","9/22");
        Calendar cal = Calendar.getInstance();
        float year = cal.get(Calendar.YEAR);
        float month = cal.get(Calendar.MONTH) + 1; // Calendar month is 0 base (january == 0)

        assertEquals(month/(year-2000),card.serviceFee()); // Deberia cambiarlo a algo que corte el numero
    }

    @Test
    public void NaraServiceFee(){
        Card card = Card.CardConstructor(5,"Martin","NARA","9/22");
        float dayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        assertEquals(dayOfMonth*05,card.serviceFee());
    }
}
