package PredictAge_Api;

import io.restassured.response.ValidatableResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class AgePrediction {
    ValidatableResponse response;
    ValidatableResponse response2;

    @BeforeTest
    public void setup() {
        //here i set up the basics as the url and the name param that must be pass as param
        response = given()
                .baseUri("https://api.agify.io")
                .queryParam("name", "naira")
                .when()
                .get()
                .then();
    }

    @Test
    public void validateApiWorks() {
        //here we validate the logic of api just works
        response
                .log().ifError()
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void validateReturnedParam() {
        //here we validate that 3 parameters are returned in response
        response
                .log().ifError()
                .assertThat()
                .body("$", allOf(
                        hasKey("name"),
                        hasKey("age"),
                        hasKey("count")
                ));
    }

    @Test
    public void validateLogic() {
 //here we validate the logic of each parameter as(name match req, age>0 , count>0)
        String name = response.extract().jsonPath().getString("name");
        Integer age = response.extract().jsonPath().getInt("age");
        Integer count = response.extract().jsonPath().getInt("count");

        Assert.assertEquals(name, "naira", "Name does not match");
        Assert.assertNotNull(age, "Age is null");
        Assert.assertTrue(age > 0, "Age is invalid");
        Assert.assertNotNull(count, "Count is null");
        Assert.assertTrue(count > 0, "Count is invalid");
    }


    @Test
    public void validateResponseNotAffected() {
        //here we test if we send another wrong parameter
        response2 = given()
                .baseUri("https://api.agify.io")
                .queryParams("name", "nairaa", "WrongParam", "1")
                .when()
                .get()
                .then()
                .log().ifError()
                .assertThat()
                .statusCode(200);


        String name = response2.extract().jsonPath().getString("name");
        Integer age = response2.extract().jsonPath().getInt("age");
        Integer count = response2.extract().jsonPath().getInt("count");

        Assert.assertEquals(name, "nairaa", "Name does not match");
       Assert.assertNotNull(age, "Age is null");
        Assert.assertTrue(age > 0, "Age is invalid");
        Assert.assertNotNull(count, "Count is null");
        Assert.assertTrue(count > 0, "Count is invalid");

    }

    @Test
    public void validateWhenSendWithoutNameParam() {
        //here we validate if send without name param which is required
        response2 = given()
                .baseUri("https://api.agify.io")
                .when()
                .get()
                .then()
                .log().ifError()
                .assertThat()
                .statusCode(not(200));
      //  String errorMessage = response2.extract().jsonPath().getString("error");
    }


    @Test
    public void WrongNameNotReturnData() {
        // here we expect that it pass but not return age nor count
        String[] invalidNames = {"","  ","@#$%", "'union+select'","naira2","12345", "نيرة"};

        for (String invalidName : invalidNames) {
            response2 = given()
                    .baseUri("https://api.agify.io")
                    .queryParam("name", invalidName)
                    .when()
                    .get()
                    .then()
                    .log().ifError()
                    .assertThat()
                    .statusCode(200)
                    .body("age", nullValue(), "count", equalTo(0));
        }
    }
    @Test
    public void testPerformance() {
        long responseTime = given()
                .queryParam("name", "naira4")
                .get("https://api.agify.io")
                .time();
        Assert.assertTrue(responseTime < 2000, "Response time is too high");
    }

}