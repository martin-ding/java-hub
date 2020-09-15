package local.ding;

import local.ding.tools.DirTool;
import local.ding.tools.TextFile;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.Charset;
import java.nio.file.attribute.FileTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.zip.*;

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

    /**
     * 计算在一个文件中出现的字符的个数，map
     */
    public static void mapNumber() {
        Map<String, Integer> map = new HashMap<>();
        TextFile textFile = new TextFile("demo.txt", "");
        for (String item : textFile) {
            Integer num = map.get(item);
            if (num == null) {
                num = 1;
            } else {
                num++;
            }
            map.put(item, num);
        }
        System.out.println(map);
    }

    public static void main(String[] args) throws Exception {
//        byteBuffer();
//        viewBuffers();
//        useSymmetricScramble();
//        mmap();
//        filelock();
//        byteBufferTest();
//        deflaterdemo(new String[]{"demo.txt", "text.txt"});
        serializeDemo();
    }

    public static void filelock() throws Exception {
        FileLock fileLock = new FileOutputStream("demo.txt").getChannel().lock();
        System.out.println(fileLock.isShared());
        if (fileLock != null) {
            System.out.println("file is locking");
            TimeUnit.MILLISECONDS.sleep(1000);
            System.out.println("file is unlocking");
        }
    }

    public static void readALine() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s;
        PrintWriter bw = new PrintWriter(System.out, true);

        while ((s = br.readLine()) != null && s.length() != 0) {
            bw.println(s);
        }
    }

    public static void redirectDemo() throws Exception {
        PrintStream outback = System.out;
        PrintStream out = new PrintStream(new BufferedOutputStream(new FileOutputStream("test.out")));
        InputStream in = new BufferedInputStream(new FileInputStream("demo.txt"));
        System.setIn(in);
        System.setOut(out);
        System.setErr(out);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s;
        while ((s = br.readLine()) != null && s.length() != 0) {
            System.out.println(s);
        }
        out.close();
        in.close();
        System.setOut(outback);
    }

    public static void BufferedByteDemo() throws Exception {
        int size = 1024;
        FileChannel fc = new FileOutputStream("demo.txt").getChannel();
        fc.write(ByteBuffer.wrap("This is demo".getBytes()));
        fc.close();
        fc = new FileInputStream("demo.txt").getChannel();//读
        ByteBuffer buff = ByteBuffer.allocate(size);
        fc.read(buff);
        buff.flip();

        System.out.println(buff.asCharBuffer());
        String encoding = System.getProperty("file.encoding");
        System.out.println(encoding);
        System.out.println("Decode with " + encoding + " : " + Charset.forName(encoding).decode(buff));
        fc.close();
        fc = new FileOutputStream("demo.txt").getChannel();
        fc.write(ByteBuffer.wrap("Some text".getBytes("gbk")));
        fc.close();
        buff.clear();
        fc = new FileInputStream("demo.txt").getChannel();
        fc.read(buff);
        buff.flip();
        System.out.println(Charset.forName("gbk").decode(buff));
        fc.close();

        fc = new FileOutputStream("demo.txt").getChannel();
        buff.clear();
        buff.asCharBuffer().put("OK wowo");
        fc.write(buff);
        fc.close();
        fc = new FileInputStream("demo.txt").getChannel();
        buff.clear();
        fc.read(buff);
        buff.flip();
        System.out.println(buff.asCharBuffer());
    }

    public static void charsetlist() {
        SortedMap<String, Charset> charsetMap = Charset.availableCharsets();
        Iterator<String> it = charsetMap.keySet().iterator();
        while (it.hasNext()) {
            String csName = it.next();
            System.out.println(csName);
            Iterator alias = Charset.forName(csName).aliases().iterator();
            while (alias.hasNext()) {
                System.out.print("alias: ");
                System.out.print(alias.next());
                if (alias.hasNext()) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }
    }

    public static void byteBuffer() {
        int size = 1024;
        ByteBuffer bb = ByteBuffer.allocate(size);
//        bb.limit(1);
        bb.slice();
        byte b = bb.get();
        System.out.println(bb.position() + " " + bb.limit());
        bb.rewind();
        bb.asCharBuffer().put("asasasas");
        System.out.println(bb.getChar());

        //view buff
        bb.clear();
        IntBuffer ib = bb.asIntBuffer();
        ib.put(new int[]{12312, 12312, 123, 242, 242, 342312, 13213, 1312});
        System.out.println(ib);
        System.out.println(ib.get(2));
        ib.flip();
        while (ib.hasRemaining()) {
            System.out.println(ib.get());
        }
    }

    public static void viewBuffers() {
        ByteBuffer bb = ByteBuffer.wrap(new byte[]{0, 0, 0, 0, 0, 0, 0, 'a'});
        System.out.println(bb.position() + "." + bb.mark());
        bb.rewind();
        System.out.println(bb.position() + "." + bb.mark());
        bb.asCharBuffer().put("abcd");
        System.out.println(Arrays.toString(bb.array()));
//        bb.rewind();
//        bb.clear();
//        bb.flip();
//        bb.mark();
//        bb.reset();
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.asCharBuffer().put("abcd");
        System.out.println(Arrays.toString(bb.array()));
    }

    public static void symmetricScramble(CharBuffer buffer) {
        while (buffer.hasRemaining()) {
            buffer.mark();
            char c1 = buffer.get();
            char c2 = '\0';
            if (buffer.hasRemaining()) {
                c2 = buffer.get();
            }
            buffer.reset();
            if (c2 != '\0') {
                buffer.put(c2).put(c1);
            } else {
                buffer.put(c1);
            }
        }
    }

    public static void useSymmetricScramble() {
        char[] data = "This is a big Thing".toCharArray();
        ByteBuffer bb = ByteBuffer.allocate(data.length * 2);
        CharBuffer cb = bb.asCharBuffer();
        cb.put(data);
//        cb.flip();
        cb.rewind();
//        System.out.println();
        symmetricScramble(cb);
        cb.rewind();
        System.out.println(cb);

        cb.rewind();
//        System.out.println();
        symmetricScramble(cb);
        cb.rewind();
        System.out.println(cb);

    }

    public static void mmap() throws Exception {
//        int numofInt = 10000000;
//
//        DataOutputStream rf = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("demo.txt")));
//        long start = System.nanoTime();
//        for (int i = 0; i < 2500000; i++) {
//            rf.writeInt(i);
//        }
//        rf.close();
//        double needtime = (double) (System.nanoTime() - start) / 1.0e9;
//        System.out.println("buff spend time:" + needtime);
//
//
//        FileChannel fc = new RandomAccessFile("demo.txt", "rw").getChannel();
//        System.out.println(fc.size());
//        IntBuffer cb = fc.map(FileChannel.MapMode.READ_WRITE, 0, numofInt).asIntBuffer();
//        start = System.nanoTime();
//        for (int i = 0; i < numofInt; i++) {
//            try {
//                cb.put(i);
//            } catch (Exception e) {
//                System.out.println("===" + i );
//                break;
//            }
//        }
//        fc.close();
//        needtime = ((double) (System.nanoTime() - start)) / 1.0e9;
//        System.out.println("spend time:" + needtime);
//
//
//         DataInputStream rs = new DataInputStream(new BufferedInputStream(new FileInputStream("demo.txt")));
//         start = System.nanoTime();
//        for (int i = 0; i < 2500000; i++) {
//            rs.readInt();
//        }
//        rs.close();
//         needtime = (double) (System.nanoTime() - start) / 1.0e9;
//        System.out.println("buff spend time:" + needtime);
//
//
//         fc = new RandomAccessFile("demo.txt", "rw").getChannel();
//        System.out.println(fc.size());
//         cb = fc.map(FileChannel.MapMode.READ_WRITE, 0, numofInt).asIntBuffer();
//        start = System.nanoTime();
//        while (cb.hasRemaining()) {
//                cb.get();
//        }
//        fc.close();
//        needtime = ((double) (System.nanoTime() - start)) / 1.0e9;
//        System.out.println("spend time:" + needtime);

        RandomAccessFile rfa = new RandomAccessFile("demo.txt", "rw");
        rfa.setLength(100);
        rfa.writeInt(1);
        for (int i = 0;i< 10; i ++) {
            rfa.seek( rfa.length() - 4);
            int a = rfa.readInt();

            System.out.println(rfa.getFilePointer() +  "!!");
            rfa.writeInt(a);
            System.out.println(rfa.length() + "~~");
            System.out.println(rfa.getFilePointer() + " =====");
        }
        System.out.println(rfa.length());
        rfa.seek(0);
        for (int i = 0;i < 10; i ++) {
            try {
                System.out.println(rfa.readInt());
            } catch (Exception e) {

            }
        }
        rfa.close();
    }

    public static void byteBufferTest()
    {
        ByteBuffer bb = ByteBuffer.wrap("hasas".getBytes());
        System.out.println(Arrays.toString(bb.array()));
        bb.position(2);
//        System.out.println(bb.capacity());
        bb.limit(4);
        ByteBuffer bb1 = bb.slice();
        System.out.println(bb1.capacity() + ":" + bb1.position());
        while (bb1.hasRemaining()) {
            System.out.println((char) bb1.get());
        }
    }

    public static void deflaterdemo (String[] files) throws Exception {
        FileOutputStream f = new FileOutputStream("test.zip");
        CheckedOutputStream csum = new CheckedOutputStream(f, new Adler32());
        ZipOutputStream zos = new ZipOutputStream(csum);
        zos.setComment("Demo java zip");
        BufferedOutputStream out = new BufferedOutputStream(zos);
        for (String file : files) {
            System.out.println("writing file " + file);
            BufferedReader br = new BufferedReader(new FileReader(file));
            ZipEntry a = new ZipEntry(file);
//            a.setComment("dasda");
//            a.setMethod(6);
            zos.putNextEntry(a);
            int c;
            while ((c = br.read()) != -1) {
                out.write(c);
            }
            br.close();
            out.flush();
        }
//        System.out.println(csum.getChecksum().getValue());
        out.close();
        System.out.println(csum.getChecksum().getValue());

        System.out.println("read zip file");
        FileInputStream fs = new FileInputStream("test.zip");
        CheckedInputStream cis = new CheckedInputStream(fs, new Adler32());
        ZipInputStream zis = new ZipInputStream(cis);
        BufferedInputStream in = new BufferedInputStream(zis);
        ZipEntry e;
        while ((e = zis.getNextEntry()) != null) {

            System.out.println("reading file " + e.getMethod() +  ":" + e.getComment() );
            int x;
            while ((x = in.read()) != -1) {
//                System.out.write(x);
            }
        }


        System.out.println("____" + cis.getChecksum().getValue());
        in.close();
    }

    public static void serializeDemo() throws Exception {
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        ObjectOutputStream oos = new ObjectOutputStream(bos);
//        oos.writeObject("Demo");
//        oos.writeObject(new ArrayList<String>(Arrays.asList("zhangsan","wangwu")));
//        oos.flush();
//
//        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
//        String s = (String)ois.readObject();
//        ArrayList<String> l = (ArrayList<String>) ois.readObject();
//        System.out.println(s + "===" + l);
//
//        ObjectOutputStream ou = new ObjectOutputStream(new FileOutputStream("data.dat"));
//        ou.writeObject(new Hello());
//        ou.writeObject(new Hello());
//        ObjectInputStream oo = new ObjectInputStream(new FileInputStream("data.dat"));
//        Hello h = (Hello) oo.readObject();
//        System.out.println(h);
//
//        h = (Hello) oo.readObject();
//        System.out.println(h);
//
////        h = (Hello) oo.readObject();
////        System.out.println(h);
//
//        Class<Hello> ha = Hello.class;
////        System.out.println(int.class);
////       ha.Constructor(Integer.class).newInstance(12);
////        new Hello(1);
//
        ByteArrayOutputStream bou = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bou);
        oos.writeObject(new HelloA(192, "WOow ssd"));
        oos.flush();

        ObjectInputStream ois =  new ObjectInputStream(new ByteArrayInputStream(bou.toByteArray()));
        HelloA a = (HelloA) ois.readObject();
        System.out.println(a);

    }

    static class HelloA implements Externalizable {
        private int a;
        private String name;

        public HelloA (){}

        public HelloA(int a, String name) {
            this.a = a;
            this.name = name;
        }

        @Override
        public String toString() {
            return name + a;
        }

        @Override
        public void writeExternal(ObjectOutput out) throws IOException {
//            out.writeInt(a);
//            out.writeObject(name);
        }

        @Override
        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
//
//            a = in.readInt();
//            name = (String)in.readObject();
        }
    }

    static class Hello implements Serializable{
        public Hello() {}
        private Hello(Integer a) {
            System.out.println("++++" + a);
        }
        private Random random = new Random(System.nanoTime());
        private final int r = random.nextInt(100);

        @Override
        public String toString() {
            return Integer.toString(r);
        }
    }
}

class OsExecute {
    public static void command(String command) {
        boolean err = false;
        try {
            Process process = new ProcessBuilder(command.split(" ")).start();
            BufferedReader results = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String s;
            while ((s = results.readLine()) != null) {
                System.out.println(s);
            }
            BufferedReader errors = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((s = errors.readLine()) != null) {
                System.err.println(s);
                err = true;
            }
        } catch (Exception e) {

        }

        if (err) {

        }
    }
}


