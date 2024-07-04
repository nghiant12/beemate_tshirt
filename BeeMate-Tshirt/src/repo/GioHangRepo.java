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
import java.util.UUID;
import model.HoaDonCT;
import model.KieuDang;
import model.Mau;
import model.SanPham;
import model.SanPhamCT;
import model.Size;
import model.ThuongHieu;
import utils.JdbcUtil;

/**
 *
 * @author maymimim
 */
public class GioHangRepo {

    private Connection conn;

    public GioHangRepo() {
        this.conn = JdbcUtil.getConnection();
    }

    public ArrayList<HoaDonCT> getGioHang(int id_hd) {
        ArrayList<HoaDonCT> ds = new ArrayList<>();
        String sql = """
                     select hdct.Id, spct.Id, sp.TenSP, m.TenMau, s.TenSize, kd.TenKieuDang, th.TenTH, hdct.SoLuong, spct.DonGia
                     from HoaDonChiTiet hdct
                         join HoaDon hd on hd.Id = hdct.Id_HD
                         join SanPhamChiTiet spct on spct.Id = hdct.Id_SPCT
                         join SanPham sp on sp.Id = spct.Id_SP
                         join MauSac m on m.Id = spct.Id_Mau
                         join [Size] s on s.Id = spct.Id_Size
                         join KieuDang kd on kd.Id = spct.Id_KD
                         join ThuongHieu th on th.Id = spct.Id_TH
                     where hd.Id = ?""";
        try {
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setInt(1, id_hd);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                HoaDonCT hdct = new HoaDonCT();
                String id_hdct = rs.getString(1);
                UUID id = UUID.fromString(id_hdct);
                hdct.setId(id);

                SanPhamCT spct = new SanPhamCT();
                spct.setId(rs.getInt(2));

                SanPham s = new SanPham();
                s.setTenSP(rs.getString(3));

                Mau m = new Mau();
                m.setTenMau(rs.getString(4));

                Size size = new Size();
                size.setTenSize(rs.getString(5));

                KieuDang kd = new KieuDang();
                kd.setTenKD(rs.getString(6));

                ThuongHieu th = new ThuongHieu();
                th.setTenTH(rs.getString(7));

                hdct.setSoLuong(rs.getInt(8));

                spct.setSp(s);
                spct.setM(m);
                spct.setS(size);
                spct.setKd(kd);
                spct.setTh(th);
                spct.setDonGia(rs.getFloat(9));

                hdct.setSpct(spct);

                ds.add(hdct);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    public int getTongTien(int id_hd) {
        String sql = """
                     SELECT SUM(GiaHienHanh * SoLuong)
                     FROM HoaDonChiTiet
                     WHERE Id_HD = ?""";
        try {
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setInt(1, id_hd);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    //Chỉnh sửa giỏ hàng
    public int getSoLuongById_SPCT(int id_spct) {
        String sql = "select SoLuong from SanPhamChiTiet where Id = ?";
        try {
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setInt(1, id_spct);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                return rs.getInt("SoLuong");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int updateSoLuongHDCT(int newSoLuong, String id_hdct) {
        String sql = "update HoaDonChiTiet set SoLuong = ? where Id like ?";
        try {
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setInt(1, newSoLuong);
            ps.setString(2, id_hdct);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int updateSoLuongSPCT(int newSoLuong, int id_spct) {
        String sql = "update SanPhamChiTiet set SoLuong = ? where Id = ?";
        try {
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setInt(1, newSoLuong);
            ps.setInt(2, id_spct);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    //Làm mới giỏ hàng
    public void clearCart(int id_hd) {
        String sql = """
                     UPDATE SanPhamChiTiet SET SoLuong = s.SoLuong + h.SoLuong
                     FROM HoaDonChiTiet h JOIN SanPhamChiTiet s ON s.Id = h.Id_SPCT
                     WHERE h.Id_HD = ? 
                     DELETE FROM HoaDonChiTiet WHERE Id_HD = ?""";
        try {
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setInt(1, id_hd);
            ps.setInt(2, id_hd);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Thêm SP vào giỏ hàng
    public void themSPGH(int id_hd, int id_spct, int newSoLuong, float giaHienHanh) {
        try {
            // Kiểm tra SPCT đã có trong Giỏ hàng chưa
            PreparedStatement ps = this.conn.prepareStatement("SELECT * FROM HoaDonChiTiet WHERE Id_HD = ? AND Id_SPCT = ?");
            ps.setInt(1, id_hd);
            ps.setInt(2, id_spct);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                // SPCT đã có trong Giỏ hàng -> tăng số lượng
                ps = conn.prepareStatement("UPDATE HoaDonChiTiet SET soLuong = soLuong + ? WHERE Id_HD = ? AND Id_SPCT = ?");
                ps.setInt(1, newSoLuong);
                ps.setInt(2, id_hd);
                ps.setInt(3, id_spct);
                ps.executeUpdate();
            } else {
                // SPCT chưa có trong Giỏ hàng -> thêm mới
                ps = conn.prepareStatement("INSERT INTO HoaDonChiTiet (Id, Id_HD, Id_SPCT, SoLuong, GiaHienHanh) VALUES (?, ?, ?, ?, ?)");
                ps.setObject(1, UUID.randomUUID());
                ps.setInt(2, id_hd);
                ps.setInt(3, id_spct);
                ps.setInt(4, newSoLuong);
                ps.setFloat(5, giaHienHanh);
                ps.executeUpdate();
            }
            // Trừ số lượng trong bảng SPCT
            ps = conn.prepareStatement("UPDATE SanPhamChiTiet SET SoLuong = SoLuong - ? WHERE id = ?");
            ps.setInt(1, newSoLuong);
            ps.setInt(2, id_spct);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Xóa HDCT
    public void deleteHdctById_Spct(int id_spct) {
        String sql = "DELETE FROM HoaDonChiTiet WHERE Id_SPCT = ?";
        try {
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setInt(1, id_spct);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
