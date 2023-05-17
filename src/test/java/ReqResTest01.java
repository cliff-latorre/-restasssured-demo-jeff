import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

public class ReqResTest01 {


    @Before
    public void setUp() {
        baseURI = "https://reqres.in";
        basePath = "/api";

        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @Test
    public void getAllUsersTest() {
        given()
                .contentType(ContentType.JSON)
                .get("users?page=2")
                .then()
                .statusCode(200);
    }

    @Test
    public void getAllUsers2Test() {
        given()
                .contentType(ContentType.JSON)
                .get("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(200);
    }

    @Test
    public void createUserTest() {
        given()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"name\": \"jefferson\",\n" +
                        "    \"job\": \"qe\"\n" +
                        "}")
                .post("https://reqres.in/api/users")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("id", notNullValue())
                .body("name", equalTo("jefferson"))
                .body("job", equalTo("qe"));
    }


    @Test
    public void loginUserTest() {
        given()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"email\": \"eve.holt@reqres.in\",\n" +
                        "    \"password\": \"cityslicka\"\n" +
                        "}")
                .post("login")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("token", notNullValue());

    }

    @Test
    public void deleteUserTest() {
        given()
                .contentType(ContentType.JSON)
                .delete("users/12")
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);

    }
}
