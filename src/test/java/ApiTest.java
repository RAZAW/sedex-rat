import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.Test;
import pojos.BookingResponse;

import java.util.logging.Logger;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

public class ApiTest {

    private static final Logger LOGGER = Logger.getLogger(String.valueOf(ApiTest.class));

    @Test
    public void myFirstRaTest() {
        assertThat(RestAssured.config(), instanceOf(RestAssuredConfig.class));
    }

    @Test
    public void mySecondRaTest() {
        Response response = given().get("https://restful-booker.herokuapp.com/ping/");
        int statusCode = response.getStatusCode();
        assertThat("Web API returns 200", statusCode, is(201));
    }

    @Test
    public void myThirdTest() {

        Response response = given()
                .get("https://restful-booker.herokuapp.com/booking/1");

        LOGGER.info(response.print());

        BookingResponse responseBody = response.as(BookingResponse.class);

        String additionalNeeds = responseBody.getAdditionalNeeds();
        LOGGER.info(additionalNeeds);
        //assertThat(additionalNeeds, is("Breakfast"));

        int totalPrice = responseBody.getTotalPrice();
        LOGGER.info(String.valueOf(totalPrice));
        assertThat("price paid is greater than 0", totalPrice, is(greaterThan(0)));
    }

    @Test
    public void myFourthTest() {

        Header header = new Header("Accept", "application/json");

        Response response = given().
                            header(header).
                            get("https://restful-booker.herokuapp.com/booking/1");

        int statusCode = response.getStatusCode();
        assertThat("response code is 200", statusCode, is(200));
        String resBody = response.getBody().print();
        //assertThat("response contains breakfast", resBody, containsString("Breakfast"));

    }

}
