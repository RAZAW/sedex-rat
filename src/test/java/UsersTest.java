import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONArray;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class UsersTest {

    private static final Logger LOGGER = Logger.getLogger(String.valueOf(UsersTest.class));

    @Test
    public void usersChangedInTimePeriod() {

        RestAssured.baseURI = Config.base_url;

        RequestSpecification request = given().auth().basic("rat", "rat");

        Header header1 = new Header("Content-Type", "application/json");
        Header header2 = new Header("Accept", "application/json");

        Response response = request.
                given().
                param("start", "1560207600").
                param("end", "1560294000").
                header(header1).
                header(header2).
                when().
                get("users");

        int statusCode = response.getStatusCode();
        assertThat("status code should be 200", statusCode, is(200));
        String resBody = response.getBody().print();
        assertThat("response body should not be empty", resBody, is(notNullValue()));
        long resTime = response.time();
        LOGGER.info("response time is " + resTime);
        assertThat("response time should be fast", resTime, is(lessThanOrEqualTo(2000L)));

    }

    @Test
    public void userDetails() {

        RestAssured.baseURI = Config.base_url;

        RequestSpecification request = given().auth().basic("rat", "rat");

        Header header = new Header("Content-Type", "application/json");

        Response response = request.
                given().
                header(header).
                when().
                get("users/ZU1016106");

        int statusCode = response.getStatusCode();
        assertThat("status code should be 200", statusCode, is(200));
        String resBody = response.getBody().print();
        assertThat("response body should not be empty", resBody, is(notNullValue()));
        long resTime = response.time();
        LOGGER.info("response time is " + resTime);
        assertThat("response time should be fast", resTime, is(lessThanOrEqualTo(3000L)));
    }

    @Test
    public void userSites() {

        RestAssured.baseURI = Config.base_url;

        RequestSpecification request = given().auth().basic("rat", "rat");

        Header header = new Header("Content-Type", "application/json");

        Response response = request.
                given().
                header(header).
                when().
                get("users/ZU1016106/sites");

        int statusCode = response.getStatusCode();
        assertThat("status code returned should be 200", statusCode, is(200));
        List<String> resBody = response.jsonPath().get();
        assertThat("response body should not be empty", resBody.size(), is(greaterThan(0)));
        LOGGER.info("site list contains " + resBody.size() + " sites");
        long resTime = response.time();
        LOGGER.info("response time is " + resTime);
        assertThat("response time should be fast", resTime, is(lessThanOrEqualTo(2000L)));

    }
}
