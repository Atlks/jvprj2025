package orgx.uti.orm;

public class SortUti
{

  //db sort score
    // 计算字符串的排序分数
    private static int stringToScore(String input) {
        int score = 0;
        for (char c : input.toCharArray()) {
            score = score * 100 + c; // 让字符位置影响排序
        }
        return score;
    }
}
