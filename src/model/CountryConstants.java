package model;


import java.util.Map;

/**
 * 国家的常量表，使用三个字母模式
 * 使用国际通用的三个字母国家代码（ISO 3166-1 alpha-3）作为常量：
 */
public class CountryConstants {
    public static final String  glb="glb";
    public static final String  uke="uke";

    public static final String CHINA = "CHN";
    public static final String UNITED_STATES = "USA";
    public static final String JAPAN = "JPN";
    public static final String UNITED_KINGDOM = "GBR";
    public static final String GERMANY = "DEU";
    public static final String FRANCE = "FRA";
    public static final String CANADA = "CAN";
    public static final String AUSTRALIA = "AUS";
    public static final String INDIA = "IND";
    public static final String RUSSIA = "RUS";
    public static final String SOUTH_KOREA = "KOR";
    public static final String BRAZIL = "BRA";
    public static final String SINGAPORE = "SGP";
    public static final String THAILAND = "THA";
    public static final String PHILIPPINES = "PHL";
    public static final String MALAYSIA = "MYS";
    public static final String INDONESIA = "IDN";
    public static final String VIETNAM = "VNM";
    public static final String AFGHANISTAN = "AFG";     // 阿富汗
    public static final String ARMENIA = "ARM";         // 亚美尼亚
    public static final String AZERBAIJAN = "AZE";      // 阿塞拜疆
    public static final String BAHRAIN = "BHR";         // 巴林
    public static final String BANGLADESH = "BGD";      // 孟加拉国
    public static final String BHUTAN = "BTN";          // 不丹
    public static final String BRUNEI = "BRN";          // 文莱
    public static final String CAMBODIA = "KHM";        // 柬埔寨

    public static final String CYPRUS = "CYP";          // 塞浦路斯
    public static final String GEORGIA = "GEO";         // 格鲁吉亚

    public static final String IRAN = "IRN";            // 伊朗
    public static final String IRAQ = "IRQ";            // 伊拉克
    public static final String ISRAEL = "ISR";          // 以色列

    public static final String JORDAN = "JOR";          // 约旦
    public static final String KAZAKHSTAN = "KAZ";      // 哈萨克斯坦
    public static final String KUWAIT = "KWT";          // 科威特
    public static final String KYRGYZSTAN = "KGZ";      // 吉尔吉斯斯坦
    public static final String LAOS = "LAO";            // 老挝
    public static final String LEBANON = "LBN";         // 黎巴嫩

    public static final String MALDIVES = "MDV";        // 马尔代夫
    public static final String MONGOLIA = "MNG";        // 蒙古
    public static final String MYANMAR = "MMR";         // 缅甸
    public static final String NEPAL = "NPL";           // 尼泊尔
    public static final String NORTH_KOREA = "PRK";     // 朝鲜
    public static final String OMAN = "OMN";            // 阿曼
    public static final String PAKISTAN = "PAK";        // 巴基斯坦
    public static final String PALESTINE = "PSE";       // 巴勒斯坦
        // 韩国
    public static final String SRI_LANKA = "LKA";       // 斯里兰卡
    public static final String SYRIA = "SYR";           // 叙利亚
    public static final String TAJIKISTAN = "TJK";      // 塔吉克斯坦

    public static final String TIMOR_LESTE = "TLS";     // 东帝汶
    public static final String TURKMENISTAN = "TKM";    // 土库曼斯坦
    public static final String UNITED_ARAB_EMIRATES = "ARE"; // 阿联酋
    public static final String UZBEKISTAN = "UZB";      // 乌兹别克斯坦

    public static final String YEMEN = "YEM";           // 也门

    // 可选：用于获取中文名描述
    public static final Map<String, String> COUNTRY_NAME_MAP = Map.ofEntries(
            Map.entry(CHINA, "中国"),
            Map.entry(UNITED_STATES, "美国"),
            Map.entry(JAPAN, "日本"),
            Map.entry(UNITED_KINGDOM, "英国"),
            Map.entry(GERMANY, "德国"),
            Map.entry(FRANCE, "法国"),
            Map.entry(CANADA, "加拿大"),
            Map.entry(AUSTRALIA, "澳大利亚"),
            Map.entry(INDIA, "印度"),
            Map.entry(RUSSIA, "俄罗斯"),
            Map.entry(SOUTH_KOREA, "韩国"),
            Map.entry(BRAZIL, "巴西"),
            Map.entry(SINGAPORE, "新加坡"),
            Map.entry(THAILAND, "泰国"),
            Map.entry(PHILIPPINES, "菲律宾"),
            Map.entry(MALAYSIA, "马来西亚"),
            Map.entry(INDONESIA, "印尼"),
            Map.entry(VIETNAM, "越南")
    );

}
