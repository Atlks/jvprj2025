package ztest;

import io.restassured.RestAssured;
import io.restassured.filter.Filter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.javalin.util.FileUtil.readFile;
import static io.restassured.RestAssured.*;
import static util.algo.JarClassScanner.getPrjDir;

public class TestRest {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "http://localhost:888";
        String token=readFile(getPrjDir()+"/test/jwt.txt");
        Filter authFilter = (requestSpec, responseSpec, ctx) -> {
            requestSpec.header("Authorization", "Bearer " + token);
            return ctx.next(requestSpec, responseSpec);
        };

// 所有请求自动添加 JWT
        RestAssured.filters(authFilter, new RequestLoggingFilter(), new ResponseLoggingFilter());
    }


    @Test
    void testQryFundDetailHdl() {
        String path = "/apiv1/wlt/QryFundDetailHdl";
        testUrlStatCodeIs200(given()
                .when().get(
                        path));
    }
    private static void testUrlStatCodeIs200(Response path) {
        path
                .then().statusCode(200)
        ;
    }


    //http://localhost:888/apiv1/password/reset/request?username=666
    //http://localhost:888/apiv1/password/reset/confirm
    //{{baseUrl}}/apiv1/myWltAdrs/ReviewWltAdrsHdl?id=666&stat=Completed
    // http://localhost:888/apiv1/adm/updtAdmStat?uname=admin001&enable=true
    //apiv1/invstAcc/me
    @Test
    void testInvsAccMe() {
        String path = "/apiv1/invstAcc/me";
        testUrlStatCodeIs200(given()
                .when().get(
                        path));
    }




    @Test
    void testGetUsers2() {
        String token="";
        testUrlStatCodeIs200(given().header("Authorization", "Bearer " + token).formParam("name", "nm1frmTst") // 发送表单参数
                .when().post("/pst?name=nm1"));
    }

//    @Test
//    void testCreateUser() {
//        given()
//                .contentType("application/json")
//                .body("{ \"name\": \"Alice\", \"age\": 25 }")
//                .when().post("/users")
//                .then().statusCode(200)
//                .body("name", equalTo("Alice"))
//                .body("age", equalTo(25));
//    }
}
