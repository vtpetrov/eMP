package emp.codemonster.models.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VoidDetails {

    @SerializedName("reference_id")
    @Expose
    public String referenceId;
    @SerializedName("transaction_type")
    @Expose
    public String transactionType;

    /**
     * No args constructor for use in serialization
     *
     */
    public VoidDetails() {
        this.transactionType = "void";
    }

    public VoidDetails(String referenceId, String transactionType) {
        super();
        this.referenceId = referenceId;
        this.transactionType = transactionType;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
}
