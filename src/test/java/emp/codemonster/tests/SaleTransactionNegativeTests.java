package emp.codemonster.tests;

import com.github.javafaker.Faker;
import emp.codemonster.common.BaseTest;
import emp.codemonster.common.Response;
import emp.codemonster.common.RestClient;
import emp.codemonster.models.requests.SaleDetails;
import emp.codemonster.models.requests.SaleTransactionReq;
import emp.codemonster.models.responses.UnprocessableEntityResponse;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.UnsupportedEncodingException;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

@RunWith(JUnitParamsRunner.class)
public class SaleTransactionNegativeTests extends BaseTest {

    private static final Logger logger = LogManager.getLogger(SaleTransactionNegativeTests.class.getSimpleName());
    protected static Faker faker = new Faker(Locale.ENGLISH);

    @Before
    public void init() {
        currentRequest.setPath("/payment_transactions");
    }

    private Object[] invalidParams() {
        return new Object[]{
                new Object[]{"transaction_type", faker.lorem().fixedString(5), "is not included in the list"},
                new Object[]{"amount", faker.number().randomDouble(2, 200, 500), "must be an integer"},
                new Object[]{"amount", 0, "must be greater than 0"},
                new Object[]{"email", faker.internet().emailAddress().replace("@", ""), "is invalid"},
                new Object[]{"card_number", faker.number().digits(12), "is invalid"},
                new Object[]{"card_number", faker.number().digits(20), "is invalid"},
                new Object[]{"card_number", faker.lorem().characters(16), "is invalid"},
                new Object[]{"cvv", faker.number().digits(2), "is invalid"},
                new Object[]{"cvv", faker.number().digits(5), "is invalid"},
                new Object[]{"cvv", faker.letterify("???"), "is invalid"}
        };
    }

    @Test
    @Parameters(method = "invalidParams")
    public void unprocessableEntity422(String node, String requestValue, String errorMessage) throws UnsupportedEncodingException {
        logger.info("---------------------------------------------------------------");
        logger.info("---===================       START       ===================---");
        logger.info("Executing test [{}]", name.getMethodName());

        SaleDetails saleDetails = SaleDetails.generateRandomSaleDetails();
        //Convert to JSON and set the new value for the field:
        String saleDetailsJson = gson.toJson(saleDetails);
        saleDetailsJson = setJsonField(saleDetailsJson, node, requestValue);

        // Set the object back with the new data:
        saleDetails = gson.fromJson(saleDetailsJson, SaleDetails.class);
        SaleTransactionReq saleTransToSend = new SaleTransactionReq(saleDetails);

        currentRequest.setBody(gson.toJson(saleTransToSend));

        Response response = RestClient.post(currentRequest);
        assertEquals(name.getMethodName() + " - Status code is not as expected:", 422, response.getStatusCode());

        UnprocessableEntityResponse responseObject = gson.fromJson(response.getBody(), UnprocessableEntityResponse.class);

        // Assert response value for the given node:
        the_field_has_the_value_("$." + node + "[0]", errorMessage);

        logger.info("Test [{}] was successful\n", name.getMethodName());
        logger.info("---====================       END       ====================---");
        logger.info("---------------------------------------------------------------");
    }

}
