import java.util.*;

class CollectionMain {
    public static void main(String[] args) {
        fillTreeMap(CollectionTest.getDirList(), CollectionTest.getFileList());
    }

    static TreeMap<String, TreeSet> fillTreeMap(ArrayList<String> dirList, ArrayList<String> fileList) {
        TreeSet<String> filesTreeSet = new TreeSet<>(fileList);
        TreeMap<String, TreeSet> dirsTreeMap = new TreeMap<>();
        for (String dirName : dirList) {
            SortedSet<String> ss_approx = filesTreeSet.subSet(dirName + '\\', dirName + '\\' + Character.MAX_VALUE);
            TreeSet ss_exact = new TreeSet();
            String low = dirName + '\\';
            for (String fileName : ss_approx) {
                if (low.lastIndexOf('\\') == fileName.lastIndexOf('\\'))
                    ss_exact.add(fileName);
            }
            dirsTreeMap.put(dirName, new TreeSet(ss_exact));
        }
        return dirsTreeMap;
    }
}
