

    public Map<String, Column> linkedHashMap = new LinkedHashMap();

   public String getSQL() {

        return "insert into "+tableName+" ("+trim4sql(linkedHashMap.keySet().toString()) +") values ("+trim4sql(linkedHashMap.values().toString())+") ";
    }

    /**
     * 去除左右俩边各一个字符
     * @param s
     * @return
     */
    private Object trim4sql(String s) {
        if (s == null || s.length() <= 2) {
            return "";
        }
        return s.substring(1, s.length() - 1);
    }



Colunmn.class


    @Override
    public String toString() {
        if (getType() == String.class) {
            String valueSafeEncode = value.toString().replaceAll("'", "''");
            return "'" + valueSafeEncode + "'";
        } else
            return String.valueOf(value);
//        return String.format("DbField{name='%s', label='%s', value=%s, type=%s}",
//                name, label, value, type.getSimpleName());
    }