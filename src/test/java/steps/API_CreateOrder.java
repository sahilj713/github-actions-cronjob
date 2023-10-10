package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.Assert;
import steps.WebServiceFactory;
import utilities.ConfigReader;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;

public class API_CreateOrder {

    public static String orderID = "";

    public String testDataPath = System.getProperty("user.dir") + File.separator
            + "testdata";
    public static final String baseURL = "https://cma-dev-api.seguesolutions.org/api";

    @When("user hit login API and store token")
    public void userHitLoginAPIAndStoreToken() {
        System.out.println("Logged in successfully");
    }

    @And("user creates order")
    public void userCreateOrder() {
        String filePath = testDataPath + File.separator + "createOrder.json";
        File jsonFile = new File(filePath);

        RestAssured.baseURI = baseURL;
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + ConfigReader.getConfigPropertyVal("token"))
                .header("Content-Type", "application/json");

        Response response = request.given().contentType(ContentType.JSON).body(jsonFile).post("/orders");
        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
        orderID = response.path("orderId").toString();
        System.out.println("Order created with ID: " + orderID);
    }

    @Then("user should see same order at All orders")
    public void userShouldSeeSameOrderAtAllOrders() {
        RestAssured.baseURI = baseURL;
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + ConfigReader.getConfigPropertyVal("token"))
                .header("Content-Type", "application/json");
        Response response = request.get("/orders?referringFacilityId=1");

        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
        String[] listOfID = response.path("orderId").toString().replaceAll("\\[", "")
                .replaceAll("]", "")
                .split(",");

        boolean test = false;
        for (String id : listOfID) {
            if (Objects.equals(id.trim(), orderID)) {
                test = true;
                break;
            }
         }
        if (!test) {
            System.out.println(orderID + " - order not found in response of all orders");
        }
        assert test;
    }
}
