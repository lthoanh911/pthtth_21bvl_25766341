
import java.io.File;

public class FindFile {
    public void finFile(String source, String key) {
        File file = new File(source);
        if (file.exists()) {
            if (file.isFile()) {
                if (file.getName().endsWith(key)) {
                    System.out.println("Đã tìm thấy:" + file.getAbsolutePath());
                }
            }
            File[] listFile = file.listFiles();
            if (listFile != null) {

                for (File f : listFile) {
                    finFile(f.getAbsolutePath(), key);
                }
            }
        } else {
            System.out.println("source không tồn tại");
        }

    }

    public static void main(String[] args) {
        FindFile findFile = new FindFile();
        String folderPath = "D:/PTHTTH/PTHTTH 23826/Test";
        String keyword = ".txt";
        System.out.println("Bắt đầu tìm kiếm file kết thúc bằng '" + keyword + "' trong :" + folderPath);
        findFile.finFile(folderPath, keyword);
    }
}