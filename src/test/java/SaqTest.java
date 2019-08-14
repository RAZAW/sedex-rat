import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Test;

import java.util.logging.Logger;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertThat;

public class SaqTest {

    private static final Logger LOGGER = Logger.getLogger(String.valueOf(SaqTest.class));


    @Test
    public void ratSaqAnswers() {

        RestAssured.baseURI = Config.base_url;

        RequestSpecification request = RestAssured.given().auth().basic("rat", "rat");

        Header header1 = new Header("Accept", "application/json");
        Header header2 = new Header("Content/Type", "application/json");

        Response response = request.
                given().
                param("start", "1498863600").
                param("end", "1499382000").
                header(header1).
                header(header2).
                when().
                get("saq/answers");

        int statusCode = response.getStatusCode();
        assertThat("response code is 200", statusCode, is(200));
        LOGGER.info("Status code is " + statusCode);

        String resBody = response.getBody().print();
        assertThat("response body is not empty", resBody, is(notNullValue()));
        LOGGER.info("Response Body is " + resBody);

        long resTime = response.getTime();
        assertThat("response time is standard", resTime, is(lessThanOrEqualTo(100000L)));
        LOGGER.info("Response time is " + resTime);

    }

    @Test
    public void ratSaqQuestionnaire() {

        RestAssured.baseURI = Config.base_url;

        RequestSpecification request = given().auth().basic("rat", "rat");

        Header header1 = new Header("Accept", "application/json");
        Header header2 = new Header("Content/Type", "application/json");

        Response response = request.
                given().
                header(header1).
                header(header2).
                when().
                get("saq/questionnaire");

        int statusCode = response.getStatusCode();
        assertThat("status code should be 200", statusCode, is(200));
        LOGGER.info("Status code is " + statusCode);

        String resBody = response.getBody().print();
        assertThat("response body should not be empty", resBody, is(notNullValue()));
        LOGGER.info("Response Body is " + resBody);

        long resTime = response.getTime();
        assertThat("response time should be fast", resTime, is(lessThanOrEqualTo(20000L)));
        LOGGER.info("Response time is " + resTime);

    }
}
