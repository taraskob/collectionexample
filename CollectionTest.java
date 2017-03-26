import java.io.*;
import java.util.*;

class CollectionTest {
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
                    SortedSet<String> ss = filesTreeSet.subSet(s + '\\', s + Character.MAX_VALUE);
                    dirsTreeMap.put(s, new TreeSet(ss));
                }
            } finally {
                in.close();
                writeMapInFile(dirsTreeMap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void writeMapInFile(TreeMap<String, TreeSet> dirsTreeMap) throws IOException {
        FileWriter writer = new FileWriter("result.txt");
            Set<Map.Entry<String, TreeSet>> es = dirsTreeMap.entrySet();
            for (Map.Entry<String, TreeSet> me : es) {
                writer.write(String.valueOf(me) + "\r\n");
         }
         writer.close();
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
