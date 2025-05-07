package model.review;

public enum ReviewStat {
    Completed,
    Pending,
        CANCELLED,
        REJECTED,OTH;

    /**
     * 根据字符串 code 获取对应枚举，默认返回 OTH（其他）
     */
    public static ReviewStat fromCodeStr_ReviewStat(String code) {
        for (ReviewStat tc : values()) {
            if (tc.name().equalsIgnoreCase(code)) {
                return tc;
            }
        }
        return OTH;
    }



}
