


/**
* 设置路由,强制规范了使用rqdto，和返回dto
* @param path
* @param dtoClz
* @param handlerFun
* @param server
*/
public static <T> void createContext4rest(String path, Class<T> dtoClz, Function<T, Object> handlerFun, HttpServer server) 


使用


        //   /p8?uname=123
        createContext4rest("/p8", QueryDto.class, dto1 -> hdlDto2(dto1), server);




    /**
     *
     * @param dto1
     * @return   apigatewayResponse
     */
    private static Object hdlDto2(QueryDto dto1) 