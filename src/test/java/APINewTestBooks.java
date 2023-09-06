import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

public class APINewTestBooks {
    private final int unexistingBookISBN = 12;
    private final String userName = "user112user";
    private final String password = "uSer123!";
    Map<String, String> request = new HashMap<>();

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "https://demoqa.com/Account/v1/";

        request.put("userName", userName);
        request.put("password", password);
    }

    @Test
    public void checkAutorizes() {
        given().contentType("application/json")
                .body(request)
                .when()
                .post(baseURI + "Authorized/")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK");
    }


    @Test
    public void bookNotFoundTest() {
        RestAssured.baseURI = "https://demoqa.com/BookStore/v1/";
        given().when()
                .get(baseURI + "Book?ISBN=" + unexistingBookISBN)
                .then()
                .log().all()
                .statusCode(400)
                .statusLine("HTTP/1.1 400 Bad Request")
                .body("code", equalTo("1205"))
                .body("message", equalTo("ISBN supplied is not available in Books Collection!"));
    }

}
