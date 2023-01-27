package com.api.auto.testcase;


import com.api.auto.utils.PropertiesFileUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.requestSpecification;
import static org.testng.Assert.assertEquals;

public class TC_API_Login {
    private String account;
    private String password;
    private Response response;
    private ResponseBody responseBody;
    private JsonPath jsonBody;

    @BeforeClass
    public void init() {
        // Init data
        String baseURL = PropertiesFileUtils.getProperties("baseurl");
        String loginPath = PropertiesFileUtils.getProperties("loginPath");
        account = "testerFunix";
        password = "Abc13579";
        System.out.println("Test case API Login");

        RestAssured.baseURI = baseURL;
        RestAssured.basePath = loginPath;

        // make body
        Map<String, Object> body1 = new HashMap<String, Object>();
        body1.put("account", account);
        body1.put("password", password);

        //init request
        RequestSpecification request = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body1);

        //post and response
        response = request.post();
        responseBody = response.getBody();
        jsonBody = responseBody.jsonPath();

        System.out.println(" " + responseBody.asPrettyString());
    }

    @Test(priority = 0)
    public void TC01_Validate200Ok() {
        Assert.assertEquals(response.getStatusCode(), 200, "Status Check Failed!");
    }

    @Test(priority = 1)
    public void TC02_ValidateMessage() {
        assertEquals(true, responseBody.asString().contains("message"), "Message field check Failed!");
        String resMessage = jsonBody.get("message");
        if (null == resMessage) resMessage = "";
        assertEquals(resMessage, "Đăng nhập thành công", "Message is not matched");
    }

    @Test(priority = 2)
    private void TC03_ValidateToken() {
        assertEquals(true, responseBody.asString().contains("token"), "token field check Failed!");
        String token = jsonBody.getString("token");
        PropertiesFileUtils.saveToken(token);
    }

    @Test(priority = 3)
    public void TC04_ValidateUserType() {
        assertEquals(true, responseBody.asString().contains("user"), "user information check Failed!");
        String typeUser = jsonBody.get("user.type");
        if (null == typeUser) typeUser = "";
        assertEquals(typeUser, "UNGVIEN", "type user is not matched");
    }

    @Test(priority = 4)
    public void TC05_ValidateAccount() {
        assertEquals(true, responseBody.asString().contains("user"), "user information check Failed!");

        String accountUser = jsonBody.get("user.account");
        if (null == accountUser) accountUser = "";
        assertEquals(accountUser, account, "account user is not matched");

        String passwordUser = jsonBody.get("user.password");
        if(null == passwordUser) passwordUser="";
        assertEquals(passwordUser,password, "password user is not matched ");

    }
    @AfterClass
    public void afterTest(){
        //clear endpoint
        RestAssured.baseURI =null;
        RestAssured.basePath=null;
    }
}


