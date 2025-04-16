package util.log;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConsoleInterceptor {
    public static void init() {
        try {
            // 日志文件输出流
            FileOutputStream fileOut = new FileOutputStream("app.log", true);
            PrintStream customOut = new PrintStream(new MultiOutputStream(System.out, fileOut));
            PrintStream customErr = new PrintStream(new MultiOutputStream(System.err, fileOut));

            // 替换标准输出
            System.setOut(customOut);
            System.setErr(customErr);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 自定义 OutputStream，支持多输出
    static class MultiOutputStream extends OutputStream {
        private final OutputStream[] outputs;

        public MultiOutputStream(OutputStream... outputs) {
            this.outputs = outputs;
        }

        @Override
        public void write(int b) throws IOException {
            for (OutputStream out : outputs) {
                out.write(b);
            }
        }

        @Override
        public void flush() throws IOException {
            for (OutputStream out : outputs) {
                out.flush();
            }
        }

        @Override
        public void close() throws IOException {
            for (OutputStream out : outputs) {
                out.close();
            }
        }
    }
}
