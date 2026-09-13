package delete;

import java.io.File;

public class DeleteFileIO {
    public void deleteFile(String source) {
        File file = new File(source);
        if (file.exists()) {
            System.out.println("File tồn tại");
            file.delete();
            System.out.println("Xóa file thành công");
        } else {
            System.out.print("File không tồn tại");
        }
    }

    public static void main(String[] args) {
        DeleteFileIO deleteFileIO = new DeleteFileIO();
        deleteFileIO.deleteFile("D:/PTHTTH/PTHTTH 23826/test/demo.txt");
    }

}
