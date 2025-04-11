


public void handle(HttpExchange exchange)

//============aop trans begn
openSessionBgnTransact();

            //---------blk chk auth
            urlAuthChkV2(exchange);



var dto = toDto(exchange, cls);
validDto(dto);
log warp( all);


            commitTsact();