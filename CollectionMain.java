import java.util.*;

import static java.lang.Character.isLetter;

class CollectionMain {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        fillTreeMap(CollectionTest.getDirList(), CollectionTest.getFileList());
        long timeSpent = System.currentTimeMillis();
        System.out.println("run time is " + (timeSpent - startTime));
    }

    static TreeMap<String, TreeSet> fillTreeMap(ArrayList<String> dirList, ArrayList<String> fileList) {
        TreeSet<String> filesTreeSet = new TreeSet<>(fileList);
        TreeMap<String, TreeSet> dirsTreeMap = new TreeMap<>();
        for (String dirName : dirList) {
            if (isLetter(dirName.charAt(0)) && dirName.substring(1, 3).equals(":\\")) {
                SortedSet<String> ss_approx = filesTreeSet.subSet(dirName + '\\', dirName + '\\' + Character.MAX_VALUE);
                TreeSet ss_exact = new TreeSet();
                String low = dirName + '\\';
                for (String fileName : ss_approx) {
                    if (low.lastIndexOf('\\') == fileName.lastIndexOf('\\'))
                        ss_exact.add(fileName);
                }
                dirsTreeMap.put(dirName, new TreeSet(ss_exact));
            }
        }
        return dirsTreeMap;
    }
}
