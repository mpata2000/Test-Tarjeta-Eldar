import Card.Card;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class CardTest {

    @Test
    public void CardCreate(){
        Card card = Card.CardConstructor(5,"Martin","AMEX","7/22");
        assertNotNull(card);
    }
}
