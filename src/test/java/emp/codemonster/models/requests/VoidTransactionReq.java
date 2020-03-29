package emp.codemonster.models.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VoidTransactionReq {

    @SerializedName("payment_transaction")
    @Expose
    public VoidDetails voidTransDetails;


    /**
     * No args constructor for use in serialization
     *
     */
    public VoidTransactionReq() {
    }

    /**
     *
     * @param voidTransDetails
     */
    public VoidTransactionReq(VoidDetails voidTransDetails) {
        super();
        this.voidTransDetails = voidTransDetails;
    }


    public VoidDetails getVoidTransDetails() {
        return voidTransDetails;
    }

    public void setVoidTransDetails(VoidDetails voidTransDetails) {
        this.voidTransDetails = voidTransDetails;
    }

}
