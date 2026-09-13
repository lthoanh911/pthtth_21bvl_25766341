package Nhiphan;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class BinaryFileDemo {

    // 1. Ghi danh sách sinh viên xuống file nhị phân[cite: 1]
    public static void saveSV(String src, ArrayList<SinhVien> listSV) throws IOException {
        DataOutputStream dos = new DataOutputStream(new FileOutputStream(new File(src))); // [cite: 1]

        dos.writeInt(listSV.size());

        for (SinhVien sv : listSV) {
            dos.writeUTF(sv.getMssv());
            dos.writeUTF(sv.getTen());
            dos.writeInt(sv.getTuoi());

            dos.writeInt(sv.getListMH().size());
            for (MonHoc mh : sv.getListMH()) {
                dos.writeUTF(mh.getTenMonHoc());
                dos.writeInt(mh.getTinChi());
                dos.writeDouble(mh.getDiem());
            }
        }
        dos.flush();
        dos.close();
        System.out.println("Ghi dữ liệu nhị phân thành công!");
    }

    public static void loadSV(String src) throws IOException {
        DataInputStream dis = new DataInputStream(new FileInputStream(new File(src)));

        int size = dis.readInt();
        ArrayList<SinhVien> listSV = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            String mssv = dis.readUTF();
            String name = dis.readUTF();
            int age = dis.readInt();

            int sizemh = dis.readInt();
            ArrayList<MonHoc> listMH = new ArrayList<>();

            for (int j = 0; j < sizemh; j++) {
                String tenMonHoc = dis.readUTF();
                int tinChi = dis.readInt();
                double diem = dis.readDouble();
                MonHoc mh = new MonHoc(tenMonHoc, tinChi, diem);
                listMH.add(mh);
            }
            listSV.add(new SinhVien(mssv, name, age, listMH));
        }
        System.out.println("\n--- DỮ LIỆU ĐỌC TỪ FILE NHỊ PHÂN ---");
        for (SinhVien sv : listSV) {
            System.out.print(sv.toString());
        }
        dis.close();
    }

    public static void main(String[] args) throws IOException {
        String filePath = "D:/PTHTTH/PTHTTH 23826/sinhvien.bin";

        // Tạo dữ liệu mẫu[cite: 1]
        MonHoc mh1 = new MonHoc("Lập trình căn bản", 3, 7.5);
        MonHoc mh2 = new MonHoc("Lập trình Web", 3, 8.0);
        MonHoc mh3 = new MonHoc("Thiết kế hướng đối tượng", 3, 6.7); // [cite: 1]

        ArrayList<MonHoc> listMH1 = new ArrayList<>();
        listMH1.add(mh1);
        listMH1.add(mh2);

        ArrayList<MonHoc> listMH2 = new ArrayList<>();
        listMH2.add(mh2);
        listMH2.add(mh3);

        ArrayList<SinhVien> listSV = new ArrayList<>();
        listSV.add(new SinhVien("11329078", "Nguyen Van A", 20, listMH1));
        listSV.add(new SinhVien("11329079", "Tran Thi B", 21, listMH2));

        // Ghi và Đọc file nhị phân[cite: 1]
        saveSV(filePath, listSV);
        loadSV(filePath);
    }
}