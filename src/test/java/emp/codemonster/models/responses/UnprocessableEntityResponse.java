package emp.codemonster.models.responses;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UnprocessableEntityResponse {

    @SerializedName("amount")
    @Expose
    public List<String> amount = null;
    @SerializedName("email")
    @Expose
    public List<String> email = null;
    @SerializedName("card_number")
    @Expose
    public List<String> cardNumber = null;
    @SerializedName("cvv")
    @Expose
    public List<String> cvv = null;
    @SerializedName("transaction_type")
    @Expose
    public List<String> transactionType = null;
    @SerializedName("reference_id")
    @Expose
    public List<String> referenceId = null;

    /**
     * No args constructor for use in serialization
     */
    public UnprocessableEntityResponse() {
    }

    public UnprocessableEntityResponse(List<String> amount, List<String> email, List<String> cardNumber, List<String> cvv, List<String> transactionType, List<String> referenceId) {
        super();
        this.amount = amount;
        this.email = email;
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.transactionType = transactionType;
        this.referenceId = referenceId;
    }

}
