package GUI;

import Database.DBConnection;
import GUI.Component.MenuTaskbar;
import GUI.Panel.TrangChu;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

/**
 * Frame chính cho vai trò Quản lý (QuanLy).
 */
public class ManagerMainFrame extends JFrame {
    private final String maNhanVien;
    private MenuTaskbar menuTaskbar;
    private JPanel mainContent;

    /**
     * Constructor chính, nhận vào mã nhân viên để load tên.
     */
    public ManagerMainFrame(String maNhanVien) {
        this.maNhanVien = maNhanVien;
        initLookAndFeel();
        initComponent();
    }

    /**
     * Constructor không tham số (vd. khi gọi từ Admin hoặc test).
     */
    public ManagerMainFrame() {
        this(null);
    }

    /**
     * Thiết lập FlatLaf và font Roboto.
     */
    private void initLookAndFeel() {
        FlatRobotoFont.install();
        FlatLaf.setPreferredFontFamily(FlatRobotoFont.FAMILY);
        FlatLaf.setPreferredLightFontFamily(FlatRobotoFont.FAMILY_LIGHT);
        FlatLaf.setPreferredSemiboldFontFamily(FlatRobotoFont.FAMILY_SEMIBOLD);
        FlatIntelliJLaf.setup();
        UIManager.put("PasswordField.showRevealButton", true);
    }

    /**
     * Xây dựng giao diện chính: header, menu và vùng nội dung.
     */
    private void initComponent() {
        setTitle("Giao diện Quản lý");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(0, 0));

        // Load tên NV nếu có, ngược lại hiển thị "Admin"
        String tenHienThi = (maNhanVien != null)
            ? loadTenNhanVien(maNhanVien)
            : "Admin";
        JLabel lblInfo = new JLabel("Nhân viên: " + tenHienThi);
        lblInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(lblInfo, BorderLayout.NORTH);

        // Menu bên trái (full quyền)
        menuTaskbar = new MenuTaskbar(this, true);
        menuTaskbar.setPreferredSize(new Dimension(250, 800));
        add(menuTaskbar, BorderLayout.WEST);

        // Vùng nội dung
        mainContent = new JPanel(new BorderLayout());
        mainContent.setBackground(Color.WHITE);
        add(mainContent, BorderLayout.CENTER);

        // Mặc định hiển thị Trang Chủ
        setPanel(new TrangChu());
    }

    /**
     * Truy vấn CSDL để lấy tên nhân viên theo mã.
     */
    private String loadTenNhanVien(String maNV) {
        String sql = "SELECT HoTenNV FROM nhanvien WHERE MaNhanVien = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maNV);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("HoTenNV");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Nếu lỗi hoặc không tìm thấy, trả về chính mã
        return maNV;
    }

    /**
     * Cho phép các panel con thay thế vùng nội dung trung tâm.
     */
    public void setPanel(JPanel panel) {
        mainContent.removeAll();
        mainContent.add(panel, BorderLayout.CENTER);
        mainContent.revalidate();
        mainContent.repaint();
    }
}
