package local.ding.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class OSExecute {
    /**
     * 执行命令
     * @param command
     */
    public static void command(String command) {
        try {
            Process process = new ProcessBuilder(Arrays.asList(command.split(" "))).start();
            //标准输入流
            BufferedReader in =  new BufferedReader(new InputStreamReader(process.getInputStream()));
            String s = in.readLine();
            while (s != null) {
                System.out.println(s);
                s = in.readLine();
            }
            //标准错误输出
            BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            s = error.readLine();
            while (s != null) {
                System.err.println(s);
                s = in.readLine();
            }

        } catch (Exception e) {
            //如果是在window下面执行的命令加上 cmd /C
            if (!command.startsWith("CMD /C"))
                command("CMD /C " + command);
            else
                throw new RuntimeException(e.getMessage());
        }
    }

    public static void main(String[] args) {
        command("ls -al /Users/dingmac/Desktop");
    }
}
