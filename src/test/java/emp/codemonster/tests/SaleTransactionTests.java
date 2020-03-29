package emp.codemonster.tests;

import emp.codemonster.common.BaseTest;
import emp.codemonster.common.Response;
import emp.codemonster.common.RestClient;
import emp.codemonster.models.requests.SaleDetails;
import emp.codemonster.models.requests.SaleTransactionReq;
import emp.codemonster.models.responses.TransactionResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;

public class SaleTransactionTests extends BaseTest {
    private static final Logger logger = LogManager.getLogger(SaleTransactionTests.class.getSimpleName());

    @Before
    public void init() {
        currentRequest.setPath("/payment_transactions");
    }

    //    200	OK APPROVED
    /**
     * This test assures that happy path scenario for creating new approved sale transaction passes.
     * <p> Expected response is like:
     * <pre>
     *  200 OK
     *  {
     *     "unique_id": "31ac9d2f28e25f730a4df0dbcf85e4f8",
     *     "status": "approved",
     *     "usage": "Machinery",
     *     "amount": 299,
     *     "transaction_time": "2020-03-28T22:01:02.816Z",
     *     "message": "Your transaction has been approved."
     *   }
     * </pre>
     */
    @Test
    public void createSimpleApprovedSaleTransaction() throws UnsupportedEncodingException {
        logger.info("---------------------------------------------------------------");
        logger.info("---===================       START       ===================---");
        logger.info("Executing test [{}]", name.getMethodName());
        SaleDetails saleDetails = SaleDetails.generateRandomSaleDetails();
        SaleTransactionReq saleTransToSend = new SaleTransactionReq(saleDetails);

        currentRequest.setBody(gson.toJson(saleTransToSend));
        Response response = RestClient.post(currentRequest);

        //Assert success response status code == 200
        assertEquals("Status code is not as expected:", 200, response.getStatusCode());

        // Assert response matches expected values (based on the request sent)
        TransactionResponse saleTransRespObj = gson.fromJson(response.getBody(), TransactionResponse.class);
        // Assert "status" and "message":
        saleTransRespObj.verifyStatus(TransactionResponse.STATUS_APPROVED);
        saleTransRespObj.verifyMessage(TransactionResponse.SALE_MESSAGE_APPROVED);

        // Compare fields that are existing in both request and response:
        // "usage":
        saleTransRespObj.verifyUsage(saleTransToSend.getSaleTransDetails().getUsage());
        //"amount":
        saleTransRespObj.verifyAmount(saleTransToSend.getSaleTransDetails().getAmount());

        logger.info("Test [{}] was successful\n", name.getMethodName());
        logger.info("---====================       END       ====================---");
        logger.info("---------------------------------------------------------------");

    }

    //    200	OK  DECLINED:
    /**
     * Make sure sale transaction is declined if card number is not equal to 4200000000000000
     * This test assures that happy path scenario for creating new declined sale transaction passes.
     * <p> Expected response is like:
     * <pre>
     *  200 OK
     *  {
     *     "unique_id": "5d29d84790e2917728038caebddeceae",
     *     "status": "declined",
     *     "usage": "Accounting",
     *     "amount": 447,
     *     "transaction_time": "2020-03-29T11:37:35.684Z",
     *     "message": "Your transaction has been declined."
     * }
     * </pre>
     */
    @Test
    public void createSimpleDeclinedSaleTransaction() throws UnsupportedEncodingException {
        logger.info("---------------------------------------------------------------");
        logger.info("---===================       START       ===================---");
        logger.info("Executing test [{}]", name.getMethodName());

        SaleDetails saleDetails = SaleDetails.generateRandomSaleDetails();
        saleDetails.setCardNumber(faker.numerify("55##############"));

        SaleTransactionReq saleTransToSend = new SaleTransactionReq(saleDetails);

        currentRequest.setBody(gson.toJson(saleTransToSend));
        Response response = RestClient.post(currentRequest);

        //Assert success response status code == 200
        assertEquals("Status code is not as expected:", 200, response.getStatusCode());

        // Assert response matches expected values (based on the request sent)
        TransactionResponse saleTransRespObj = gson.fromJson(response.getBody(), TransactionResponse.class);
        // Assert status and message:
        // "status":
        assertEquals("\"status\" value is not as expected",
                TransactionResponse.STATUS_DECLINED,
                saleTransRespObj.getStatus());
        // "message":
        assertEquals("\"message\" value is not as expected",
                TransactionResponse.SALE_MESSAGE_DECLINED,
                saleTransRespObj.getMessage());

        // Compare fields that are existing in both request and response:
        // "usage":
        assertEquals("\"usage\" value is not as expected",
                saleTransToSend.getSaleTransDetails().getUsage(),
                saleTransRespObj.getUsage());
        //"amount":
        assertEquals("\"amount\" value is not as expected",
                saleTransToSend.getSaleTransDetails().getAmount(),
                String.valueOf(saleTransRespObj.getAmount()));

        logger.info("Test [{}] was successful\n", name.getMethodName());
        logger.info("---====================       END       ====================---");
        logger.info("---------------------------------------------------------------");
    }


//    401	Access denied
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
    public void saleTransactionUnauthorized() throws UnsupportedEncodingException {
        logger.info("---------------------------------------------------------------");
        logger.info("---===================       START       ===================---");
        logger.info("Executing test [{}]", name.getMethodName());
        SaleDetails saleDetails = SaleDetails.generateRandomSaleDetails();
        SaleTransactionReq saleTransToSend = new SaleTransactionReq(saleDetails);

        currentRequest.setBody(gson.toJson(saleTransToSend));
        // remove the authorization in order to get 401
        currentRequest.deleteAuthorization();
        Response response = RestClient.post(currentRequest);

        //Assert success response status code == 200 and response body:
        assertEquals("Status code is not as expected:", 401, response.getStatusCode());
        assertEquals("Body not as expected", UNAUTHORIZED_401_RESPONSE_BODY, response.getBody());

        logger.info("Test [{}] was successful\n", name.getMethodName());
        logger.info("---====================       END       ====================---");
        logger.info("---------------------------------------------------------------");
    }

}
