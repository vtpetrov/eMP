package emp.codemonster.common;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.ReadContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Rule;
import org.junit.rules.TestName;

import java.util.Locale;

import static emp.codemonster.common.Config.config;
import static emp.codemonster.common.RestClient.getLastResponse;
import static emp.codemonster.common.Utils.removeSecondsFromTimestamp;
import static emp.codemonster.common.Utils.timestampRootUTC;
import static org.junit.Assert.assertEquals;

public class BaseTest {

    private static boolean executedHealthCheck = false;
    private static boolean configLoaded = false;

    private static final Logger logger = LogManager.getLogger(BaseTest.class.getSimpleName());
    protected static Faker faker = new Faker(Locale.ENGLISH);

    public static Request currentRequest = new Request();
    public static final String UNAUTHORIZED_401_RESPONSE_BODY = "HTTP Basic: Access denied.\n";

    protected Gson gson = new Gson();

    @Rule
    public TestName name = new TestName();

    public BaseTest() {
        if (!configLoaded) {
            Config.LoadPropertyFiles();
            configLoaded = true;
        }
        //set base url:
        RestClient.setBaseURI(config.getProperty("baseUrl"));
        healthCheckRoot();

        initializeCodemonsterEmptyRequest();
    }

    protected void initializeCodemonsterEmptyRequest() {
        // prepare request skeleton:
        currentRequest.clear();
        currentRequest.setVersion("");
        currentRequest.addAuthorization("Basic " + Utils.generateBasicAuthTokenFromConfig());
        currentRequest.setContentType("application/json");
    }

    public void healthCheckRoot() {
        if (executedHealthCheck) return;

        logger.info("-----------------------------------------------------------------------------");
        logger.info("---===================      HEALTH CHECK - START       ===================---");
        logger.info("Performing health check of the root '{}' API", RestClient.getBaseUrl());
        initializeCodemonsterEmptyRequest();

        currentRequest.deleteAuthorization();
        Response response = RestClient.get(currentRequest);
        String expectedTimestamp = removeSecondsFromTimestamp(timestampRootUTC());
        String rootResponse = removeSecondsFromTimestamp(response.getBody());

        //Assert success response status code == 200
        assertEquals("Status code is not as expected:", 200, response.getStatusCode());

        assertEquals("Root timestamp response is not as expected:", expectedTimestamp, rootResponse);

        executedHealthCheck = true;
        logger.info("Success: the expected timestamp [{}], matches the actual timestamp [{}] (seconds truncated)"
                , expectedTimestamp, rootResponse);

        logger.info("---===================      HEALTH CHECK - END;       ===================---");
        logger.info("----------------------------------------------------------------------------");
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
        logger.info("Success: the given expected value [{}], matches the actual value [{}] pointed by jsonPath '{}' ", expectedResultAsString, actualResultAsString, jsonPath);
    }

    /**
     * Updates given node in a source JSON. Returns the updated JSON.
     *
     * @param json  The old JSON
     * @param node  the node to be updated
     * @param value the value for the updted node
     * @return The new JSON
     */
    public String setJsonField(String json, String node, String value) {
        JsonElement jsonElement = gson.fromJson(json, JsonElement.class);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        jsonObject.addProperty(node, value);

        return jsonObject.toString();
    }

}
