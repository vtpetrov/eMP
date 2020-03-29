package emp.codemonster.models.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SaleTransactionReq {

    @SerializedName("payment_transaction")
    @Expose
    public SaleDetails saleTransDetails;


    /**
     * No args constructor for use in serialization
     *
     */
    public SaleTransactionReq() {
    }

    /**
     *
     * @param saleTransDetails
     */
    public SaleTransactionReq(SaleDetails saleTransDetails) {
        super();
        this.saleTransDetails = saleTransDetails;
    }


    public SaleDetails getSaleTransDetails() {
        return saleTransDetails;
    }

    public void setSaleTransDetails(SaleDetails saleTransDetails) {
        this.saleTransDetails = saleTransDetails;
    }

}
