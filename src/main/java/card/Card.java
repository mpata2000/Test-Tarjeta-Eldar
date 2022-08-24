package card;

import card.Exceptions.NotValidCardBrand;
import card.exceptions.CardExpiredException;
import card.exceptions.InvalidOperationException;
import com.google.gson.Gson;
import org.json.simple.JSONObject;
import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

public abstract class Card {

    public static final double MIN_FEE = 0.3;
    public static final double MAX_FEE = 5.0;

    private final int cardNumber;
    private final String cardHolder;
    private final String cardBrand;
    private final String cardExpirationDate;

    public static Card CardConstructor(int cardNumber,@NotNull String cardHolder,@NotNull String cardBrand,@NotNull String cardExpirationDate) {
        Card card = null;
        cardBrand = cardBrand.toUpperCase();

        // Card number validation
        if (cardNumber <= 0) {
            throw new IllegalArgumentException("Card number must be positive");
        }

        //No null or empty strings
        if (cardHolder == null || cardHolder.isBlank() || cardBrand == null || cardBrand.isBlank()) {
            throw new IllegalArgumentException("Card holder, card brand and card expiration date must not be null or empty");
        }

        // Validate car expiration date
        if (!cardExpirationDate.matches("^(0[1-9]|1[0-2])[/]([0-9]{2})$")) {
            throw new IllegalArgumentException("Card expiration date must be in the format MM/YY");
        }
        
        card = switch (cardBrand) {
            case "VISA" -> new VisaCard(cardNumber, cardHolder, cardBrand, cardExpirationDate);
            case "NARA" -> new NaranjaCard(cardNumber, cardHolder, cardBrand, cardExpirationDate);
            case "AMEX" -> new AmericanExpress(cardNumber, cardHolder, cardBrand, cardExpirationDate);
            default -> throw new NotValidCardBrand();
        };

        return card;
    }

    protected Card(int cardNumber, String cardHolder, String cardBrand, String cardExpirationDate) {
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
        this.cardBrand = cardBrand;
        this.cardExpirationDate = cardExpirationDate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return cardNumber == card.cardNumber &&
                cardHolder.equals(card.cardHolder) &&
                cardBrand.equals(card.cardBrand) &&
                cardExpirationDate.equals(card.cardExpirationDate);
    }

    protected double serviceFeeLimit(double fee){
        return Math.min(Math.max(fee,MIN_FEE),MAX_FEE);
    }
    public abstract double serviceFee();

    public String toJSON(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public boolean isExpired() {
        String[] date = cardExpirationDate.split("/");
        int month = Integer.parseInt(date[0]);
        int year = Integer.parseInt(date[1]);
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1; // Calendar month is 0 base (january == 0)
        int currentYear = Calendar.getInstance().get(Calendar.YEAR) % 100;
        
        return currentYear > year || (currentYear == year && currentMonth > month);
   
    }

    public boolean canOperate(){
        return !this.isExpired();
    }

    public boolean validOperation(int operationAmount) {
        return !this.isExpired() && operationAmount < 1000;
    }

    public String operation(int operationAmount) {
        // Validate operation amount
        if(!this.validOperation(operationAmount)){
            throw new InvalidOperationException();
        }

        JSONObject json = new JSONObject();
        json.put("cardBrand", this.cardBrand);
        json.put("serviceFee", this.serviceFee());
        json.put("operationAmount", operationAmount);
        return json.toJSONString();
    }
}
