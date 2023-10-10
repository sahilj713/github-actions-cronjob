package steps;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class WebServiceFactory {

    public static String token;
    public static Response response;
    public static String jsonString;

    public static final String user = "jill.taylor";
    public static final String pass = "112233";
    public static final String tenantID = "bpd";

    public static final String baseURL = "https://cma-dev-api.seguesolutions.org/api";

    public String getToken() {

        RestAssured.baseURI = baseURL;

        RequestSpecification request = RestAssured.given();

        request.header("Content-Type", "application/json");
        response = request.body("{ \"userName\":\"" + user + "\", \"password\":\"" + pass + "\", \"tenantId\":\"" + tenantID + "\"}")
                .post("/auth/login");

        String jsonString = response.asString();
        token = JsonPath.from(jsonString).get("token");
        System.out.println(token);
        return token;
    }
}
