package delete;

import java.io.File;
import java.io.IOException;

public class DeleteTh3 {
    public boolean deleteListFileInfolder(String source) throws IOException {
        File folder = new File(source);
        // folder tồn tại
        if (folder.exists()) {
            // danh sách file
            File[] listFile = folder.listFiles();
            if (listFile !=null && listFile.length !=0) {
                for (File f : listFile) {
                    // file thì xóa
                    if (f.isFile()) {
                        f.delete();
                    }
                    
                    if(f.isDirectory()){
                        deleteListFileInfolder(f.getAbsolutePath());
                    }
                }
            }
            folder.delete();
            System.out.println("Delete folder thành công!" + folder.getAbsolutePath());
            return true;
        } else {
            System.out.println("Folder không tồn tại");
            return false;
        }
    }

    public static void main(String[] args) throws IOException{
        DeleteTh3 deleteTh3 = new DeleteTh3();
        deleteTh3.deleteListFileInfolder("D:/PTHTTH/PTHTTH 23826/Testparent");
    }

}
