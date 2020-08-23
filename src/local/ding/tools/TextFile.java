package local.ding.tools;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;

/**
 * 这个类添加很多静态方法用来简化IO 文件写入写出操作
 */
public class TextFile extends ArrayList<String> {
    /**
     * 从一个文件中读取所有的内容，转成String返回
     * @param fileName
     * @return
     */
    public static String read(String fileName) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader in  = new BufferedReader(new FileReader(
                    new File(fileName).getAbsoluteFile()));
            try {
                String s;
                while ((s = in.readLine()) != null) {
                    sb.append(s);
                    sb.append("\n");
                }
            } finally {
                in.close();
            }
        } catch (IOException e ) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

    /**
     * 将text写入到fileName对应的文件中
     * @param fileName
     * @param text
     */
    public static void write(String fileName, String text) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(
               new File(fileName).getAbsoluteFile()
            ));
            try {
                bw.write(text);
            } finally {
                bw.close();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public TextFile(String fileName, String splitter) {
        super(Arrays.asList(read(fileName).split(splitter)));
        //spit会保留第一个是空字符串
        if (get(0).equals("")) remove(0);
    }

    public TextFile(String fileName) {
        this(fileName, "\n");
    }

    /**
     * 将内容输出到文件中
     * @param fileName
     */
    public void write(String fileName) {
        try {
            //已实现了缓存
            PrintWriter out = new PrintWriter(new File(fileName).getAbsoluteFile());
            try {
                for (String item : this) {
                    out.println(item);
                }
            } finally {
                out.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        String file = read("day1-code/src/local/ding/tools/TextFile.java");
//        System.out.println(file);
        write("text.txt", file);
        TextFile text = new TextFile("text.txt");
        text.write("text2.txt");
        TreeSet<String> words = new TreeSet<>(new TextFile("text.txt", "\\W+"));
        System.out.println(words.headSet("a"));
    }
}
