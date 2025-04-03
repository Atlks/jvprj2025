package util.algo;

import java.util.ArrayList;
import java.util.List;

public class CutJoin {

    /**这个函数的作用是将 bodyBytes 按照 boundaryBytes 进行分割，并返回一个 List<byte[]>，其中每个 byte[] 是一个分割后的部分。
     * 流程是
     * 遍历 bodyBytes 并查找 boundaryBytes 的位置
     *
     * 记录每个分段的起始索引
     *
     * 将分段的字节数组存入 List<byte[]> 并返回
     * @param bodyBytes
     * @param boundaryBytes
     * @return
     */
    public static List<byte[]> splitByBytearray(byte[] bodyBytes, byte[] boundaryBytes) {

        List<byte[]> parts = new ArrayList<>();
        int start = 0;
        int index;

        while ((index = indexOf(bodyBytes, boundaryBytes, start)) != -1) {
            // 提取从 start 到 boundary 开始的位置的部分
            if (index > start) {
                byte[] part = new byte[index - start];
                System.arraycopy(bodyBytes, start, part, 0, part.length);
                parts.add(part);
            }
            // 跳过 boundary
            start = index + boundaryBytes.length;
        }

        // 添加最后一部分（如果有）
        if (start < bodyBytes.length) {
            byte[] part = new byte[bodyBytes.length - start];
            System.arraycopy(bodyBytes, start, part, 0, part.length);
            if(part.length>0)
                parts.add(part);
        }

        return parts;
    }
}
