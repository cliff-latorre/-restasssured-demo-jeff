import static io.restassured.RestAssured.*;

import Pojos.CreateUserRequest;
import Pojos.CreateUserResponse;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ReqResTest extends BaseTest{




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


    @Test
    public void registerUserWithPojoTest() {
        CreateUserRequest user = new CreateUserRequest();
        user.setEmail("eve.holt@reqres.in");
        user.setPassword("pistol");
        CreateUserResponse userResponse = given()
                .contentType(ContentType.JSON)
                .body(user)
                .post("register")
                .then()
                .statusCode(HttpStatus.SC_OK)
        .extract().body().as(CreateUserResponse.class);

        assertThat(userResponse.getId(), equalTo(4));
        assertThat(userResponse.getToken(), equalTo("QpwL5tke4Pnpja7X4"));
    }
}
