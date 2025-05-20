package handler.uti;

import com.sun.net.httpserver.HttpExchange;
import entityx.usr.NonDto;
import handler.NoWarpApiRsps;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

import util.secury.CaptchaGenerator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import static util.misc.util2026.setCrossDomain;
import static util.oo.HttpUti.httpExchangeCurThrd;
import static util.secury.CaptchaGenerator.generate;

//
public class CaptchHdr {

    public static Map<String,String> Cptch_map=new HashMap<>();

    @PermitAll
@NoWarpApiRsps
    @Path("/api/captcha")
    @Produces("image/png")
    public   void getCptch(NonDto args) throws IOException {
        System.setProperty("jdk.net.spi.nameservice.provider.1", "default");
        CaptchaGenerator.Captcha captcha = generate();
        System.out.println("验证码问题: " + captcha.question);
        System.out.println("答案: " + captcha.answer); // 实际使用时不要返回答案！


        HttpExchange exchange= httpExchangeCurThrd.get();
        String uidAuto=getUidFrmBrsr(exchange);
        Cptch_map.put(uidAuto, String.valueOf(captcha.answer));
        BufferedImage image = new BufferedImage(100, 40, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 100, 40);
        g.setColor(Color.BLACK);
        g.drawString(captcha.question, 10, 25);
// 将 image 写出为 PNG 返回给前端


        g.dispose();

        // 2. 写入 ByteArrayOutputStream
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        byte[] imageBytes = baos.toByteArray();

        // 3. 返回 Response
        String contentType = "image/png";


        // 设置 HTTP 响应头

        // 设置跨域响应头
        setCrossDomain(exchange);

        exchange.getResponseHeaders().set("Content-Disposition", " attachment; filename=\"captcha.png\"");
        exchange.getResponseHeaders().set("Content-Type", "image/png");

        exchange.getResponseHeaders().set("Cache-Control", "no-cache, no-store, must-revalidate");
        exchange.getResponseHeaders().set("Pragma", "no-cache");
        exchange.getResponseHeaders().set("Expires", "0");
        exchange.sendResponseHeaders(200, imageBytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(imageBytes);
        }
        return ;
    }

    //endfun getUidFrmBrsr() ret=0:0:0:0:0:0:0:1_ua:Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/136.0.0.0 Safari/537.36__accept:text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7
    public static String getUidFrmBrsr(HttpExchange exchange) {
        String ip = exchange.getRemoteAddress().getAddress().getHostAddress();
        String ua = exchange.getRequestHeaders().getFirst("User-Agent");
        String accept ="";  //bcs swaggui tet cant same
                //exchange.getRequestHeaders().getFirst("Accept");
// 生成 hash 指纹（可做匿名追踪，不是绝对唯一）
        String s = ip +"_ua:"+ ua +"__accept:"+accept;
        System.out.println("endfun getUidFrmBrsr() ret=" + s + " ");
        return s;

    }
}
