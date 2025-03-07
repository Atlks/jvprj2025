

encodeParam  
cvt to dto  ,,,use int type...




var sql = "select * from ordbet where uname ="+encodeParamSql(dto.uname)+" order by timestamp desc limit  "+dto.limit+" offset "+dto.offset ;
       

