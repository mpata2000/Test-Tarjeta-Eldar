import Card.Card;
import Card.AmericanExpress;
import Card.Exceptions.NotValidCardBrand;
import Card.VisaCard;
import Card.NaranjaCard;
import org.junit.jupiter.api.Test;


import java.time.Year;
import java.util.Calendar;

import static Card.Card.MAX_FEE;
import static Card.Card.MIN_FEE;
import static org.junit.jupiter.api.Assertions.*;


public class CardTest {

    @Test
    public void CardCreateWithValidValuesCreatesACard(){
        Card card = Card.CardConstructor(5,"Martin","AMEX","7/22");
        assertNotNull(card);
    }

    @Test
    public void AmexCardIsCreatedCorrectly(){
        Card card = Card.CardConstructor(5,"Martin","AMEX","7/22");
        assertNotNull(card);
        assertTrue(card instanceof AmericanExpress);
    }

    @Test
    public void VisaCardIsCreatedCorrectly(){
        Card card = Card.CardConstructor(5,"Martin","VISA","7/22");
        assertNotNull(card);
        assertTrue(card instanceof VisaCard);
    }

    @Test
    public void CardThowsNotValidBrandIfBRandIsntValid(){
        assertThrows(NotValidCardBrand.class, ()->  Card.CardConstructor(5,"Martin","F1","7/22"));
    }

    @Test
    public void NarajaCardIsCreatedCorrectly(){
        Card card = Card.CardConstructor(5,"Martin","NARA","7/22");
        assertNotNull(card);
        assertTrue(card instanceof NaranjaCard);
    }


    @Test
    public void AmexServiceFee(){
        Card card = Card.CardConstructor(5,"Martin","AMEX","9/22");
        double month = Calendar.getInstance().get(Calendar.MONTH) + 1; // Calendar month is 0 base (january == 0)

        assertEquals(Math.min(Math.max(month*0.1,MIN_FEE),MAX_FEE),card.serviceFee());
        assertTrue(0.3 <= card.serviceFee() && card.serviceFee() <= 5.0);
    }

    @Test
    public void VisaServiceFee(){
        Card card = Card.CardConstructor(5,"Martin","VISA","9/22");
        Calendar cal = Calendar.getInstance();
        double year = cal.get(Calendar.YEAR);
        double month = cal.get(Calendar.MONTH) + 1; // Calendar month is 0 base (january == 0)

        assertEquals(Math.min(Math.max(month/(year-2000),MIN_FEE),MAX_FEE),card.serviceFee()); // Deberia cambiarlo a algo que corte el numero
        assertTrue(0.3 <= card.serviceFee() && card.serviceFee() <= 5.0);
    }

    @Test
    public void NaraServiceFee(){
        Card card = Card.CardConstructor(5,"Martin","NARA","9/22");
        double dayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        assertEquals(Math.min(Math.max(dayOfMonth*0.5,MIN_FEE),MAX_FEE),card.serviceFee());
        assertTrue(0.3 <= card.serviceFee() && card.serviceFee() <= 5.0);
    }
}
