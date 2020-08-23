package local.ding;

import local.ding.tools.DirTool;

import java.io.*;

public class FileDemo {
    public static String read(String filename) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        StringBuffer sb = new StringBuffer();
        String s;
        while ((s = br.readLine()) != null) {
            sb.append(s.toUpperCase());
            sb.append("\n");
        }
        br.close();
        return sb.toString();
    }


    public static void main(String[] args) throws Exception {
//        System.out.println(read("/Users/dingmac/Desktop/demo.txt"));
//        StringReader sr = new StringReader("我是");
//        System.out.println((char) sr.read());
//        DataInputStream ds = new DataInputStream(new ByteArrayInputStream("asdasd".getBytes()));
//        while (ds.available() != 0)
//            System.out.println((char) ds.readByte());
//        DataOutputStream dos = new DataOutputStream(new FileOutputStream("/Users/dingmac/Desktop/demo.txt"));
//        dos.writeDouble(12.12232342);
//        dos.writeUTF("我是中");
//        dos.writeDouble(89.2122313);
////        dos.close();
//        DataInputStream ds = new DataInputStream(new FileInputStream("/Users/dingmac/Desktop/demo.txt"));
//        System.out.println(ds.readDouble());
//        System.out.println(ds.readUTF());
//        System.out.println(ds.readDouble());
//        ds.close();
        try {
            try {
                throw new Exception("hhhh");
            } finally {
                System.out.println("final");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}