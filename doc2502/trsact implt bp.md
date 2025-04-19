

事务机制的实现


不要卸载service里面，容易嵌套事务

写在  api 层里面。。


//basehdr.kt
//-----------------stat trans action
//  System.out.println("▶\uFE0Ffun handle2(HttpExchange)");
Session session=sessionFactory.getCurrentSession();
session.beginTransaction();
handle2(exchange);
commitTransaction(session);


==store use orm hbnt 
easy ztest in sqlt
also can implt ini store ...use hbnt api
