package entityx;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CharBo {

    public String ch;  //字符
    @Nullable
    public String pinyinWzTone;
    public boolean boynameCanUse=true;
    public   boolean canUseInName;

    public boolean isBoynameCanUse() {
        return boynameCanUse;
    }

    public void setBoynameCanUse(boolean boynameCanUse) {
        this.boynameCanUse = boynameCanUse;
    }

    @NotNull
    public String getPinyinNoTone() {
        return pinyinNoTone;
    }

    @Nullable
    public String getPinyinWzTone() {
        return pinyinWzTone;
    }

    public boolean isSmpPy() {
        return isSmpPy;
    }

    public void setSmpPy(boolean smpPy) {
        isSmpPy = smpPy;
    }

    @NotNull
    public String pinyinNoTone;
    public boolean isSmpPy=false;

    public String getCh() {
        return ch;
    }

    public void setPinyinWzTone(@Nullable String pinyinWzTone) {
        this.pinyinWzTone = pinyinWzTone;
    }

    public void setPinyinNoTone(@NotNull String pinyinNoTone) {
        this.pinyinNoTone = pinyinNoTone;
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
