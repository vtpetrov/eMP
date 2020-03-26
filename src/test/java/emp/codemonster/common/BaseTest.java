package emp.codemonster.common;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.ReadContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static emp.codemonster.common.Config.config;
import static emp.codemonster.common.RestClient.getLastResponse;
import static org.junit.Assert.assertEquals;

public class BaseTest {

    private static final Logger logger = LogManager.getLogger(BaseTest.class.getSimpleName());
    public static Request currentRequest = new Request();

    public BaseTest() {
        Config.LoadPropertyFiles();

        // prepare request skeleton:
        currentRequest.clear();
        currentRequest.setVersion("");
        String stringToEncode = config.getProperty("basicUser") + ":" + config.getProperty("basicPassword");
        String basicToken = Base64.getEncoder().encodeToString(stringToEncode.getBytes(StandardCharsets.UTF_8));
        currentRequest.addAuthorization("Basic " + basicToken);
        currentRequest.setContentType("application/json");
        //set base uri:
        RestClient.setBaseURI(config.getProperty("baseUrl"));
    }

    /**
     * Compares a single value from a json with the supplied expectedResultAsString
     * Finding the value pointed by the jsonPath and converting it to String first,
     * before compare it with the expectedResultAsString.
     *
     * @param jsonPath               the jsonPath pointing to the actual value to be compared within the body fo the LastResponse
     * @param expectedResultAsString the expectedResultAsString to be compared to
     * @see <a href="https://github.com/jayway/JsonPath">https://github.com/jayway/JsonPath</a>
     */
    public void the_field_has_the_value_(String jsonPath, String expectedResultAsString) {

        Configuration conf = Configuration.builder().options(Option.SUPPRESS_EXCEPTIONS).build();
        ReadContext responseContext = JsonPath.using(conf).parse(getLastResponse().getBody());

        Object parsedResult = responseContext.read(jsonPath);

        String actualResultAsString = String.valueOf(parsedResult);

        assertEquals("Expected doesn't match actual: ", expectedResultAsString, actualResultAsString);
        logger.debug("Success: the given expected value [{}], matches the actual value [{}] pointed by jsonPath '{}' ", expectedResultAsString, actualResultAsString, jsonPath);
    }

}
