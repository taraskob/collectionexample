import java.util.*;

class FillMap {
    static int fileStartInd = 0;

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        fillTreeMap(CollectionTest.getDirList(), CollectionTest.getFileList());
        long timeSpent = System.currentTimeMillis();
        System.out.println("run time is " + (timeSpent - startTime));
    }

    static TreeMap<String, TreeSet> fillTreeMap(ArrayList<String> dirList, ArrayList<String> fileList) {
        TreeSet<String> dirSet = new TreeSet<>(dirList);
        Collections.sort(fileList, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.substring(0, o1.lastIndexOf('\\')+1).compareTo(o2.substring(0, o2.lastIndexOf('\\')+1));
            }
        });
        TreeMap<String, TreeSet> dirsTreeMap = new TreeMap<>();
        for (String dirName : dirSet) {
            TreeSet ss_exact = new TreeSet();
            String fileName = "";
            for (int i = fileStartInd; i < fileList.size(); i++) {
                fileName = fileList.get(i);
                if (fileName.startsWith(dirName) && (dirName.lastIndexOf('\\') == fileName.lastIndexOf('\\')))
                    ss_exact.add(fileName);
                if (fileName.substring(0, fileName.lastIndexOf('\\') + 1).compareTo(dirName) > 0) {
                    fileStartInd = i;
                    break;
                }
            }
            dirsTreeMap.put(dirName, new TreeSet(ss_exact));
        }
        return dirsTreeMap;
    }
}
