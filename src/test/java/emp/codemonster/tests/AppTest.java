package emp.codemonster.tests;

import emp.codemonster.common.BaseTest;
import emp.codemonster.common.Response;
import emp.codemonster.common.RestClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for simple App.
 */
public class AppTest extends BaseTest
{
    private static final Logger logger = LogManager.getLogger(AppTest.class.getSimpleName());

    public AppTest() throws Exception {
    }

    @Test
    public void demoTest() throws Throwable {

        logger.info("Running test: {}", "demoTest");
        currentRequest.setPath("/payment_transactions");
        currentRequest.setBody("{\n" +
                "   \"payment_transaction\":{\n" +
                "      \"card_number\":\"4200000000000000\",\n" +
                "      \"cvv\":\"567\",\n" +
                "      \"expiration_date\":\"12/2021\",\n" +
                "      \"amount\":\"305\",\n" +
                "      \"usage\":\"Demomaker\",\n" +
                "      \"transaction_type\":\"sale\",\n" +
                "      \"card_holder\":\"Chicho Mitko\",\n" +
                "      \"email\":\"chm@emp.com\",\n" +
                "      \"address\":\"Sofia, Mladost 4, China\"\n" +
                "   }\n" +
                "}");

        Response response = RestClient.post(currentRequest);

        //Assert success response status code == 200
        assertEquals("Status code is not as expected:", 200, response.getStatusCode());

        //Assert the operation status and message:
        the_field_has_the_value_("$.status", "approved");
        the_field_has_the_value_("$.message", "Your transaction has been approved.");
    }
}
