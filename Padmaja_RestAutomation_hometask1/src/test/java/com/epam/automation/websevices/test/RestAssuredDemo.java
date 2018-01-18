package com.epam.automation.websevices.test;

import com.epam.automation.websevices.model.MyPojo;
import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class RestAssuredDemo {
    Response response = null;
    private static int expectedStatusCode = 200;
    private static final String expectedHeaderName = "Content-Type";
    private static final String expectedHeaderValue = "application/json";
    private static int expectedNumberOfUsers = 10;
    
    @BeforeClass
    public void initTest(){
        RestAssured.baseURI = "http://jsonplaceholder.typicode.com";
        RequestSpecification request = RestAssured.given();
        response = request.request(Method.GET, "/users");
    }

    @Test(description = "Check Response Status")
    public void checkStatusCode(){
    	int actualStatusCode = response.getStatusCode();
    	System.out.println("The Response status code is: " + actualStatusCode);
        Assert.assertEquals(expectedStatusCode, actualStatusCode,"The response code matches with the expected.");
    }

    @Test(description = "Check Response Headers have Header-content-type")
    public void checkResponseHeader(){
		Headers headers = response.getHeaders();
		Boolean contentType = headers.hasHeaderWithName(expectedHeaderName);
		Assert.assertEquals(expectedHeaderName, contentType, "The header " + expectedHeaderName + " is not present in the list of headers");
    }

    @Test(description = "Check Response Headers have Header-content-type with value application/json; charset=utf-8")
    public void checkResponseHeaderValue(){
    	String headerValue = response.getHeader(expectedHeaderName);
		Assert.assertEquals(expectedHeaderValue, headerValue.contains(expectedHeaderValue), "The header value is not as expected!");
		System.out.println("The value of header " + expectedHeaderName + "is: " + headerValue);
    }

    @Test(description = "Check Response Body to have a length of 10 users")
    public void checkBodyLength(){
		MyPojo[] user = response.as(MyPojo[].class);
		int numberOfUsers = user.length;
		System.out.println(user.toString());
		System.out.println("No: of users = " + numberOfUsers);
		Assert.assertEquals(numberOfUsers, expectedNumberOfUsers);
        }
    }