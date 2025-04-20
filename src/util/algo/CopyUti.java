package util.algo;

import org.springframework.beans.BeanUtils;

public class CopyUti {


    /**
     * 复制属性
     * 将 from 对象的同名属性复制到 target 对象中
     *
     * @param from   来源对象
     * @param target 目标对象
     */
    public static void copyProp(Object from, Object target) {
        if (from == null || target == null) {
            throw new IllegalArgumentException("Source and target must not be null");
        }
        BeanUtils.copyProperties(from, target);
    }
}
