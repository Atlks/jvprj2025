package orgx;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExecutionTimeCalculator {
    public static void main(String[] args) throws IOException {
        //100w 3m,,, 1kw,30M,,1y 300M
        long startTime = System.currentTimeMillis(); // 记录开始时间
        Long sum = 0L;
        List<String> numberList = new ArrayList<>();
        String file = "t.txt";
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        for (int i = 0; i <= 100_000_000; i++) {
           // numberList.add(String.valueOf(i));
            sum += i;

            write(String.valueOf(i), writer);
        }
        System.out.println(sum);

        long endTime = System.currentTimeMillis(); // 记录结束时间

        System.out.println("函数执行时间：" + (endTime - startTime) + " 毫秒");
    }

    /**
     *
     *for bug file,,gb lev
     * @param dataq
     * @param writer
     */
    private static void write(String dataq, BufferedWriter writer) throws IOException {
       // System.out.println("<UNK>" + file + "<UNK>");


                writer.write(dataq);
                writer.newLine(); // 换行

         //   System.out.println("数据已成功写入到文件：" + file);


    }

}
