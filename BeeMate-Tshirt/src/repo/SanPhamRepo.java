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
import java.util.List;
import model.SanPham;
import utils.JdbcUtil;

/**
 *
 * @author maymimim
 */
public class SanPhamRepo {

    private Connection conn;

    public SanPhamRepo() {
        this.conn = JdbcUtil.getConnection();
    }

    public ArrayList<SanPham> findAll() {
        ArrayList<SanPham> ds = new ArrayList<>();
        String sql = "SELECT Id, MaSP, TenSP, XuatXu,TrangThai FROM SanPham";
        try {
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SanPham s = new SanPham();
                s.setId(rs.getInt(1));
                s.setMaSP(rs.getString(2));
                s.setTenSP(rs.getString(3));
                s.setXuatXu(rs.getString(4));
                s.setTrangThai(rs.getInt(5));
                ds.add(s);
            }
            return ds;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<SanPham> findAllSP_DangBan() {
        ArrayList<SanPham> ds = new ArrayList<>();
        String sql = "SELECT Id, MaSP, TenSP, XuatXu,TrangThai FROM SanPham where TrangThai = 0";
        try {
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SanPham s = new SanPham();
                s.setId(rs.getInt(1));
                s.setMaSP(rs.getString(2));
                s.setTenSP(rs.getString(3));
                s.setXuatXu(rs.getString(4));
                s.setTrangThai(rs.getInt(5));
                ds.add(s);
            }
            return ds;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<SanPham> findAllSP_DungBan() {
        ArrayList<SanPham> ds = new ArrayList<>();
        String sql = "SELECT Id, MaSP, TenSP, XuatXu,TrangThai FROM SanPham where TrangThai = 1";
        try {
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SanPham s = new SanPham();
                s.setId(rs.getInt(1));
                s.setMaSP(rs.getString(2));
                s.setTenSP(rs.getString(3));
                s.setXuatXu(rs.getString(4));
                s.setTrangThai(rs.getInt(5));
                ds.add(s);
            }
            return ds;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void create(SanPham s) {
        String sql = "INSERT INTO SanPham(MaSP, TenSP, XuatXu, TrangThai) VALUES (?,?,?,?)";
        try {
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setString(1, s.getMaSP());
            ps.setString(2, s.getTenSP());
            ps.setString(3, s.getXuatXu());
            ps.setInt(4, s.getTrangThai());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(SanPham s, int id_sp) {
        String sql = "UPDATE SanPham SET MaSP = ?, TenSP = ?, XuatXu = ?, TrangThai = ? WHERE Id = ?";
        try {
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setString(1, s.getMaSP());
            ps.setString(2, s.getTenSP());
            ps.setString(3, s.getXuatXu());
            ps.setInt(4, s.getTrangThai());
            ps.setInt(5, id_sp);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkTrungMaSP(String maSP) {
        boolean check = false;
        List<SanPham> list = this.findAll();
        for (SanPham sanPham : list) {
            if (maSP == null ? sanPham.getMaSP() == null : maSP.equals(sanPham.getMaSP())) {
                check = true;
                break;
            }
        }
        return check;
    }

    public ArrayList<SanPham> searchAndPaging(String keyword, int trangThai, int page, int limit) {
        ArrayList<SanPham> ds = new ArrayList<>();
        String sql = "SELECT * FROM SanPham WHERE TrangThai = ?";
        if (keyword.length() != 0) {
            sql += "AND (MaSP LIKE ? OR TenSP LIKE ?)";
        }
        sql += "ORDER BY Id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try {
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setInt(1, trangThai);
            int offset = ((page - 1) * limit);
            if (keyword.length() != 0) {
                ps.setString(2, "%" + keyword + "%");
                ps.setString(3, "%" + keyword + "%");
                ps.setInt(4, offset);
                ps.setInt(5, limit);
            } else {
                ps.setInt(2, offset);
                ps.setInt(3, limit);
            }

            ps.execute();

            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                int id = rs.getInt("Id");
                String ma = rs.getString("MaSP");
                String ten = rs.getString("TenSP");
                String x = rs.getString("XuatXu");
                int stt = rs.getInt("TrangThai");
                SanPham s = new SanPham(id, ma, ten, x, stt);
                ds.add(s);
            }
            return ds;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getTotalItems() {
        int totalItems = 0;
        String query = "SELECT COUNT(*) FROM SanPham";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                totalItems = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalItems;
    }
    
    public boolean checkTrungSanPham(String tenSP, String xuatXu) throws SQLException {
        String query = "SELECT COUNT(*) FROM SanPham WHERE TenSP = ? AND XuatXu = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, tenSP);
            stmt.setString(2, xuatXu);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Trả về true nếu tồn tại sản phẩm với các thuộc tính tương tự
                }
            }
        }
        return false; // Trả về false nếu không tồn tại sản phẩm với các thuộc tính tương tự
    }
}
