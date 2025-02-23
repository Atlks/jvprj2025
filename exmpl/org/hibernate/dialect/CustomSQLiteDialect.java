//package org.hibernate.dialect;
//
//import org.hibernate.boot.model.FunctionContributions;
//import org.hibernate.boot.model.TypeContributions;
//import org.hibernate.query.sqm.function.SqmFunctionRegistry;
//import org.hibernate.service.ServiceRegistry;
//import org.hibernate.type.descriptor.sql.internal.DdlTypeImpl;
//import org.hibernate.type.descriptor.sql.internal.NativeEnumDdlTypeImpl;
//import org.hibernate.type.descriptor.sql.internal.NativeOrdinalEnumDdlTypeImpl;
//import org.hibernate.type.descriptor.sql.spi.DdlTypeRegistry;
//import org.hibernate.type.spi.TypeConfiguration;
//
///**
// * 编写自定义 Dialect（处理 SQL 语法和类型）
// */
//public class CustomSQLiteDialect extends Dialect {
//    public CustomSQLiteDialect() {
//        super(DatabaseVersion.make(3)); // 指定 SQLite 版本
//
//        // 注册基本数据类型
////        getTypeConfiguration().standardBasicTypeForJavaType(String.class).addSqlType(SqlTypes.VARCHAR);
////        getTypeConfiguration().standardBasicTypeForJavaType(Integer.class).addSqlType(SqlTypes.INTEGER);
////        getTypeConfiguration().standardBasicTypeForJavaType(Double.class).addSqlType(SqlTypes.DOUBLE);
////        getTypeConfiguration().standardBasicTypeForJavaType(byte[].class).addSqlType(SqlTypes.BLOB);
//    }
//    protected String columnType(int sqlTypeCode) {
//        switch (sqlTypeCode) {
//            case -15:
//                return this.columnType(1);
//            case -9:
//                return this.columnType(12);
//            default:
//                return super.columnType(sqlTypeCode);
//        }
//    }
//    protected Integer resolveSqlTypeCode(String typeName, String baseTypeName, TypeConfiguration typeConfiguration) {
//        switch (baseTypeName) {
//            case "CHARACTER VARYING":
//                return 12;
//            default:
//                return super.resolveSqlTypeCode(typeName, baseTypeName, typeConfiguration);
//        }
//    }
//    protected Integer resolveSqlTypeCode(String columnTypeName, TypeConfiguration typeConfiguration) {
//        switch (columnTypeName) {
//            case "FLOAT(24)":
//                return 7;
//            default:
//                return super.resolveSqlTypeCode(columnTypeName, typeConfiguration);
//        }
//    }
//    protected void registerColumnTypes(TypeContributions typeContributions, ServiceRegistry serviceRegistry) {
//        super.registerColumnTypes(typeContributions, serviceRegistry);
//        DdlTypeRegistry ddlTypeRegistry = typeContributions.getTypeConfiguration().getDdlTypeRegistry();
//        ddlTypeRegistry.addDescriptor(new DdlTypeImpl(3000, "uuid", this));
//        ddlTypeRegistry.addDescriptor(new DdlTypeImpl(3200, "geometry", this));
//        ddlTypeRegistry.addDescriptor(new DdlTypeImpl(3100, "interval second($p,$s)", this));
//        ddlTypeRegistry.addDescriptor(new DdlTypeImpl(3001, "json", this));
//        ddlTypeRegistry.addDescriptor(new NativeEnumDdlTypeImpl(this));
//        ddlTypeRegistry.addDescriptor(new NativeOrdinalEnumDdlTypeImpl(this));
//    }
//    protected String castType(int sqlTypeCode) {
//        switch (sqlTypeCode) {
//            case -15:
//            case 1:
//                return "char";
//            case -9:
//            case 12:
//            case 4001:
//            case 4002:
//                return "varchar";
//            case -3:
//            case -2:
//            case 4003:
//                return "varbinary";
//            default:
//                return super.castType(sqlTypeCode);
//        }
//    }
//
//    public void initializeFunctionRegistry(SqmFunctionRegistry functionRegistry) {
//        super.initializeFunctionRegistry((FunctionContributions) functionRegistry);
//
//        // 注册 SQL 函数
////        functionRegistry.namedDescriptorBuilder("concat")
////                .setInvariant(new SqlFunctionTemplate(StandardBasicTypes.STRING, "?1 || ?2"))
////                .register();
////
////        functionRegistry.namedDescriptorBuilder("length")
////                .setInvariant(new SqlFunctionTemplate(StandardBasicTypes.INTEGER, "length(?1)"))
////                .register();
////
////        functionRegistry.namedDescriptorBuilder("substr")
////                .setInvariant(new SqlFunctionTemplate(StandardBasicTypes.STRING, "substr(?1, ?2, ?3)"))
////                .register();
//    }
//}
//
