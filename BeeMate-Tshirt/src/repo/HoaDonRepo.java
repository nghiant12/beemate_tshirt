/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repo;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import model.HoaDon;
import model.KhachHang;
import model.NhanVien;
import utils.JdbcUtil;

/**
 *
 * @author maymimim
 */
public class HoaDonRepo {

    private Connection conn;

    public HoaDonRepo() {
        this.conn = JdbcUtil.getConnection();
    }

    public ArrayList<HoaDon> getHoaDonCho() {
        ArrayList<HoaDon> ds = new ArrayList<>();
        String sql = """
                     select h.Id, h.MaHD, n.MaNV, k.HoTen, h.TrangThai, h.NgayTao
                     from HoaDon h 
                     join NhanVien n on n.Id=h.Id_NV
                     join KhachHang k on k.Id=h.Id_KH
                     where h.TrangThai = 0""";
        try {
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                HoaDon h = new HoaDon();
                h.setId(rs.getInt(1));
                h.setMaHD(rs.getString(2));
                NhanVien n = new NhanVien();
                n.setMaNV(rs.getString(3));
                h.setNv(n);
                KhachHang k = new KhachHang();
                k.setHoTen(rs.getString(4));
                h.setKh(k);
                h.setTrangThai(rs.getInt(5));
                h.setNgayTao(rs.getDate(6));
                ds.add(h);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    public int taoHoaDonCho(int id_NV, int id_KH) {
        String sql = """
               INSERT INTO HoaDon
               (Id_NV, Id_KH, MaHD, NgayTao, TongTien, SoTienGiamGia, HinhThucThanhToan, ThanhTien, TrangThai)
                VALUES (?,  ?, ? , getdate(), 0, 0, 0, 0, 0)""";
        try {
            HoaDon hoaDon = new HoaDon();
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setInt(1, id_NV);
            ps.setInt(2, id_KH);
            ps.setObject(3, generateCode());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String generateCode() {
        Random random = new SecureRandom();
        StringBuilder code = new StringBuilder(CODE_LENGTH);

        for (int i = 0; i < CODE_LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            code.append(CHARACTERS.charAt(randomIndex));
        }

        return "HD-" + code.toString();
    }
    private static final String CHARACTERS = "0123456789";
    private static final int CODE_LENGTH = 5;

    public int thanhToanHoaDon(
            int id_hd,
            Integer id_km, // Sử dụng Integer để có thể chứa giá trị null
            float tongTien,
            float soTienGiamGia,
            String hinhThucThanhToan,
            String ghiChu,
            float thanhTien
    ) {
        String sql = "UPDATE HoaDon SET Id_KM = ?, TongTien = ?, SoTienGiamGia = ?, HinhThucThanhToan = ?, GhiChu = ?, ThanhTien = ?, TrangThai = 1 WHERE Id = ?";
        try {
            PreparedStatement ps = this.conn.prepareStatement(sql);

            if (id_km == null) {
                System.out.println("NULL");
                ps.setNull(1, java.sql.Types.INTEGER); // Thiết lập giá trị null cho Id_KM
            } else {
                System.out.println(id_km);
                ps.setInt(1, id_km); // Thiết lập giá trị cụ thể cho Id_KM
            }

            ps.setFloat(2, tongTien);
            ps.setFloat(3, soTienGiamGia);
            ps.setString(4, hinhThucThanhToan);
            ps.setString(5, ghiChu);
            ps.setFloat(6, thanhTien);
            ps.setInt(7, id_hd); // Thay đổi từ ps.setObject(7, id_hd) sang ps.setInt(7, id_hd)

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public ArrayList<HoaDon> findAll() {
        ArrayList<HoaDon> ds = new ArrayList<>();
        String sql = """
                     select h.MaHD, n.MaNV, k.HoTen, h.NgayTao,
                     h.TongTien, h.SoTienGiamGia, h.HinhThucThanhToan,
                     h.GhiChu, h.ThanhTien, h.TrangThai
                     from HoaDon h 
                     join NhanVien n on n.Id=h.Id_NV
                     join KhachHang k on k.Id=h.Id_KH""";
        try {
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                HoaDon h = new HoaDon();
                h.setMaHD(rs.getString(1));
                NhanVien n = new NhanVien();
                n.setMaNV(rs.getString(2));
                h.setNv(n);
                KhachHang k = new KhachHang();
                k.setHoTen(rs.getString(3));
                h.setKh(k);
                h.setNgayTao(rs.getDate(4));
                h.setTongTien(rs.getFloat(5));
                h.setSoTienGiamGia(rs.getFloat(6));
                h.setHinhThucThanhToan(rs.getString(7));
                h.setGhiChu(rs.getString(8));
                h.setThanhTien(rs.getFloat(9));
                h.setTrangThai(rs.getInt(10));
                ds.add(h);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    public ArrayList<HoaDon> paging(int page, int limit) {
        ArrayList<HoaDon> ds = new ArrayList<>();
        String sql = """
                     SELECT h.MaHD, n.MaNV, k.HoTen, h.NgayTao,
                            h.TongTien, h.SoTienGiamGia, h.HinhThucThanhToan,
                            h.GhiChu, h.ThanhTien, h.TrangThai
                     FROM HoaDon h 
                          join NhanVien n on n.Id=h.Id_NV
                          join KhachHang k on k.Id=h.Id_KH 
                     ORDER BY h.Id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY""";
        try {
            PreparedStatement ps = this.conn.prepareStatement(sql);
            int offset = ((page - 1) * limit); // Offset starts from 0
            ps.setInt(1, offset);
            ps.setInt(2, limit);

            ResultSet rs = ps.executeQuery(); // Use executeQuery to get ResultSet

            while (rs.next()) {
                HoaDon h = new HoaDon();
                h.setMaHD(rs.getString("MaHD")); // Use column names instead of indices for better readability and maintainability
                NhanVien n = new NhanVien();
                n.setHoTen(rs.getString("HoTen"));
                h.setNv(n);
                KhachHang k = new KhachHang();
                k.setHoTen(rs.getString("HoTen"));
                h.setKh(k);
                h.setNgayTao(rs.getDate("NgayTao"));
                h.setTongTien(rs.getFloat("TongTien"));
                h.setSoTienGiamGia(rs.getFloat("SoTienGiamGia"));
                h.setHinhThucThanhToan(rs.getString("HinhThucThanhToan"));
                h.setGhiChu(rs.getString("GhiChu"));
                h.setThanhTien(rs.getFloat("ThanhTien"));
                h.setTrangThai(rs.getInt("TrangThai"));
                ds.add(h);
            }
            return ds;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getTotalItems() {
        int totalItems = 0;
        String query = "SELECT COUNT(*) FROM HoaDon";
        try {
            PreparedStatement ps = this.conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                totalItems = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalItems;
    }

    public int getIdHd(int rowIndex) {
        int hdId = -1;
        String sql = "SELECT Id FROM HoaDon ORDER BY Id OFFSET ? ROWS FETCH NEXT 1 ROW ONLY";
        try {
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setInt(1, rowIndex);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                hdId = resultSet.getInt("Id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hdId;
    }

    public int capNhatHoaDon(int rowIndex) {
        String sql = "UPDATE HoaDon SET TrangThai = 1 where Id = ?";
        try {
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setInt(1, rowIndex);
            ps.executeUpdate();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
