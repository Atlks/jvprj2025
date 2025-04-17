package util.serverless;

import jakarta.ws.rs.core.Context;


/**
 * （来自 AWS SDK）
 *
 * I	input	输入数据（通常是 Map、POJO、JSON 等）
 * Context	context	Lambda 的上下文信息（如请求 ID、日志功能等）
 * O	返回值	输出结果（可返回 Map、String、自定义类等）
 *
 * 当你用 API Gateway 调用 Lambda 时，它会把请求结构封装成一大个 Map（包括 body, queryStringParameters, headers, pathParameters, 等）
 * @param <DTO>
 * @param <RspObj>
 */
public interface RequestHandler<DTO, RspObj> {
    RspObj handleRequest(DTO param, Context context) throws Throwable;
}
