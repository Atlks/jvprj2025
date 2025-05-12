package Usr;

import static cfg.IniCfg.iniContnr4cfgfile;

import static cfg.MainStart.iniContnr;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static util.algo.CallUtil.callTry;
import static util.algo.GetUti.getUuid;
import static util.evtdrv.EvtUtil.iniEvtHdrCtnr;
import static util.misc.Util2025.encodeJson;
import static util.orm.HbntExt.migrateSql;
import static util.tx.TransactMng.commitTsact;
import static util.tx.TransactMng.openSessionBgnTransact;


import cfg.Containr;
import cfg.MainStart;
import handler.usr.RegHandler;
import handler.usr.dto.RegDto;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import util.serverless.ApiGatewayResponse;

import java.io.FileNotFoundException;
import java.sql.SQLException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RegHandlerTest {

    private RegHandler regHandler=new RegHandler();
    private RegDto mockDto=  new RegDto();
   // private Context mockContext;

@BeforeAll
    public   void setup() throws Exception {
         //    mockContext = mock(Context.class);  // 模拟Lambda上下文对象
    Containr.testUnitMode=true;
        iniContnr4cfgfile();

    callTry(() -> migrateSql());
        new MainStart().sessionFactory();//ini sessFctr ..


        //ini contnr 4cfg,, svrs
        //ioc ini
        iniContnr();
        iniEvtHdrCtnr();



    }
    @BeforeEach
    public void setupBefEach() throws Exception {
        openSessionBgnTransact();

    }

    @AfterEach
    public void setupAftEach() throws Exception {
        commitTsact();
    }

    @Test
    public void testHandleRequest_Success() throws Throwable {
        // 设置模拟对象的返回值
     //   when(mockDto.uname).thenReturn("666");
      //  when(mockDto.pwd).thenReturn("666");// 假设RegDto有一个名为getSomeField的方法
        mockDto.uname="666"+getUuid();
        mockDto.pwd="666";
        // 调用handleRequest方法
        Object response =  regHandler.handleRequest(mockDto);

        System.out.println(encodeJson(response));
        // 验证结果
        assertNotNull(response, "Response should not be null");
       // assertEquals(200, response.getStatusCode(), "Status code should be 200");
      //  assertEquals("expectedMessage", response.getBody(), "Response body should match expected message");
    }

   // @Test
    public void testHandleRequest_ExceptionThrown() throws Throwable {
        // 模拟RegDto的异常情况，确保handleRequest处理了异常
        when(mockDto.getUname()).thenThrow(new RuntimeException("Test Exception"));

        // 调用方法并捕获异常
        assertThrows(RuntimeException.class, () -> {
            regHandler.handleRequest(mockDto);
        });
    }

    // 可以根据需要添加更多的测试用例，例如测试不同的输入数据或异常情况
}
