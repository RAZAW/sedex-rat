import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertThat;

public class OrgsTest {

    @Test
    public void ratOrgsTest() {
        int statusCode;
        long responseTime;
        String resBody;

        RestAssured.baseURI = Config.base_url;

        RequestSpecification request = RestAssured.given().auth().basic("rat", "rat");

        Response response = request
                .given()
                .param("start", 1560207600)
                .param("end", 1560294000)
                .when()
                .get("organisations");

        statusCode = response.getStatusCode();
        assertThat("Web API returns 200", statusCode, is(200));
        responseTime = response.time();
        assertThat(responseTime, is(lessThanOrEqualTo(2500L)));
        resBody = response.getBody().print();
        assertThat("response body is not empty", resBody, is(notNullValue()));
    }
}
