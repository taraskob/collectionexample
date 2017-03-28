import java.io.*;
import java.util.*;

import static java.lang.String.valueOf;

class CollectionTest {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        if (FilesExists("dirs.txt", "files.txt")) {
            TreeSet<String> filesTreeSet = new TreeSet<>();
            fillTreeSet("files.txt", filesTreeSet);
            TreeMap<String, TreeSet> dirsTreeMap = CollectionMain.fillTreeMap(getDirList(),getFileList());
            try {
                mapCompleteness(dirsTreeMap, "dirs.txt");
                mapCompleteness(dirsTreeMap, "files.txt");
                mapCorrectness(dirsTreeMap, filesTreeSet, "dirs.txt");
                writeMapInFile(dirsTreeMap);
                long timeSpent = System.currentTimeMillis();
                System.out.println("run time is " + (timeSpent-startTime ));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static void fillTreeSet(String files_txtName, TreeSet<String> filesTreeSet) {
        File file = new File(files_txtName);
        try {
            BufferedReader in = new BufferedReader(new FileReader(file.getAbsoluteFile()));
            try {
                String filesName;
                while ((filesName = in.readLine()) != null) {
                    filesTreeSet.add(filesName);
                }
            } finally {
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static ArrayList<String> getDirList() {
        return fillArrayList("dirs.txt");
    }

    static ArrayList<String> getFileList() {
        return fillArrayList("files.txt");
    }

    static ArrayList<String> fillArrayList(String readFile) {
        File file = new File(readFile);
        ArrayList<String> alReadData = new ArrayList<>();
        try {
            BufferedReader in = new BufferedReader(new FileReader(file.getAbsoluteFile()));
            try {
                String dirs_files_Name;
                while ((dirs_files_Name = in.readLine()) != null) {
                    alReadData.add(dirs_files_Name);
                }
            } finally {
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return alReadData;
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
            fillTreeSet(filename, testSet);
            file_missing = "missing_dirs.txt";
            ifContains(keySet, testSet, filename, file_missing);
        }
        if (filename.equals("files.txt")) {
            valueSet = toSet(dirsTreeMap.values());
            fillTreeSet(filename, testSet);
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

    static void writeMapInFile(TreeMap<String, TreeSet> dirsTreeMap) throws IOException {
        FileWriter writer = new FileWriter("result.txt");
        Set<Map.Entry<String, TreeSet>> es = dirsTreeMap.entrySet();
        for (Map.Entry<String, TreeSet> me : es) {
            writer.write(me.getKey() + "\r\n");
            writer.write(me.getValue() + "\r\n");
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
