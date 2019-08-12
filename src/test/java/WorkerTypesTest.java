import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Test;

import java.util.List;
import java.util.logging.Logger;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertThat;

public class WorkerTypesTest {

    private static final Logger LOGGER = Logger.getLogger(String.valueOf(WorkerTypesTest.class));

    @Test
    public void getAllWorkerTypes() {

        RestAssured.baseURI = Config.base_url;

        RequestSpecification request = given().auth().basic("rat", "rat");

        Header header = new Header("Content-Type", "application/json");

        Response response = request.
                given().
                header(header).
                when().
                get("worker-types");

        int statusCode = response.getStatusCode();
        assertThat("status code should be 200", statusCode, is(200));
        List<String> resBody = response.jsonPath().getList("$");
        LOGGER.info("response body contains list of " + resBody.size() + " items");
        assertThat("response body size should not be 0", resBody.size(), is(greaterThanOrEqualTo(0)));
        long resTime = response.time();
        LOGGER.info("response time is " + resTime + " ms");
        assertThat("response time is fast", resTime, is(lessThanOrEqualTo(1000L)));

    }
}
