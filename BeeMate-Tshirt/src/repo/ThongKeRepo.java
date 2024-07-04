/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import model.ThongKe;
import utils.JdbcUtil;

/**
 *
 * @author USER
 */
public class ThongKeRepo {

    private Connection connection;

    public ThongKeRepo() {
        this.connection = (Connection) JdbcUtil.getConnection();
    }

    public ArrayList<ThongKe> thongKeTatCaSanPhamBanRaTuTruocDenNay() {
        ArrayList<ThongKe> ds = new ArrayList<>();
        String sql = "select c.MaSP,c.TenSP,SUM(a.SoLuong) from HoaDonChiTiet a \n"
                + "join SanPhamChiTiet b on b.Id=a.Id_SPCT\n"
                + "join SanPham c on c.Id=b.Id_SP\n"
                + "join HoaDon d on d.Id=a.Id_HD\n"
                + "where d.TrangThai=1\n"
                + "group by c.MaSP,c.TenSP";

        try {
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String maSP = rs.getString(1);
                String tenSP = rs.getString(2);
                int soLuong = rs.getInt(3);
                ThongKe thongKe = new ThongKe(maSP, tenSP, soLuong);
                ds.add(thongKe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    public ArrayList<ThongKe> thongKeTatCaSanPhamBanRaTrongHomNay() {
        ArrayList<ThongKe> ds = new ArrayList<>();
        String sql = "SELECT c.MaSP, c.TenSP, SUM(a.SoLuong) AS TongSoLuong\n"
                + "FROM HoaDonChiTiet a \n"
                + "JOIN SanPhamChiTiet b ON b.Id = a.Id_SPCT\n"
                + "JOIN SanPham c ON c.Id = b.Id_SP\n"
                + "JOIN HoaDon d ON d.Id = a.Id_HD\n"
                + "WHERE d.TrangThai = 1\n"
                + "AND CAST(d.NgayTao AS DATE) = CAST(GETDATE() AS DATE)\n"
                + "GROUP BY c.MaSP, c.TenSP;";

        try {
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String maSP = rs.getString(1);
                String tenSP = rs.getString(2);
                int soLuong = rs.getInt(3);
                ThongKe thongKe = new ThongKe(maSP, tenSP, soLuong);
                ds.add(thongKe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    public ArrayList<ThongKe> thongKeTatCaSanPhamBanRaTrongThangNay() {
        ArrayList<ThongKe> ds = new ArrayList<>();
        String sql = "SELECT c.MaSP, c.TenSP, SUM(a.SoLuong) AS TongSoLuong\n"
                + "FROM HoaDonChiTiet a \n"
                + "JOIN SanPhamChiTiet b ON b.Id = a.Id_SPCT\n"
                + "JOIN SanPham c ON c.Id = b.Id_SP\n"
                + "JOIN HoaDon d ON d.Id = a.Id_HD\n"
                + "WHERE d.TrangThai = 1\n"
                + "AND MONTH(d.NgayTao) = MONTH(GETDATE())\n"
                + "AND YEAR(d.NgayTao) = YEAR(GETDATE())\n"
                + "GROUP BY c.MaSP, c.TenSP;";

        try {
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String maSP = rs.getString(1);
                String tenSP = rs.getString(2);
                int soLuong = rs.getInt(3);
                ThongKe thongKe = new ThongKe(maSP, tenSP, soLuong);
                ds.add(thongKe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    public ArrayList<ThongKe> thongKeTatCaSanPhamBanRaTrongNamNay() {
        ArrayList<ThongKe> ds = new ArrayList<>();
        String sql = "SELECT c.MaSP, c.TenSP, SUM(a.SoLuong) AS TongSoLuong\n"
                + "FROM HoaDonChiTiet a \n"
                + "JOIN SanPhamChiTiet b ON b.Id = a.Id_SPCT\n"
                + "JOIN SanPham c ON c.Id = b.Id_SP\n"
                + "JOIN HoaDon d ON d.Id = a.Id_HD\n"
                + "WHERE d.TrangThai = 1\n"
                + "AND YEAR(d.NgayTao) = YEAR(GETDATE())\n"
                + "GROUP BY c.MaSP, c.TenSP;";

        try {
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String maSP = rs.getString(1);
                String tenSP = rs.getString(2);
                int soLuong = rs.getInt(3);
                ThongKe thongKe = new ThongKe(maSP, tenSP, soLuong);
                ds.add(thongKe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    public ArrayList<ThongKe> thongKeTatCaSanPhamBanTrongKhoangThoiGian(Date ngayBatDau, Date ngayKetThuc) {
        ArrayList<ThongKe> ds = new ArrayList<>();
        String sql = "SELECT c.MaSP, c.TenSP, SUM(a.SoLuong) AS TongSoLuong\n"
                + "FROM HoaDonChiTiet a \n"
                + "JOIN SanPhamChiTiet b ON b.Id = a.Id_SPCT\n"
                + "JOIN SanPham c ON c.Id = b.Id_SP\n"
                + "JOIN HoaDon d ON d.Id = a.Id_HD\n"
                + "WHERE d.TrangThai = 1\n"
                + "AND d.NgayTao BETWEEN ? AND ?\n"
                + "GROUP BY c.MaSP, c.TenSP;";

        try {
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setDate(1, new java.sql.Date(ngayBatDau.getTime()));
            ps.setDate(2, new java.sql.Date(ngayKetThuc.getTime()));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String maSP = rs.getString(1);
                String tenSP = rs.getString(2);
                int soLuong = rs.getInt(3);
                ThongKe thongKe = new ThongKe(maSP, tenSP, soLuong);
                ds.add(thongKe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    public float tongDoanhThu() {
        String sql = "select SUM(ThanhTien) from HoaDon where TrangThai=1";
        float tongDoanhThu = 0;
        try {
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tongDoanhThu = rs.getFloat(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tongDoanhThu;
    }

    public float tongDoanhThuHomNay() {
        String sql = "select sum(ThanhTien) from HoaDon where TrangThai=1 AND CAST(NgayTao AS DATE) = CAST(GETDATE() AS DATE)";
        float tongDoanhThu = 0;
        try {
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tongDoanhThu = rs.getFloat(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tongDoanhThu;
    }

    public float tongDoanhThuThangNay() {
        String sql = "select sum(ThanhTien) from HoaDon where TrangThai=1  AND MONTH(NgayTao) = MONTH(GETDATE()) AND YEAR(NgayTao) = YEAR(GETDATE())";
        float tongDoanhThu = 0;
        try {
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tongDoanhThu = rs.getFloat(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tongDoanhThu;
    }

    public float tongDoanhThuNamNay() {
        String sql = "select sum(ThanhTien) from HoaDon where TrangThai=1 AND YEAR(NgayTao) = YEAR(GETDATE())";
        float tongDoanhThu = 0;
        try {
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tongDoanhThu = rs.getFloat(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tongDoanhThu;
    }

    public float tongDoanhThuTrongKhoang(Date ngayBatDau, Date ngayKetThuc) {
        String sql = "select sum(ThanhTien) from HoaDon where TrangThai=1  AND NgayTao BETWEEN ? AND ?";
        float tongDoanhThu = 0;
        try {

            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setDate(1, new java.sql.Date(ngayBatDau.getTime()));
            ps.setDate(2, new java.sql.Date(ngayKetThuc.getTime()));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tongDoanhThu = rs.getFloat(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tongDoanhThu;
    }

    public int tongSoHoaDon() {
        String sql = "select count(Id) from HoaDon where TrangThai=1";
        int tongSoHoaDon = 0;
        try {
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tongSoHoaDon = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tongSoHoaDon;
    }

    public int tongSoHoaDonNamNay() {
        String sql = "select count(Id) from HoaDon where TrangThai=1 AND YEAR(NgayTao) = YEAR(GETDATE())";
        int tongSoHoaDon = 0;
        try {
            PreparedStatement ps = this.connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tongSoHoaDon = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tongSoHoaDon;
    }

    public int tongSoHoaDonTrongKhoang(Date ngayBatDau, Date ngayKetThuc) {
        String sql = "select count(Id) from HoaDon where TrangThai=1 AND NgayTao BETWEEN ? AND ?";
        int tongSoHoaDon = 0;
        try {
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setDate(1, new java.sql.Date(ngayBatDau.getTime()));
            ps.setDate(2, new java.sql.Date(ngayKetThuc.getTime()));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tongSoHoaDon = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tongSoHoaDon;
    }

    public int tongSoHoaDonThangNay() {
        String sql = "select count(Id) from HoaDon where TrangThai=1 AND MONTH(NgayTao) = MONTH(GETDATE()) AND YEAR(NgayTao) = YEAR(GETDATE())";
        int tongSoHoaDon = 0;
        try {
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tongSoHoaDon = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tongSoHoaDon;
    }

    public int tongSoHoaDonHomNay() {
        String sql = "select count(Id) from HoaDon where TrangThai=1 AND CAST(NgayTao AS DATE) = CAST(GETDATE() AS DATE)";
        int tongSoHoaDon = 0;
        try {
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tongSoHoaDon = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tongSoHoaDon;
    }

    public int tongSoLuongSanPhamDaBan() {
        String sql = "SELECT SUM(a.SoLuong) FROM HoaDonChiTiet a \n"
                + "JOIN SanPhamChiTiet b ON b.Id = a.Id_SPCT \n"
                + "JOIN SanPham c ON c.Id = b.Id_SP\n"
                + "JOIN HoaDon d ON d.Id = a.Id_HD\n"
                + "WHERE d.TrangThai = 1";
        int tongSP = 0;
        try {
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tongSP = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tongSP;
    }

    public int tongSoLuongSanPhamDaBanHomNay() {
        String sql = "SELECT SUM(a.SoLuong) FROM HoaDonChiTiet a \n"
                + "JOIN SanPhamChiTiet b ON b.Id = a.Id_SPCT \n"
                + "JOIN SanPham c ON c.Id = b.Id_SP\n"
                + "JOIN HoaDon d ON d.Id = a.Id_HD\n"
                + "WHERE d.TrangThai = 1 AND CAST(d.NgayTao AS DATE) = CAST(GETDATE() AS DATE)";
        int tongSP = 0;
        try {
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tongSP = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tongSP;
    }

    public int tongSoLuongSanPhamDaBanThangNay() {
        String sql = "SELECT SUM(a.SoLuong) FROM HoaDonChiTiet a \n"
                + "JOIN SanPhamChiTiet b ON b.Id = a.Id_SPCT \n"
                + "JOIN SanPham c ON c.Id = b.Id_SP\n"
                + "JOIN HoaDon d ON d.Id = a.Id_HD\n"
                + "WHERE d.TrangThai = 1 AND MONTH(d.NgayTao) = MONTH(GETDATE()) AND YEAR(d.NgayTao) = YEAR(GETDATE())";
        int tongSP = 0;
        try {
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tongSP = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tongSP;
    }

    public int tongSoLuongSanPhamDaBanNamNay() {
        String sql = "SELECT SUM(a.SoLuong) FROM HoaDonChiTiet a \n"
                + "JOIN SanPhamChiTiet b ON b.Id = a.Id_SPCT \n"
                + "JOIN SanPham c ON c.Id = b.Id_SP\n"
                + "JOIN HoaDon d ON d.Id = a.Id_HD\n"
                + "WHERE d.TrangThai = 1 AND YEAR(d.NgayTao) = YEAR(GETDATE())";
        int tongSP = 0;
        try {
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tongSP = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tongSP;
    }

    public int tongSoLuongSanPhamDaTrongKhoang(Date ngayBatDau, Date ngayKetThuc) {
        String sql = "SELECT SUM(a.SoLuong) FROM HoaDonChiTiet a \n"
                + "JOIN SanPhamChiTiet b ON b.Id = a.Id_SPCT \n"
                + "JOIN SanPham c ON c.Id = b.Id_SP\n"
                + "JOIN HoaDon d ON d.Id = a.Id_HD\n"
                + "WHERE d.TrangThai = 1 AND d.NgayTao BETWEEN ? AND ?";
        int tongSP = 0;
        try {
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setDate(1, new java.sql.Date(ngayBatDau.getTime()));
            ps.setDate(2, new java.sql.Date(ngayKetThuc.getTime()));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tongSP = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tongSP;
    }
}
