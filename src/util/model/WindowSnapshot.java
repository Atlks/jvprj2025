package util.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Data;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.List;

import static util.algo.CallUtil.exec;
import static util.algo.ParseUtil.parseJsonFil2obj;

//import static jdk.jpackage.internal.IOUtils.exec;


@Data
public class WindowSnapshot {


    public String name = "WindowSnapshot";
    @JsonProperty("timestamp")
    private LocalDateTime timestamp;

    @JsonProperty("WindowProcessList")
    private List<WindowProcess> windows;





    public static void restoreWinPrcs() throws IOException {
        String f = "C:\\work\\last_state.json";
        WindowSnapshot snp =parseJsonFil2obj(f, WindowSnapshot.class);
        for (WindowSnapshot.WindowProcess prcs : snp.getWindows()) {
            String cmd = prcs.getCommandLine();
            exec((cmd));
            //   exec()
            //exec(false, cmd);
        }
    }



    @Override
    public String toString() {
        return "WindowSnapshot{" +
                "timestamp=" + timestamp +
                ", windows=" + windows +
                '}';
    }

    // 内部类 Window 表示每个窗口进程
    @Data
    public static class WindowProcess {

        @JsonProperty("Name")
        private String name;

        @JsonProperty("MainWindowTitle")
        private String mainWindowTitle;

        @JsonProperty("CommandLine")
        private String commandLine;

        @JsonProperty("ProcessId")
        private int processId;


    }
}
