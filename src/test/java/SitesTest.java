import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Test;
import utils.FileUtil;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class SitesTest {

    @Test
    public void sitesChangedInTimePeriod() {

        RestAssured.baseURI = Config.base_url;

        RequestSpecification request = given().auth().basic("rat", "rat");

        Response response = request.
                given().
                param("start", "1560207600").
                param("end", "1560294000").
                when().
                get("sites");

        int statusCode = response.getStatusCode();
        assertThat("status returned should be 200", statusCode, is(200));
        String resBody = response.getBody().print();
        assertThat("response body should not be empty", resBody, is(notNullValue()));
        long resTime = response.getTime();
        System.out.println(resTime);
        assertThat("response time should be fast", resTime, is(lessThanOrEqualTo(4000L)));


    }

    @Test
    public void updateRiskDetailsForSite() {

        RestAssured.baseURI = Config.base_url;

        RequestSpecification request = given().auth().basic("rat", "rat");

        String template = FileUtil.readFileS(System.getProperty("user.dir") + "/src/test/resources/templates/site_risk_details.json");
        assert template != null;

        Response response = request.
                            given().
                            contentType("application/json\r\n").
                            body(template).
                            when().
                            put("sites/ZS46712010");

        int statusCode = response.getStatusCode();
        assertThat("status code is 200", statusCode, is(204));
        }

}
