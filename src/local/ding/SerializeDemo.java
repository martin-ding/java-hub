package local.ding;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SerializeDemo {
    public static void main(String[] args) throws Exception {
        autoSer();
    }

    public static void autoSer() throws Exception {
       A a = new A();
       a.setColor("red");
       a.setName("redOa");
        ByteArrayOutputStream bos =  new ByteArrayOutputStream();
        ObjectOutputStream oos =  new ObjectOutputStream(bos);

        oos.writeObject(a);
        A.serState(oos);
        oos.flush();

        a.setColor("HAU ");
        a.setName("HAS");


        ObjectInputStream ois =  new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
        A aa  = (A)ois.readObject();
        A.getState(ois);
        System.out.println(aa);

    }
}

class A implements Serializable{
    private static String color;
    private String name;
    public static void serState(ObjectOutputStream oos) throws IOException {
        oos.writeObject(color);
    }

    public static void getState(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        color =  (String)ois.readObject();
    }
    A()
    {
        color = "zha";
        name = "hehde";
    }
    public void setColor(String co) {
        color = co;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return color + " " + name;
    }
}
class B extends A {
    private static String color;

}

class FileSplitMerge {
    //拆分文件
    public static void splitFile(String filePath, int fileCount) throws IOException {
        FileInputStream fis = new FileInputStream(filePath);
        FileChannel inputChannel = fis.getChannel();
        final long fileSize = inputChannel.size();
        long average = fileSize / fileCount;//平均值
        long startPosition = 0; //子文件开始位置
        long endPosition = average;//子文件结束位置

        for (int i = 0; i < fileCount; i++) {
            if (i + 1 == fileCount) {//不是最后一个文件
                endPosition = fileSize; //最后一个文件直接指向文件末尾
            }

            //写文件
            FileOutputStream fos = new FileOutputStream(filePath + (i + 1));
            FileChannel outputChannel = fos.getChannel();
            inputChannel.transferTo(startPosition, endPosition - startPosition, outputChannel);//通道传输文件数据
            outputChannel.close();
            fos.close();
            startPosition = endPosition;//这边不需要+1
            endPosition += average;
        }
        inputChannel.close();
        fis.close();
    }

    /**
     * 合并文件
     */
    public static void mergeFile(String subfileName, String endfileName, int num) throws Exception {
        FileOutputStream fos = new FileOutputStream(endfileName);
        FileChannel ofc = fos.getChannel();
        for (int i = 0; i < num; i++) {
            FileInputStream fio = new FileInputStream(subfileName + (i + 1));
            FileChannel cfio = fio.getChannel();
            long tnum = cfio.transferTo(0, cfio.size(), ofc);//直接使用transferTo，使用通道直接传输，快的很
//            System.out.println(tnum + "::" + cfio.size());//可能不一定能读到cfio.size()个bytes的数据，具体查看这个方法的说明
            cfio.close();
        }
        ofc.close();
    }

    public static void main(String[] args) throws Exception {
//        Scanner 从终端格式化读取数据
//        Scanner scanner = new Scanner(System.in);
//        scanner.nextLine();
        long startTime = System.currentTimeMillis();
        splitFile("/Users/dingmac/Downloads/java_reader_uml.png", 5);
        long endTime = System.currentTimeMillis();
        System.out.println("split 耗费时间： " + (endTime - startTime) + " ms");

        startTime = System.currentTimeMillis();
        mergeFile("/Users/dingmac/Downloads/java_reader_uml.png", "/Users/dingmac/Downloads/java_reader_uml_merge.png", 5);
        endTime = System.currentTimeMillis();
        System.out.println("merge 耗费时间： " + (endTime - startTime) + " ms");
//        scanner.nextLine();
    }
}

