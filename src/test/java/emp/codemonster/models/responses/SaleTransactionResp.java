package emp.codemonster.models.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SaleTransactionResp {

//    {
//        "unique_id": "c00ca6e1a4264a78de12ff961e55adb8",
//            "status": "approved",
//            "usage": "Coffeemaker",
//            "amount": 500,
//            "transaction_time": "2020-03-28T20:31:56.572Z",
//            "message": "Your transaction has been approved."
//    }

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
     *
     */
    public SaleTransactionResp() {
    }

    /**
     *
     * @param amount
     * @param usage
     * @param transactionTime
     * @param message
     * @param uniqueId
     * @param status
     */
    public SaleTransactionResp(String uniqueId, String status, String usage, Integer amount, String transactionTime, String message) {
        super();
        this.uniqueId = uniqueId;
        this.status = status;
        this.usage = usage;
        this.amount = amount;
        this.transactionTime = transactionTime;
        this.message = message;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "SaleTransactionResp{" +
                "uniqueId='" + uniqueId + '\'' +
                ", status='" + status + '\'' +
                ", usage='" + usage + '\'' +
                ", amount=" + amount +
                ", transactionTime='" + transactionTime + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}