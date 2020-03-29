package emp.codemonster.models.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static org.junit.Assert.assertEquals;

public class TransactionResponse {
    public static final String STATUS_APPROVED = "approved";
    public static final String STATUS_DECLINED = "declined";
    public static final String SALE_MESSAGE_APPROVED = "Your transaction has been approved.";
    public static final String SALE_MESSAGE_DECLINED = "Your transaction has been declined.";
    public static final String VOID_MESSAGE_APPROVED = "Your transaction has been voided successfully";

    @SerializedName("unique_id")
    @Expose
    private String uniqueId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("usage")
    @Expose
    private String usage;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("transaction_time")
    @Expose
    private String transactionTime;
    @SerializedName("message")
    @Expose
    private String message;

    /**
     * No args constructor for use in serialization
     */
    public TransactionResponse() {
    }

    public TransactionResponse(String uniqueId, String status, String usage, Integer amount, String transactionTime, String message) {
        super();
        this.uniqueId = uniqueId;
        this.status = status;
        this.usage = usage;
        this.amount = amount;
        this.transactionTime = transactionTime;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public String getUsage() {
        return usage;
    }

    public Integer getAmount() {
        return amount;
    }

    public String getTransactionTime() {
        return transactionTime;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "TransactionResponse to String: \n{" +
                "uniqueId='" + uniqueId + '\'' +
                ", status='" + status + '\'' +
                ", usage='" + usage + '\'' +
                ", amount=" + amount +
                ", transactionTime='" + transactionTime + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    /**
     * Verifies the "status" property value in the JSON response.
     * @param expectedStatus the expected value for "status"
     */
    public void verifyStatus(String expectedStatus) {
        assertEquals("\"status\" value is not as expected",
                expectedStatus,
                this.getStatus());
    }

    /**
     * Verifies the "message" property value in the JSON response.
     * @param expectedMessage the expected value for "message"
     */
    public void verifyMessage(String expectedMessage) {
        assertEquals("\"message\" value is not as expected",
                expectedMessage,
                this.getMessage());
    }

    /**
     * Verifies the "usage" property value in the JSON response.
     * @param expectedUsage the expected value for "usage"
     */
    public void verifyUsage(String expectedUsage) {
        assertEquals("\"usage\" value is not as expected",
                expectedUsage,
                this.getUsage());
    }

    /**
     * Verifies the "amount" property value in the JSON response.
     * @param expectedAmount the expected value for "amount"
     */
    public void verifyAmount(String expectedAmount) {
        assertEquals("\"amount\" value is not as expected",
                expectedAmount,
                String.valueOf(this.getAmount()));
    }
}