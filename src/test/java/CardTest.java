import card.Card;
import card.AmericanExpress;
import card.Exceptions.NotValidCardBrand;
import card.VisaCard;
import card.NaranjaCard;
import card.exceptions.CardExpiredException;
import card.exceptions.InvalidOperationException;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import com.google.gson.JsonObject;


import java.util.Calendar;

import static card.Card.MAX_FEE;
import static card.Card.MIN_FEE;
import static org.junit.jupiter.api.Assertions.*;


public class CardTest {

    @Test
    public void CardCreateWithValidValuesCreatesACard(){
        Card card = Card.CardConstructor(5,"Martin","AMEX","07/22");
        assertNotNull(card);
    }

    @Test
    public void CardNumberCanNotBeZero(){
        assertThrows(IllegalArgumentException.class, ()->  Card.CardConstructor(0,"Martin","F1","07/22"));
    }

    @Test
    public void CardNumberCanNotBeNegative(){
        assertThrows(IllegalArgumentException.class, ()->  Card.CardConstructor(-10,"Martin","AMEX","07/22"));
    }

    @Test
    public void CardHolderCanNotBeNull(){
        assertThrows(IllegalArgumentException.class, ()->  Card.CardConstructor(10,null,"AMEX","07/22"));
    }

    @Test
    public void CardHolderCanNotBeBlank(){
        assertThrows(IllegalArgumentException.class, ()->  Card.CardConstructor(10,"","AMEX","07/22"));
        assertThrows(IllegalArgumentException.class, ()->  Card.CardConstructor(10,"   ","AMEX","07/22"));
    }

    @Test
    public void CardBrandCanNotBeNull(){
        assertThrows(IllegalArgumentException.class, ()->  Card.CardConstructor(10,"x",null,"07/22"));
    }

    @Test
    public void CardBrandCanNotBeBlank(){
        assertThrows(IllegalArgumentException.class, ()->  Card.CardConstructor(10,"x","","07/22"));
        assertThrows(IllegalArgumentException.class, ()->  Card.CardConstructor(10,"x","  ","07/22"));
    }

    @Test
    public void ExperitionDateMustBeMonthYear(){
        assertThrows(IllegalArgumentException.class, ()->  Card.CardConstructor(10,"x","AMEX","7/22"));
        assertThrows(IllegalArgumentException.class, ()->  Card.CardConstructor(10,"x","AMEX",""));
        assertThrows(IllegalArgumentException.class, ()->  Card.CardConstructor(10,"x","AMEX","07/224"));
        assertThrows(IllegalArgumentException.class, ()->  Card.CardConstructor(10,"x","AMEX","11/07/24"));
    }

    @Test
    public void ExperitionDateMustBeValid(){
        assertThrows(IllegalArgumentException.class, ()->  Card.CardConstructor(10,"x","AMEX","13/22"));
        assertThrows(IllegalArgumentException.class, ()->  Card.CardConstructor(10,"x","AMEX","00/22"));
        assertThrows(IllegalArgumentException.class, ()->  Card.CardConstructor(10,"x","AMEX","-02/22"));
    }

    @Test
    public void AmexCardIsCreatedCorrectly(){
        Card card = Card.CardConstructor(5,"Martin","AMEX","07/22");
        assertNotNull(card);
        assertTrue(card instanceof AmericanExpress);
    }

    @Test
    public void VisaCardIsCreatedCorrectly(){
        Card card = Card.CardConstructor(5,"Martin","VISA","07/22");
        assertNotNull(card);
        assertTrue(card instanceof VisaCard);
    }

    @Test
    public void CardThowsNotValidBrandIfBRandIsntValid(){
        assertThrows(NotValidCardBrand.class, ()->  Card.CardConstructor(5,"Martin","F1","07/22"));
    }

    @Test
    public void NarajaCardIsCreatedCorrectly(){
        Card card = Card.CardConstructor(5,"Martin","NARA","07/22");
        assertNotNull(card);
        assertTrue(card instanceof NaranjaCard);
    }

    @Test
    public void CardInformationIsReadableAndCorrect(){
        int number = 5;
        String cardHolder = "Martin";
        String cardBrand = "NARA";
        String exp = "07/22";

        Card card = Card.CardConstructor(number,cardHolder,cardBrand,exp);

        JsonObject jsonObject = new Gson().fromJson(card.toJSON(), JsonObject.class);

        assertEquals(number, jsonObject.get("cardNumber").getAsInt());
        assertEquals(cardHolder, jsonObject.get("cardHolder").getAsString());
        assertEquals(cardBrand, jsonObject.get("cardBrand").getAsString());
        assertEquals(exp, jsonObject.get("cardExpirationDate").getAsString());
    }


    @Test
    public void AmexServiceFee(){
        Card card = Card.CardConstructor(5,"Martin","AMEX","09/22");
        double month = Calendar.getInstance().get(Calendar.MONTH) + 1; // Calendar month is 0 base (january == 0)

        assertEquals(Math.min(Math.max(month*0.1,MIN_FEE),MAX_FEE),card.serviceFee());
        assertTrue(0.3 <= card.serviceFee() && card.serviceFee() <= 5.0);
    }

    @Test
    public void VisaServiceFee(){
        Card card = Card.CardConstructor(5,"Martin","VISA","09/22");
        Calendar cal = Calendar.getInstance();
        double year = cal.get(Calendar.YEAR);
        double month = cal.get(Calendar.MONTH) + 1; // Calendar month is 0 base (january == 0)

        assertEquals(Math.min(Math.max(month/(year-2000),MIN_FEE),MAX_FEE),card.serviceFee()); // Deberia cambiarlo a algo que corte el numero
        assertTrue(0.3 <= card.serviceFee() && card.serviceFee() <= 5.0);
    }

    @Test
    public void NaraServiceFee(){
        Card card = Card.CardConstructor(5,"Martin","NARA","09/22");
        double dayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        assertEquals(Math.min(Math.max(dayOfMonth*0.5,MIN_FEE),MAX_FEE),card.serviceFee());
        assertTrue(0.3 <= card.serviceFee() && card.serviceFee() <= 5.0);
    }

    @Test
    public void CardWithEqualValuesAreEqual(){
        Card card1 = Card.CardConstructor(5,"Martin","NARA","09/22");
        Card card2 = Card.CardConstructor(5,"Martin","NARA","09/22");
        assertEquals(card1,card2);
    }

    @Test
    public void CardWithDifferentValuesAreNotEqual(){
        Card card1 = Card.CardConstructor(5,"Martin","NARA","09/22");
        Card card2 = Card.CardConstructor(45,"Martin Pata","AMEX","09/23");
        assertNotEquals(card1,card2);
    }

    @Test
    public void CardIsEqualToItself(){
        Card card = Card.CardConstructor(5,"Martin","NARA","09/22");
        assertEquals(card,card);
    }

    @Test
    public void ExpiredCardCanNotOperate(){
        Card card = Card.CardConstructor(5,"Martin","NARA","06/22");
        assertTrue(card.isExpired());
        assertFalse(card.validOperation(100));

        Card cardExpYear = Card.CardConstructor(5,"Martin","NARA","09/12");
        assertTrue(cardExpYear.isExpired());
        assertFalse(cardExpYear.validOperation(100));
    }

    @Test
    public void ValidCardCanOperate100(){
        Card card = Card.CardConstructor(5,"Martin","NARA","09/22");
        assertFalse(card.isExpired());
        assertTrue(card.validOperation(100));
    }

    @Test
    public void ValidCardCantOperate1000(){
        Card card = Card.CardConstructor(5,"Martin","NARA","09/22");
        assertFalse(card.isExpired());
        assertFalse(card.validOperation(1000));
    }

    @Test
    public void ValidOperationReturnJSONWithInformation(){

        Card card = Card.CardConstructor(5,"Martin","NARA","09/22");
        JsonObject jsonObject = new Gson().fromJson(card.operation(100), JsonObject.class);

        assertEquals("NARA", jsonObject.get("cardBrand").getAsString());
        assertEquals(card.serviceFee(), jsonObject.get("serviceFee").getAsDouble());
        assertEquals(100, jsonObject.get("operationAmount").getAsInt());
    }

    @Test
    public void TryingToOperateWithExperiedCardThrowsException(){
        Card card = Card.CardConstructor(5,"Martin","NARA","09/19");
        assertThrows(InvalidOperationException.class, ()-> card.operation(1000));
    }

    @Test
    public void NotExperiedCardCanOperate(){
        Card card = Card.CardConstructor(5,"Martin","NARA","09/22");
        assertFalse(card.isExpired());
        assertTrue(card.canOperate());
    }

    @Test
    public void ExperiedCardCanNotOperate(){
        Card card = Card.CardConstructor(5,"Martin","NARA","09/12");
        assertTrue(card.isExpired());
        assertFalse(card.canOperate());
    }
}
