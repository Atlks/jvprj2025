package util.algo;

public class IndexOfUti {

    public static int indexOfFirst(byte[] data, byte[] pattern)
    {
        return  indexOf(data,pattern,0);
    }
    // 在 byte[] 中查找子数组的索引
    public static int indexOf(byte[] data, byte[] pattern, int start) {
        outer:
        for (int i = start; i <= data.length - pattern.length; i++) {
            for (int j = 0; j < pattern.length; j++) {
                if (data[i + j] != pattern[j]) {
                    continue outer;
                }
            }
            return i; // 找到匹配项
        }
        return -1; // 未找到
    }
}
