package emp.codemonster.models.requests;

import com.github.javafaker.Faker;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import emp.codemonster.common.Utils;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Locale;

public class SaleDetails {

    private static Faker faker = new Faker(Locale.ENGLISH);

    @SerializedName("card_number")
    @Expose
    public String cardNumber;
    @SerializedName("cvv")
    @Expose
    public String cvv;
    @SerializedName("expiration_date")
    @Expose
    public String expirationDate;
    @SerializedName("amount")
    @Expose
    public String amount;
    @SerializedName("usage")
    @Expose
    public String usage;
    @SerializedName("transaction_type")
    @Expose
    public String transactionType;
    @SerializedName("card_holder")
    @Expose
    public String cardHolder;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("address")
    @Expose
    public String address;

    /**
     * No args constructor for use in serialization
     *
     */
    public SaleDetails() {
        this.cardNumber = "4200000000000000";
        this.transactionType = "sale";
    }

    /**
     *
     * @param transactionType
     * @param cvv
     * @param amount
     * @param address
     * @param usage
     * @param cardHolder
     * @param cardNumber
     * @param email
     * @param expirationDate
     */
    public SaleDetails(String cardNumber, String cvv, String expirationDate, String amount, String usage, String transactionType, String cardHolder, String email, String address) {
        super();
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.expirationDate = expirationDate;
        this.amount = amount;
        this.usage = usage;
        this.transactionType = transactionType;
        this.cardHolder = cardHolder;
        this.email = email;
        this.address = address;
    }

    public static SaleDetails generateRandomSaleDetails(){

        SaleDetails details = new SaleDetails();

//            faker.finance().creditCard();
        details.setCvv(RandomStringUtils.randomNumeric(3));
        details.setExpirationDate(Utils.getExpirationDate(faker.number().numberBetween(1,13), faker.number().numberBetween(2020,2025)));
        details.setAmount(String.valueOf(faker.number().numberBetween(100, 500)));
//        details.setUsage(faker.lordOfTheRings().character());
        details.setUsage(faker.company().industry());
        details.setCardHolder(faker.name().fullName());
        details.setEmail(faker.internet().emailAddress());
        details.setAddress(faker.address().streetAddress());

        return details;
    }

//        Getters:
    public String getCardNumber() {
        return cardNumber;
    }

    public String getCvv() {
        return cvv;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public String getAmount() {
        return amount;
    }

    public String getUsage() {
        return usage;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

//        Setters:

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
