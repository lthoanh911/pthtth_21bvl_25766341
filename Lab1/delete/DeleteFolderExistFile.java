package delete;

import java.io.File;

public class DeleteFolderExistFile {
    public boolean deleteListFileInfolder(String source) {
        File folder = new File(source);
        // folder tồn tại
        if (folder.exists()) {
            // danh sách file
            File[] listFile = folder.listFiles();
            if (listFile.length != 0) {
                for (File f : listFile) {
                    // file thì xóa
                    if (f.isFile()) {
                        f.delete();
                    }
                }
            }
            folder.delete();
            System.out.println("Delete folder thành công!");
            return true;
        } else {
            System.out.println("Folder không tồn tại");
            return false;
        }
    }

    public static void main(String[] args) {
        DeleteFolderExistFile deleteFolder = new DeleteFolderExistFile();
        deleteFolder.deleteListFileInfolder("D:/PTHTTH/PTHTTH 23826/test");
    }

}
