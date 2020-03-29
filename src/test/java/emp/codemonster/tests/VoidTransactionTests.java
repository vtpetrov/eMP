package emp.codemonster.tests;

import emp.codemonster.common.BaseTest;
import emp.codemonster.common.Response;
import emp.codemonster.common.RestClient;
import emp.codemonster.models.requests.VoidDetails;
import emp.codemonster.models.requests.VoidTransactionReq;
import emp.codemonster.models.responses.TransactionResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;

public class VoidTransactionTests extends BaseTest {

    private static final Logger logger = LogManager.getLogger(VoidTransactionTests.class.getSimpleName());
    TransactionResponse saleTransaction;

    @Before
    public void init() throws UnsupportedEncodingException {
        currentRequest.setPath("/payment_transactions");
    }

    // 200 OK, void existing transaction successfully

    /**
     * This test assures the happy path scenario for voiding existing sale transaction.
     * <p> Expected response is like:
     * <pre>
     *  200 OK
     *  {
     *     "unique_id": "30e587132bb2664699d3fd647dc2eb52",
     *     "status": "approved",
     *     "usage": "Leisure, Travel & Tourism",
     *     "amount": 187,
     *     "transaction_time": "2020-03-29T22:11:50.585Z",
     *     "message": "Your transaction has been voided successfully"
     * }
     * </pre>
     */
    @Test
    public void voidExistingTransaction() throws UnsupportedEncodingException {
        logger.info("---------------------------------------------------------------");
        logger.info("---===================       START       ===================---");
        logger.info("Executing test [{}]", name.getMethodName());

        saleTransaction = createNewSaleTransaction();

        VoidDetails voidDetails = new VoidDetails();
        voidDetails.setReferenceId(saleTransaction.getUniqueId());

        VoidTransactionReq voidTransToSend = new VoidTransactionReq(voidDetails);

        currentRequest.setBody(gson.toJson(voidTransToSend));
        Response voidResponse = RestClient.post(currentRequest);

        //Assert success response status code == 200
        assertEquals("Status code is not as expected:", 200, voidResponse.getStatusCode());

        // Assert response matches expected values (based on the request sent)
        TransactionResponse voidTransRespObj = gson.fromJson(voidResponse.getBody(), TransactionResponse.class);
        // Assert "status" and "message":
        voidTransRespObj.verifyStatus(TransactionResponse.STATUS_APPROVED);
        voidTransRespObj.verifyMessage(TransactionResponse.VOID_MESSAGE_APPROVED);

        // Compare fields across the original voided transaction and the void response:
        // "usage":
        voidTransRespObj.verifyUsage(saleTransaction.getUsage());
        //"amount":
        voidTransRespObj.verifyAmount(String.valueOf(saleTransaction.getAmount()));
        // "unique_id":
        voidTransRespObj.verifyUniqueId(voidDetails.getReferenceId());

        logger.info("Test [{}] was successful\n", name.getMethodName());
        logger.info("---====================       END       ====================---");
        logger.info("---------------------------------------------------------------");
    }

    // 401 Unauthorized
    /**
     * Negative test.
     * This test assures that Unauthorized requests are handled correctly.
     * <p> Expected response is like:
     * <pre>
     *  401 Unauthorized
     *  HTTP Basic: Access denied.
     * </pre>
     */
    @Test
    public void unauthorizedVoidRequest() throws UnsupportedEncodingException {
        logger.info("---------------------------------------------------------------");
        logger.info("---===================       START       ===================---");
        logger.info("Executing test [{}]", name.getMethodName());

        saleTransaction = createNewSaleTransaction();

        VoidDetails voidDetails = new VoidDetails();
        voidDetails.setReferenceId(saleTransaction.getUniqueId());

        VoidTransactionReq voidTransToSend = new VoidTransactionReq(voidDetails);

        currentRequest.setBody(gson.toJson(voidTransToSend));
        currentRequest.deleteAuthorization();
        Response voidResponse = RestClient.post(currentRequest);

        //Assert success response status code == 401 and response body:
        assertEquals("Status code is not as expected:", 401, voidResponse.getStatusCode());
        assertEquals("Body not as expected", UNAUTHORIZED_401_RESPONSE_BODY, voidResponse.getBody());

        logger.info("Test [{}] was successful\n", name.getMethodName());
        logger.info("---====================       END       ====================---");
        logger.info("---------------------------------------------------------------");
    }


    // 422 Try to void non-existing transaction
    /**
     * Negative test to ensure the validation when try to void non-existing transaction
     * <p> Expected response is like:
     * <pre>
     *  422 Unprocessable Entity
     *  {
     *   "reference_id": [
     *     "Invalid reference transaction!"
     *   ]
     * }
     * </pre>
     */
    @Test
    public void tryToVoidNonExistingTrans() throws UnsupportedEncodingException {
        logger.info("---------------------------------------------------------------");
        logger.info("---===================       START       ===================---");
        logger.info("Executing test [{}]", name.getMethodName());

        VoidDetails voidDetails = new VoidDetails();
        voidDetails.setReferenceId(faker.lorem().characters(32));

        VoidTransactionReq voidTransToSend = new VoidTransactionReq(voidDetails);

        currentRequest.setBody(gson.toJson(voidTransToSend));
        Response voidResponse = RestClient.post(currentRequest);

        assertEquals("Status code is not as expected:", 422, voidResponse.getStatusCode());

        // Assert response value for the given node:
        the_field_has_the_value_("$.reference_id[0]", "Invalid reference transaction!");

        logger.info("Test [{}] was successful\n", name.getMethodName());
        logger.info("---====================       END       ====================---");
        logger.info("---------------------------------------------------------------");
    }


    // 422 invalid "transaction_type"
    /**
     * Negative test to ensure the validation of the "transaction_type" enum.
     * <p> Expected response is like:
     * <pre>
     *  422 Unprocessable Entity
     *  {
     *   "transaction_type": [
     *     "is not included in the list"
     *   ]
     * }
     * </pre>
     */
    @Test
    public void invalidTransactionType() throws UnsupportedEncodingException {
        logger.info("---------------------------------------------------------------");
        logger.info("---===================       START       ===================---");
        logger.info("Executing test [{}]", name.getMethodName());

        saleTransaction = createNewSaleTransaction();

        VoidDetails voidDetails = new VoidDetails();
        voidDetails.setReferenceId(saleTransaction.getUniqueId());

        //Convert to JSON and set the new value for the field:
        String voidDetailsJson = gson.toJson(voidDetails);
        voidDetailsJson = setJsonField(voidDetailsJson, "transaction_type", faker.letterify("????"));

        // Set the object back with the new data:
        voidDetails = gson.fromJson(voidDetailsJson, VoidDetails.class);

        VoidTransactionReq voidTransToSend = new VoidTransactionReq(voidDetails);

        currentRequest.setBody(gson.toJson(voidTransToSend));
        Response voidResponse = RestClient.post(currentRequest);

        assertEquals("Status code is not as expected:", 422, voidResponse.getStatusCode());

        // Assert response value for the given node:
        the_field_has_the_value_("$.transaction_type[0]", "is not included in the list");

        logger.info("Test [{}] was successful\n", name.getMethodName());
        logger.info("---====================       END       ====================---");
        logger.info("---------------------------------------------------------------");
    }

}
