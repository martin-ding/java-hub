package local.ding.files;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class StreamDemo {
    static void fileRead() throws IOException {
        String contents = new String(Files.readAllBytes(Paths.get("/Users/dingmac/Downloads/javaProj/day1-code/demo.txt")), StandardCharsets.UTF_8);
        String[] strs = contents.split("\\PL+");
        List<String> list = new LinkedList<>(Arrays.asList(strs));
        for (String m :list) {
            System.out.println(m);
//            System.exit(1);
        }

        System.out.println(list.stream().filter(w->w.length() >= 12).count());

        Stream<String> mm = Arrays.stream(strs);
        Stream<String> m = Stream.of(strs);
        String a = "Hsas";
        Stream<String> demo = Stream.generate(() -> a);
//        System.out.println(demo);
        Stream<Double> dem = Stream.generate(Math::random);
//        Stream<Integer> aa = Stream.iterate();
//        Stream<String> words = new Scanner(contents).tokens();
//        System.out.println(words.filter(x -> x.length() > 10). count());
//        words.collect(Collectors.toList());
//        Stream<String> mmm = StreamSupport.stream(list.spliterator(), false);
//        show("mm", mmm);

    }

    public static <T> void show(String title, Stream<T> stream) {
        final int SIZE = 10;
        List<T> firstElements = stream.limit(SIZE + 1).collect(Collectors.toList());
        System.out.print("Title :" + title);
        System.out.println(firstElements);
    }

    static class Apple{
        private static int count = 0;
        private int id = count ++;

        @Override
        public String toString() {
            return "apple " + id;
        }
    }

    public static void streamdemo1(){
        //创建一个apple List
        List<Apple> apples = new LinkedList<>(Arrays.asList(new Apple(),new Apple(),new Apple(),new Apple(),new Apple(),new Apple(),new Apple()));

    }

    public static void main(String[] args) throws Exception {
//        fileRead();
        streamdemo1();
    }

    public void ad(String name) {
        System.out.println(name);
    }
}

@FunctionalInterface
interface Default<T> {
    void method(T a);//貌似要两个参数，我们可以将第一个参数当成执行的对象，当然也可以设置两个参数
}
//Facade 类
class Dem {
    public static void method(Default d, Imp a)
    {
        d.method(a);
    }
}
class Imp {
    private static int count = 0;
    private final int id = count++;
    private String name;

    @Override
    public String toString() {
        return "Imp " + id +  " : name " + name;
    }

    public void mmm() {
        System.out.println(this + " wowo");
    }

    public static void main(String[] args) {
        Default <Imp> d = Imp::mmm;//这就其实就相当于一个子类实现
        Dem.method(d, new Imp());

//        //等同于
//        Dem.method(new Default() {
//            @Override
//            public void method(Imp a) {
//                a.mmm();
//            }
//        }, new Imp());
    }
}