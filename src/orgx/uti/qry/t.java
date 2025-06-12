package orgx.uti.qry;

import com.jiahua.springcloudwebadmin.entity.CenterUserBackpack;
import com.jiahua.springcloudwebadmin.utils.fp.FunctionX;

public class t {

    public static void main(String[] args) {
        eqx(CenterUserBackpack::getCreateTime,1);
    }

    private static <T,R>  void eqx(FunctionX<T, R> fld, int v) {
        System.out.println(toFldNameFrmMthd(fld.getLambdaMethodNameSmple()));
    }

    private static String toFldNameFrmMthd(String lambdaMethodNameSmple) {
        lambdaMethodNameSmple=lambdaMethodNameSmple.substring(3);
        return  FirstCharLower(lambdaMethodNameSmple);
    }


    //将第一个字符转化为小写
    private static String FirstCharLower(String lambdaMethodNameSmple) {

        if (lambdaMethodNameSmple == null || lambdaMethodNameSmple.isEmpty()) {
            return ""; // 处理空字符串或 null
        }

        char firstChar = lambdaMethodNameSmple.charAt(0);
        if (Character.isLowerCase(firstChar)) {
            return lambdaMethodNameSmple; // 如果已经是小写，则不需要转换
        }

        // 转换第一个字符为小写，并拼接剩余部分
        String modifiedString = Character.toLowerCase(firstChar) + lambdaMethodNameSmple.substring(1);

        System.out.println("转换后: " + modifiedString);
        return modifiedString; // 转换成功
    }
}
