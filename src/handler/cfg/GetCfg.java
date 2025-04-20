package handler.cfg;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import model.cfg.CfgKv;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import util.serverless.ApiGatewayResponse;
import util.serverless.RequestHandler;

import static cfg.AppConfig.sessionFactory;
import static util.tx.HbntUtil.findByHerbinate;

/**  GetCfg kv mode
 *     /admin/cfg/GetCfg?k=rechargeCommissionRates&v={}
 *
 * /cfg/GetCfg?k=DataSummary
 *     DataSummary
 */
@RestController
@Path("/cfg/GetCfg")
//@GetMapping("/admin/cfg/GetCfg")
@PermitAll
public class GetCfg implements RequestHandler<CfgKv, ApiGatewayResponse> {
    /**
     * @param reqDto
     * @param context
     * @return
     * @throws Throwable
     */
    @Override
    public ApiGatewayResponse handleRequest(CfgKv reqDto, Context context) throws Throwable {
     //   reqDto.id="uniqID";
        CfgKv c=   findByHerbinate(CfgKv.class,reqDto.k,sessionFactory.getCurrentSession());
        var vo=c.v;
        var jsonObjOrArr=toJsonObjectOrArrayUseGson(vo);
        return new ApiGatewayResponse(jsonObjOrArr);
    }


    /**
     * 转换为json对象或数组 ,使用gson
     * @param vo
     * @return
     */
    private Object toJsonObjectOrArrayUseGson(String vo) {
        if (vo == null || vo.trim().isEmpty()) {
            return null;
        }

        try {
            JsonElement element = JsonParser.parseString(vo.trim());
            if (element.isJsonObject()) {
                return element.getAsJsonObject();
            } else if (element.isJsonArray()) {
                return element.getAsJsonArray();
            }
        } catch (Exception e) {
            e.printStackTrace(); // 可换成日志记录
        }

        return null;
    }
        /**
         * 转换为json对象或数组
         * @param vo
         * @return
         */
        @Deprecated
    private Object toJsonObjectOrArray(String vo) {
        if (vo == null || vo.trim().isEmpty()) {
            return null;
        }

        vo = vo.trim();
        try {
            if (vo.startsWith("{")) {
                return new JSONObject(vo);
            } else if (vo.startsWith("[")) {
                return new JSONArray(vo);
            }
        } catch (Exception e) {
            e.printStackTrace(); // 你也可以抛出异常或记录日志
        }

        return null;
    }
}
