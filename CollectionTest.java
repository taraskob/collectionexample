import java.io.*;
import java.util.*;

import static java.lang.String.valueOf;

class CollectionTest {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        if (FilesExists("dirs.txt", "files.txt")) {
            TreeSet<String> filesTreeSet = new TreeSet<>();
            fillTreeSet("files.txt", filesTreeSet);
            TreeMap<String, TreeSet> dirsTreeMap = CollectionMain.fillTreeMap(getDirList(), getFileList());
            try {
                if (!(mapCompleteness(dirsTreeMap, "dirs.txt") && !(mapCompleteness(dirsTreeMap, "files.txt")
                        && !(mapCorrectness(dirsTreeMap, filesTreeSet, "dirs.txt")))))
                    System.out.println("FAIL!");
                else
                    System.out.println("TEST RESULT IS OK!");

                writeMapInFile(dirsTreeMap);
                long timeSpent = System.currentTimeMillis();
                System.out.println("run time is " + (timeSpent - startTime));
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

    static boolean mapCorrectness(TreeMap<String, TreeSet> dirsTreeMap, TreeSet<String> filesTreeSet, String s) {
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
            return false;
        } else
            System.out.println("Map is correct");
        return true;
    }


    static boolean mapCompleteness(TreeMap<String, TreeSet> dirsTreeMap, String filename) {
        String file_missing = "";
        Collection collectionValue = null;
        if (filename.equals("dirs.txt")) {
            Set keySet = dirsTreeMap.keySet();
            file_missing = "missing_dirs.txt";
            collectionValue = keySet;
        }
        if (filename.equals("files.txt")) {
            Collection valueSet = toSet(dirsTreeMap.values());
            file_missing = "missing_files.txt";
            collectionValue = valueSet;
        }
        return ifContains(dirsTreeMap, collectionValue, filename, file_missing);
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

    static boolean ifContains(TreeMap<String, TreeSet> map, Collection collectionValue, String filename, String file_missing) {
        TreeSet<String> testSet = new TreeSet<>();
        TreeSet<String> dirSet = new TreeSet<>();
        fillTreeSet(filename, testSet);
        if (collectionValue.containsAll(testSet)) {
            System.out.println("Map contains all the data from the file " + filename);
            return true;
        } else {
            if (filename == "files.txt") {

                fillTreeSet("dirs.txt", dirSet);
            }
            ArrayList<String> al = new ArrayList<>();
            testSet.removeAll(collectionValue);
            int missed = 0;
            for (String str : testSet) {
                missed = 0;
                for (Object ob : collectionValue) {
                    String strobj = valueOf(ob);
                    if (strobj.startsWith(str)) {
                        missed = 0;

                        break;
                    } else
                        missed += 1;
                }
                if (missed > 0)
                    if (filename == "dirs.txt")
                        al.add(str);
                    else {
                        for (String dirName : dirSet) {
                            if (str.startsWith(dirName)) {
                                al.add(str);
                                break;
                            }
                        }
                    }
            }

            if (al.size() > 0) {
                writeErr(file_missing, al);
                System.out.println("Map contains NOT ALL the data from the file " + filename + " See the " + file_missing);
                return false;
            } else
                System.out.println("Map contains all the data from the file " + filename);
            return true;
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
