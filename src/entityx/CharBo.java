package entityx;

public class CharBo {

    public String ch;  //字符

    public String getCh() {
        return ch;
    }

    public int getStkcnt() {
        return stkcnt;
    }

    public String getIsGilrOnlyNameCh() {
        return isGilrOnlyNameCh;
    }

    public String getPinyin() {
        return pinyin;
    }

    public boolean isCanUseInName() {
        return CanUseInName;
    }

    public int stkcnt;  //笔画数
    public String isGilrOnlyNameCh = enm_boyname;  //是否女孩用字
    public String pinyin = "";
    public boolean CanUseInName = true;

    public static String enm_girlOnlyName = "女孩专用用字";
    public static String enm_boyname = "男孩可用";

    public void setCh(String ch) {
        this.ch = ch;
    }

    public void setStkcnt(int stkcnt) {
        this.stkcnt = stkcnt;
    }

    public void setIsGilrOnlyNameCh(String isGilrOnlyNameCh) {
        this.isGilrOnlyNameCh = isGilrOnlyNameCh;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public void setCanUseInName(boolean canUseInName) {
        CanUseInName = canUseInName;
    }
}
