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
import model.SanPhamCT;
import utils.JdbcUtil;

/**
 *
 * @author maymimim
 */
public class HoaDonCTRepo {

    private Connection conn;

    public HoaDonCTRepo() {
        this.conn = JdbcUtil.getConnection();
    }

    public ArrayList<HoaDonCT> findByIdHd(int id_hd) {
        ArrayList<HoaDonCT> ds = new ArrayList<>();
        String sql = """
                     select hdct.Id, spct.MaSPCT, hdct.SoLuong, spct.DonGia, hdct.TrangThai
                     from HoaDonChiTiet hdct
                         join HoaDon hd on hd.Id = hdct.Id_HD
                         join SanPhamChiTiet spct on spct.Id = hdct.Id_SPCT
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
                spct.setMaSPCT(rs.getString(2));

                hdct.setSoLuong(rs.getInt(3));
                spct.setDonGia(rs.getFloat(4));
                hdct.setSpct(spct);
                hdct.setTrangThai(rs.getInt(5));
                ds.add(hdct);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    public ArrayList<HoaDonCT> paging(int id_hd, int page, int limit) {
        ArrayList<HoaDonCT> ds = new ArrayList<>();
        String sql = """
                     select hdct.Id, spct.MaSPCT, hdct.SoLuong, spct.DonGia, hdct.TrangThai
                     from HoaDonChiTiet hdct
                          join HoaDon hd on hd.Id = hdct.Id_HD
                          join SanPhamChiTiet spct on spct.Id = hdct.Id_SPCT
                     where hd.Id = ? 
                     ORDER BY hdct.Id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY""";
        try {
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setInt(1, id_hd);
            int offset = (page - 1) * limit; // Offset starts from 0
            ps.setInt(2, offset);
            ps.setInt(3, limit);

            ResultSet rs = ps.executeQuery(); // Use executeQuery to get ResultSet
            while (rs.next()) {
                HoaDonCT hdct = new HoaDonCT();
                String id_hdct = rs.getString(1);
                UUID id = UUID.fromString(id_hdct);
                hdct.setId(id);

                SanPhamCT spct = new SanPhamCT();
                spct.setMaSPCT(rs.getString(2));

                hdct.setSoLuong(rs.getInt(3));
                spct.setDonGia(rs.getFloat(4));
                hdct.setSpct(spct);
                hdct.setTrangThai(rs.getInt(5));
                ds.add(hdct);
            }
            return ds;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getTotalItems() {
        int totalItems = 0;
        String query = "SELECT COUNT(*) FROM HoaDonChiTiet";
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
}
