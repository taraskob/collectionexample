import java.io.IOException;
import java.util.*;

class CollectionMain {
    public static void main(String[] args) {
        fillTreeMap(CollectionTest.getDirList(),CollectionTest.getFileList());
    }

    static TreeMap<String, TreeSet> fillTreeMap(ArrayList<String> dirList,ArrayList<String> fileList) {
        TreeSet<String> filesTreeSet = new TreeSet<>(fileList);
        TreeMap<String, TreeSet> dirsTreeMap = new TreeMap<>();
        ArrayList<String> dirsList = dirList;
        for (String dirName : dirsList) {
            SortedSet<String> ss_approx = filesTreeSet.subSet(dirName + '\\', dirName + '\\' + Character.MAX_VALUE);
            TreeSet ss_exact = new TreeSet();
            String low = dirName + '\\';
            for (String fileName : ss_approx) {
                if (low.lastIndexOf('\\') == fileName.lastIndexOf('\\'))
                    ss_exact.add(fileName);
                else {
                    dirsTreeMap.put(dirName, new TreeSet(ss_exact));
                }
            }
            dirsTreeMap.put(dirName, new TreeSet(ss_exact));
        }
        return dirsTreeMap;
    }
}
