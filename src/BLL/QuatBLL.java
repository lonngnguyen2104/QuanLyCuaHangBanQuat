package bll;

import dao.QuatDAO;
import dto.Quat;

import java.sql.SQLException;
import java.util.List;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;


public class QuatBLL {
    private final QuatDAO dao = new QuatDAO();

    // Lấy tất cả các quạt
    public List<Quat> layTatCa() {
        try {
            return dao.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return List.of();
        }
    }
    private String convertDateFormat(String inputDate) {
    SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");  
    SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");  

    try {
        return outputFormat.format(inputFormat.parse(inputDate));  
    } catch (ParseException e) {
        e.printStackTrace(); 
        return null;  
    }
}
   
  public boolean them(String maQuat, String tenQuat, int gia, String maNSX, String ngaySanXuat, String chatLieu, String thuongHieu, String maLoaiSP) {
    try {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(ngaySanXuat, inputFormatter);
        String formattedDate = localDate.toString();  // yyyy-MM-dd

        dao.insert(new Quat(maQuat, tenQuat, gia, maNSX, formattedDate, chatLieu, thuongHieu, maLoaiSP));
        return true;
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Lỗi khi lưu quạt: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        return false;
    }
}


    // Xóa quạt theo mã quạt
    public boolean xoa(String maQuat) {
        try {
            dao.delete(maQuat);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Sửa thông tin quạt
    public boolean sua(String maQuat, String tenQuat, int gia, String maNSX, String ngaySanXuat, String chatLieu, String thuongHieu, String maLoaiSP) {
        try {
            dao.update(new Quat(maQuat, tenQuat, gia, maNSX, ngaySanXuat, chatLieu, thuongHieu, maLoaiSP));
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Tìm kiếm quạt theo mã quạt
    public Quat timTheoMaQuat(String maQuat) {
        try {
            return dao.findByMaQuat(maQuat);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Tìm kiếm gần đúng theo tên quạt
    public List<Quat> timTheoTenQuat(String keyword) {
        try {
            return dao.findByTenQuat(keyword);
        } catch (SQLException e) {
            e.printStackTrace();
            return List.of();
        }
    }

    // Tìm kiếm gần đúng theo thương hiệu
    public List<Quat> timTheoThuongHieu(String keyword) {
        try {
            return dao.findByThuongHieu(keyword);
        } catch (SQLException e) {
            e.printStackTrace();
            return List.of();
        }
    }
}
