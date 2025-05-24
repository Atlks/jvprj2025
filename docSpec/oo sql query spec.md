

# 使用 WHERE 条件查询
   go
   Copy
   Edit
   db.Where("username = ?", "alice").Find(&users)

# 使用 Struct 条件 qry
go
Copy
Edit
db.Where(&User{Username: "alice"}).First(&user)

# 5. 多条件查询
jooq model
    query.addConditions(Transaction.Fields.timestamp+">",beforeTmstmp(reqdto.day));


7. 分页查询
   go
   Copy
   Edit
   var users []User
   db.Limit(10).Offset(20).Find(&users) // 从第21条开始取10条