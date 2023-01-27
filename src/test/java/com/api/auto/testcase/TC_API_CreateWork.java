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

import static org.testng.Assert.assertEquals;

public class TC_API_CreateWork {
    private String token;
    private Response response;
    private ResponseBody responseBody;
    private JsonPath jsonBody;

    private String myWork ="automation tester";
    private String myExperience ="1 măm";
    private String myEducation = "Đại học";

    @BeforeClass
    public void init() {
        // Init data
        String baseURL = PropertiesFileUtils.getProperties("baseurl");
        String createPath = PropertiesFileUtils.getProperties("createWorkPath");
        token = PropertiesFileUtils.getToken();
        System.out.println("Test case API Create Work");

        RestAssured.baseURI = baseURL;
        RestAssured.basePath = createPath;

        // make body
        Map<String, Object> body2 = new HashMap<String, Object>();
        body2.put("nameWork", myWork);
        body2.put("experience", myExperience);
        body2.put("education",myEducation);

        //init request
        RequestSpecification request = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("token",token)
                .body(body2);

        //post and response
        response = request.post();
        responseBody = response.getBody();
        jsonBody = responseBody.jsonPath();

        System.out.println(" " + responseBody.asPrettyString());
    }

    @Test(priority = 0)
    public void TC01_Validate201Created(){
        Assert.assertEquals(response.getStatusCode(), 201, "Status Check Failed!");
    }

    @Test(priority = 1)
    public void TC02_ValidateWorkId(){
        assertEquals(true, responseBody.asString().contains("id"), "id user check Failed!");
    }

    @Test(priority = 2)
    public void TC03_ValidateNameOfWorkMatched(){
        assertEquals(true, responseBody.asString().contains("nameWork"), "nameWork field check Failed!");
        String resNameWork = jsonBody.get("nameWork");
        if (null == resNameWork) resNameWork = "";
        assertEquals(resNameWork, myWork, "nameWork is not matched");
    }

    @Test(priority = 3)
    public void TC04_ValidateExperienceMatched(){
        assertEquals(true, responseBody.asString().contains("experience"), "experience field check Failed!");
        String resExperience = jsonBody.get("experience");
        if (null == resExperience) resExperience= "";
        assertEquals(resExperience,myExperience, "experience is not matched");
    }

    @Test(priority = 4)
    public void TC05_ValidateEducationMatched(){
        assertEquals(true, responseBody.asString().contains("education"), "education field check Failed!");
        String resEducation = jsonBody.get("education");
        if (null == resEducation) resEducation= "";
        assertEquals(resEducation,myEducation, "education is not matched");
    }

    @AfterClass
    public void afterTest(){
        //clear endpoint
        RestAssured.baseURI =null;
        RestAssured.basePath=null;
    }
}
