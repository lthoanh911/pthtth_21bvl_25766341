
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CopyFile {

    public boolean copyFile(String source, String dest) throws FileNotFoundException, IOException {
        File sourceFile = new File(source);
        File destFile = new File(dest);

        if (sourceFile.exists()) {
            // Khởi tạo luồng đọc và luồng ghi[cite: 1]
            FileInputStream fis = new FileInputStream(sourceFile);
            FileOutputStream fos = new FileOutputStream(destFile);

            byte[] arr = new byte[1024];
            int length;

            while ((length = fis.read(arr)) != -1) {
                fos.write(arr, 0, length);
            }

            fos.flush();
            fis.close();
            fos.close();

            System.out.println("Copy thành công từ:\n-> " + source + "\nsang:\n-> " + dest);
            return true;
        } else {
            System.out.println("File nguồn không tồn tại!");
            return false;
        }
    }

    public static void main(String[] args) {
        CopyFile app = new CopyFile();

        String source = "D:/PTHTTH/PTHTTH 23826/Test/SubTest/demo.txt";
        String dest = "D:/PTHTTH/PTHTTH 23826/Test/SubTest/test.txt";

        try {
            app.copyFile(source, dest);
        } catch (IOException e) {
            System.out.println("Lỗi trong quá trình copy: " + e.getMessage());
        }
    }
}