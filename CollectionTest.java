import java.io.*;
import java.util.*;

import static java.lang.String.valueOf;

class CollectionTest {
    public static void main(String[] args) {
        if (CollectionMain.FilesExists("dirs.txt", "files.txt")) {
            TreeSet<String> filesTreeSet = new TreeSet<>();
            TreeMap<String, TreeSet> dirsTreeMap = new TreeMap<>();
            CollectionMain.fillTreeSet("files.txt", filesTreeSet);
            CollectionMain.fillTreeMap("dirs.txt", dirsTreeMap, filesTreeSet);
            try {
                mapCompleteness(dirsTreeMap, "dirs.txt");
                mapCompleteness(dirsTreeMap, "files.txt");
                mapCorrectness(dirsTreeMap, filesTreeSet, "dirs.txt");
                writeMapInFile(dirsTreeMap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static void mapCorrectness(TreeMap<String, TreeSet> dirsTreeMap, TreeSet<String> filesTreeSet, String s) {
        Set<Map.Entry<String, TreeSet>> esMap = dirsTreeMap.entrySet();
        ArrayList<String> al = new ArrayList<>();
        for (Map.Entry<String, TreeSet> me : esMap) {
            String strKey = valueOf(me.getKey());
            String strvalue = valueOf(me.getValue()).replace('[', ' ');

            strvalue = strvalue.replace(']', ' ');
            String strValue[] = strvalue.split(",");
            for (String str : strValue) {
                if (!(str.substring(1, str.length()).startsWith(strKey)))
                    if (!(str.substring(1, str.length()).startsWith(" ")))
                        al.add(strKey + " " + str.substring(1, str.length()));
            }
        }
        if (al.size() > 0) {
            System.out.println("Map IS NOT CORRECT See the map_err.txt");
            writeErr("map_err.txt", al);
        } else
            System.out.println("Map is correct");
    }

    static void mapCompleteness(TreeMap<String, TreeSet> dirsTreeMap, String filename) {
        TreeSet<String> testSet = new TreeSet<>();
        String file_missing = "";
        Set keySet = null;
        Collection valueSet = null;
        if (filename.equals("dirs.txt")) {
            keySet = dirsTreeMap.keySet();
            CollectionMain.fillTreeSet(filename, testSet);
            file_missing = "missing_dirs.txt";
            ifContains(keySet, testSet, filename, file_missing);
        }
        if (filename.equals("files.txt")) {
            valueSet = toSet(dirsTreeMap.values());
            CollectionMain.fillTreeSet(filename, testSet);
            file_missing = "missing_files.txt";
            ifContains(valueSet, testSet, filename, file_missing);
        }
    }

    static TreeSet<String> toSet(Collection<TreeSet> values) {
        TreeSet<String> tSet = new TreeSet<>();
        for (Object ob : values) {
            String strvalue = valueOf(ob).replace('[', ' ');
            ;
            strvalue = strvalue.replace(']', ' ');
            String strValue[] = strvalue.split(",");
            for (String str : strValue) {
                if (!(str.substring(1, str.length()).startsWith(" ")))
                    tSet.add(str.substring(1));
            }
        }
        return tSet;
    }

    static void ifContains(Collection map, TreeSet<String> testSet, String filename, String file_missing) {
        if (map.containsAll(testSet))
            System.out.println("Map contains all the data from the file " + filename);
        else {
            System.out.println("Map contains NOT ALL the data from the file " + filename + " See the " + file_missing);
            ArrayList<String> al = new ArrayList<>();
            testSet.removeAll(map);
            int missed = 0;
            for (String str : testSet) {
                missed = 0;
                for (Object ob : map) {
                    String strobj = valueOf(ob);
                    if (strobj.startsWith(str)) {
                        missed = 0;

                        break;
                    } else
                        missed += 1;
                }
                if (missed > 0)
                    al.add(str);
            }
            writeErr(file_missing, al);
        }
    }

    static void writeErr(String filename, Collection ks) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(filename);
            for (Object str : ks) {
                writer.write(str + "\r\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static ArrayList<String> readInArray(String filename) {
        File file = new File(filename);
        ArrayList<String> al = new ArrayList<>();
        try {
            BufferedReader in = new BufferedReader(new FileReader(file.getAbsoluteFile()));
            try {
                String s;
                while ((s = in.readLine()) != null) {
                    al.add(s);
                }
            } finally {
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return al;
    }

    static void writeMapInFile(TreeMap<String, TreeSet> dirsTreeMap) throws IOException {
        FileWriter writer = new FileWriter("result.txt");
        Set<Map.Entry<String, TreeSet>> es = dirsTreeMap.entrySet();
        for (Map.Entry<String, TreeSet> me : es) {
            writer.write(me.getKey() + "\r\n");
            writer.write(me.getValue() + "\r\n");
        }
        writer.close();
    }
}
