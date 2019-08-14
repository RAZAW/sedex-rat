import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Test;

import java.util.logging.Logger;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertThat;

public class AuditsTest {

    private static final Logger LOGGER = Logger.getLogger(String.valueOf(AuditsTest.class));

    @Test
    public void ratAuditsTest() {

        RestAssured.baseURI = Config.base_url;

        Header header = new Header("Accept", "application/json");

        RequestSpecification request = RestAssured.given().auth().basic("rat", "rat");

        Response response = request.given()
                .param("start", "1560121200")
                .param("end", "1560207600")
                .header(header)
                .when()
                .get("audits");

        int statusCode = response.getStatusCode();
        assertThat("status code is 200", statusCode, is(200));
        LOGGER.info("Status code is " + statusCode);

        long responseTime = response.time();
        assertThat("response time is less than 30000L", responseTime, is(lessThanOrEqualTo(30000L)));
        LOGGER.info("Response time is " + responseTime);

        String resBody = response.getBody().print();
        assertThat("response is not null", resBody, is(notNullValue()));
        LOGGER.info("Response Body is " +  resBody);


    }

    @Test
    public void ratAuditFindingsTest() {

        RestAssured.baseURI = Config.base_url;

        Header header1 = new Header("Accept", "application/json");
        Header header2 = new Header("Content/Type", "application/json");

        RequestSpecification request = RestAssured.given().auth().basic("rat", "rat");

        Response response = request.given()
                .param("start", "1551830400")
                .param("end", "1560294000")
                .header(header1)
                .header(header2)
                .when()
                .get("auditfindings");

        int statusCode = response.getStatusCode();
        assertThat("response code is 200", statusCode, is(200));
        LOGGER.info("Status code is " + statusCode);

        String resBody = response.getBody().print();
        assertThat("response body is not null", resBody, is(notNullValue()));
        LOGGER.info("Response Body is " +  resBody);

        long resTime = response.time();
        assertThat("response time is fast", resTime, is(lessThanOrEqualTo(50000L)));
        LOGGER.info("Response time is " + resTime);



    }
}
