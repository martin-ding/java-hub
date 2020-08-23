package local.ding.tools;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

public class DirTool {
    public static void main(String[] args) {
//        File dir = new File("day1-code/src/local/ding/");
//        System.out.println(dir.getName());
//        System.out.println(dir.getAbsolutePath());
//        System.out.println(dir.getCanonicalPath());
//        System.out.println(Arrays.toString(dir.list(filter(".*\\.java"))));
        System.out.println(walk("./", ".*\\.java"));

    }
    
    public static FilenameFilter filter (String regex) {
        FilenameFilter filenameFilter = new FilenameFilter() {
            Pattern pattern;
            {
                pattern = Pattern.compile(regex);
            }

            @Override
            public boolean accept(File dir, String name) {
                return pattern.matcher(name).matches();
            }
        };
        return filenameFilter;
    }

    static class TreeInfo implements Iterable<File> {
        private List<File> dirs = new ArrayList<>();
        private List<File> files = new ArrayList<>();

        public void addAll(TreeInfo info) {
            dirs.addAll(info.dirs);
            files.addAll(info.files);
        }

        @Override
        public Iterator<File> iterator() {
            return files.iterator();
        }

        public String toString() {
            return "dirs " + PPrint.pformat(dirs) + "\n\nfiles " + PPrint.pformat(files);
        }
    }

    public static TreeInfo walk(String startDir, String regex) {
        return recurseDir(new File(startDir), regex);
    }

    public static TreeInfo walk(String startDir) {
        return recurseDir(new File(startDir), ".*");
    }


    public static TreeInfo recurseDir(File startDir, String regex) {
        TreeInfo treeInfo = new TreeInfo();
        for (File subf: startDir.listFiles()) {
            if (subf.isDirectory()) {
                treeInfo.dirs.add(subf);
                treeInfo.addAll(recurseDir(subf, regex));
            } else {
                if (subf.getName().matches(regex)) {
                    treeInfo.files.add(subf);
                }
            }
        }
        return treeInfo;
    }

}
