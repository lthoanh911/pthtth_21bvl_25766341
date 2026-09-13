package delete;

import java.io.File;

public class DeleteFolderEmpty {
    public boolean deleteEmptyFolder(String source) {
        File folder = new File(source);
        // kiem tra neu folder ton tai thi xoa
        if (folder.exists()) {
            folder.delete();
            System.out.println("Folder tồn tại\n Xóa Folder thành công");
            return true;
        } else {
            System.out.println("Folder không tồn tại");
        }
        return false;
    }

    public static void main(String[] args) {
        DeleteFolderEmpty deleteFolder = new DeleteFolderEmpty();
        deleteFolder.deleteEmptyFolder("D:/PTHTTH/PTHTTH 23826/test");
    }

}
