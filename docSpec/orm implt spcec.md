
if not use orm gene sql also ok,use migrate tool sql just ok...
then use oosql to insert ,query that also ok,hah 


# jpa model to Table(Column) model


#  table model 2 sql

public static String generateCreateTableSQL(Table table) {


# type map


private static String mapJavaTypeToMySQL(Column col) {
Class<?> type = col.getType();

        if (type == String.class) {
            long length = col.getLength() != null ? col.getLength() : 255;
            return "VARCHAR(" + length + ")";
        } else i