package handler.uti;

import com.sun.net.httpserver.HttpExchange;
import entityx.NonDto;
import handler.usr.RegDto;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import util.algo.Icall;
import util.ex.existUserEx;
import util.serverless.ApiGatewayResponse;
import util.serverless.RequestHandler;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static util.algo.CutJoin.splitByBytearray;
import static util.algo.CutJoin.subByte;
import static util.algo.GetUti.*;
import static util.algo.JarClassScanner.getPrjPath;
import static util.algo.SaveUti.saveFile;
import static util.algo.ParsePostFileUti.*;
import static util.serverless.ApiGateway.httpExchangeCurThrd;

/**
 * login
 * //@param uname
 * //@param pwd
 */
@RestController


//组合了 @Controller 和 @ResponseBody，表示该类是 REST API 控制器，所有方法的返回值默认序列化为 JSON 或 XML。
@PermitAll
@Path("/uploads")
//   http://localhost:8889/login?uname=008&pwd=000
@NoArgsConstructor
@Data
public class UpldHr  implements RequestHandler<NonDto, ApiGatewayResponse> {

    /** upload file
     * @param param
     * @param context
     * @return
     * @throws Throwable
     */
    @Override
    public ApiGatewayResponse handleRequest(NonDto param, Context context) throws Throwable {
        HttpExchange httpExchange = httpExchangeCurThrd.get();
        HttpExchange exchange = httpExchange;
        String uploadDir = getPrjPath() + "/res/uploads";
        //  mkdir2025(uploadDir+"/xxx.jpg");
        // 确保上传目录存在
        Files.createDirectories(Paths.get(uploadDir));


        // 提取 Content-Type 以获取 boundary
        String boundary = getBoundary(exchange);
        if (boundary.equals("")) {
            exchange.sendResponseHeaders(400, 0); // Bad Request
            return  new ApiGatewayResponse("") ;
        }

        // 生成唯一文件名
        String newFileName = getFilename(uploadDir, "upload_");

        // 处理上传的文件
        // 读取请求体并提取文件内容
        InputStream inputStream = exchange.getRequestBody();
        parseSingleFile(inputStream, boundary, uploadDir, newFileName);


        // 发送响应
        String rltpath = "/res/uploads/" + newFileName;
        return  new ApiGatewayResponse(rltpath) ;
    }

    /**
     * upload file
     *
     * @return file path
     * @throws Exception
     * @throws existUserEx
     */
//    @Override
//    public Object main(@BeanParam NonDto usr_dto) throws Exception {
//
//
//    }
//

    /**
     * post data
     * ------geckoformboundary34b49dbfd9a4a64fa922e966447ac390
     * Content-Disposition: form-data; name="file"; filename="download (1).jpg"
     * Content-Type: image/jpeg
     * <p>
     * fgsfgsfg
     * ------geckoformboundary34b49dbfd9a4a64fa922e966447ac390--
     */
    public static void parseSingleFile(@NotNull InputStream inputStream, String boundary, String outputDir, String newFileName) throws IOException {
        // Step 1: Read the request body and split by boundary
        byte[] boundaryBytes = boundary.getBytes("UTF-8");
        byte[] bodyBytes = getBytes4bodyPost(inputStream);

        List<byte[]> parts = splitByBytearray(bodyBytes, boundaryBytes);


        for (byte[] part : parts) {
            byte[] partHead = subByte(part, 3000);
            var partStr = new String(partHead, "utf-8");
            if (partStr.contains("Content-Disposition")) {
                String filename = getFilenameFromUpdtStream(partStr);
                System.out.println(filename);

                // Extract the file content (after the headers)
                byte[] fileContentBytes = getBytesFilecontext(part);

                saveFile(fileContentBytes, outputDir, newFileName);
                break; // Only one file, so we can stop after saving it
            }
        }
    }




    // Save the file to the output directory
    // 生成唯一文件名
//                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmssSSS").format(new Date());
//                String newFileName = "upload_" + timeStamp + ".jpg";
    //   java.nio.file. Path filePath = Paths.get(uploadDir, newFileName);


}
