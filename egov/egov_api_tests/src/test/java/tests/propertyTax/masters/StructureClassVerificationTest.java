package tests.propertyTax.masters;

import builders.propertyTax.masters.RequestInfoBuilder;
import builders.propertyTax.masters.structureClass.CreateStructureClassRequestBuilder;
import builders.propertyTax.masters.structureClass.StructureClassesBuilder;
import com.jayway.restassured.response.Response;
import entities.requests.propertyTax.masters.RequestInfo;
import entities.requests.propertyTax.masters.structureClass.StructureClassRequest;
import entities.requests.propertyTax.masters.structureClass.StructureClasses;
import entities.responses.propertyTax.masters.structureClass.create.StructureClassResponse;
import org.testng.Assert;
import org.testng.annotations.Test;
import resources.propertyTax.masters.StructureClassResource;
import tests.BaseAPITest;
import utils.*;

import java.io.IOException;
import java.util.Arrays;

import static data.UserData.NARASAPPA;

public class StructureClassVerificationTest extends BaseAPITest {

    StructureClasses[] structureClasses;
    RequestInfo requestInfo;
    SearchHelper helper;

    public StructureClassVerificationTest(){
        structureClasses = new StructureClasses[1];
    }

    @Test(groups = {Categories.PTIS, Categories.SANITY})
    public void structureClassTest() throws IOException {
        LoginAndLogoutHelper.login1(NARASAPPA); // Login
        requestInfo = new RequestInfoBuilder().withAuthToken(scenarioContext.getAuthToken()).build();
        helper = new SearchHelper();
        StructureClassResponse create = createStructureClass();  //Create
        helper.searchStructureClassMaster(create);    //Search

        Arrays.fill(structureClasses,null);
        StructureClassResponse update = updateStructureClass(create.getStructureClasses()[0].getId());  //Update
        helper.searchStructureClassMaster(update);    //Search
        LoginAndLogoutHelper.logout1();  //Logout
    }

    private StructureClassResponse createStructureClass() throws IOException {
        new APILogger().log("Create StructureClass Master Started");
        structureClasses[0] = new StructureClassesBuilder().withName("Test_"+get3DigitRandomInt()).withCode(get3DigitRandomInt())
                .withNameLocal("Test_"+get3DigitRandomInt()).withOrderNumber(Integer.parseInt(get3DigitRandomInt())).build();
        StructureClassRequest request = new CreateStructureClassRequestBuilder().withRequestinfo(requestInfo)
                .withStructureClasses(structureClasses)
                                        .build();

        Response response = new StructureClassResource().create(RequestHelper.getJsonString(request));
        StructureClassResponse response1 = checkAsserts(request,response);
        new APILogger().log("Create StructureClass Master Completed");
        return response1;
    }

    private StructureClassResponse checkAsserts(StructureClassRequest request, Response response) throws IOException{
        StructureClassResponse response1 = (StructureClassResponse)
                ResponseHelper.getResponseAsObject(response.asString(), StructureClassResponse.class);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response1.getStructureClasses()[0].getName(), request.getStructureClasses()[0].getName());
        Assert.assertEquals(response1.getStructureClasses()[0].getCode(), request.getStructureClasses()[0].getCode());
        Assert.assertEquals(response1.getStructureClasses()[0].getNameLocal(), request.getStructureClasses()[0].getNameLocal());
        Assert.assertEquals(response1.getStructureClasses()[0].getOrderNumber(), request.getStructureClasses()[0].getOrderNumber());
        Assert.assertEquals(response1.getResponseInfo().getStatus(), "SUCCESSFUL");
        return response1;
    }

    private StructureClassResponse updateStructureClass(int id) throws IOException{
        new APILogger().log("Update StructureClass Master Started");
        structureClasses[0] = new StructureClassesBuilder().withId(id).withName("Test_"+get3DigitRandomInt()).withCode(get3DigitRandomInt())
                .withNameLocal("Test_"+get3DigitRandomInt()).build();
        StructureClassRequest request = new CreateStructureClassRequestBuilder().withRequestinfo(requestInfo)
                .withStructureClasses(structureClasses)
                .build();

        Response response = new StructureClassResource().update(RequestHelper.getJsonString(request));
        StructureClassResponse response1 = checkAsserts(request,response);
        new APILogger().log("Update StructureClass Master Completed");
        return response1;
    }
}