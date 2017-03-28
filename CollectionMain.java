import java.io.*;
import java.util.*;

import static java.lang.String.valueOf;

class CollectionMain {
    public static void main(String[] args) {
        if (FilesExists("dirs.txt", "files.txt")) {
            TreeSet<String> filesTreeSet = new TreeSet<>();
            TreeMap<String, TreeSet> dirsTreeMap = new TreeMap<>();
            fillTreeSet("files.txt", filesTreeSet);
            fillTreeMap("dirs.txt", dirsTreeMap, filesTreeSet);
        }
    }

    static void fillTreeSet(String filename, TreeSet<String> filesTreeSet) {
        File file = new File(filename);
        try {
            BufferedReader in = new BufferedReader(new FileReader(file.getAbsoluteFile()));
            try {
                String s;
                while ((s = in.readLine()) != null) {
                    filesTreeSet.add(s);
                }
            } finally {
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void fillTreeMap(String filename, TreeMap<String, TreeSet> dirsTreeMap, TreeSet<String> filesTreeSet) {
        File file = new File(filename);
        try {
            BufferedReader in = new BufferedReader(new FileReader(file.getAbsoluteFile()));
            try {
                String s;
                while ((s = in.readLine()) != null) {
                    SortedSet<String> ss_approx = filesTreeSet.subSet(s + '\\', s + '\\' + Character.MAX_VALUE);
                    TreeSet ss_exact = new TreeSet();
                    String low = s + '\\';
                    for (String str : ss_approx) {
                        if (low.lastIndexOf('\\') == str.lastIndexOf('\\'))
                            ss_exact.add(str);
                        else {
                            inPut(s, ss_exact, dirsTreeMap);
                        }
                    }
                    inPut(s, ss_exact, dirsTreeMap);
                }
            } finally {
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void inPut(String s, TreeSet ss_exact, TreeMap<String, TreeSet> dirsTreeMap) {
        dirsTreeMap.put(s, new TreeSet(ss_exact));
    }

    static boolean FilesExists(String dirs_txt, String files_txt) {
        File file = new File(dirs_txt);
        if (!file.exists())
            return false;
        file = new File(files_txt);
        if (!file.exists())
            return false;
        return true;
    }
}
