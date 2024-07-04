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
import model.KieuDang;
import utils.JdbcUtil;

/**
 *
 * @author maymimim
 */
public class KieuDangRepo {

    private Connection conn;

    public KieuDangRepo() {
        this.conn = (Connection) JdbcUtil.getConnection();
    }

    public ArrayList<KieuDang> findAll() {
        ArrayList<KieuDang> ds = new ArrayList<>();
        String sql = "SELECT Id, MaKD, TenKieuDang, MoTa, TrangThai FROM KieuDang where TrangThai = 0";

        try {
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                KieuDang k = new KieuDang();
                k.setId(rs.getInt(1));
                k.setMaKD(rs.getString(2));
                k.setTenKD(rs.getString(3));
                k.setMoTa(rs.getString(4));
                k.setTrangThai(rs.getInt(5));
                ds.add(k);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    public ArrayList<KieuDang> findAll_Unactive() {
        ArrayList<KieuDang> ds = new ArrayList<>();
        String sql = "SELECT Id, MaKD, TenKieuDang, MoTa, TrangThai FROM KieuDang where TrangThai = 1";

        try {
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                KieuDang k = new KieuDang();
                k.setId(rs.getInt(1));
                k.setMaKD(rs.getString(2));
                k.setTenKD(rs.getString(3));
                k.setMoTa(rs.getString(4));
                k.setTrangThai(rs.getInt(5));
                ds.add(k);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    public ArrayList<KieuDang> findAllKd() {
        ArrayList<KieuDang> ds = new ArrayList<>();
        String sql = "SELECT Id, MaKD, TenKieuDang, MoTa, TrangThai FROM KieuDang";

        try {
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                KieuDang k = new KieuDang();
                k.setId(rs.getInt(1));
                k.setMaKD(rs.getString(2));
                k.setTenKD(rs.getString(3));
                k.setMoTa(rs.getString(4));
                k.setTrangThai(rs.getInt(5));
                ds.add(k);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    public int create(KieuDang k) {
        String sql = "INSERT INTO KieuDang(MaKD, TenKieuDang, MoTa, TrangThai) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setObject(1, k.getMaKD());
            ps.setString(2, k.getTenKD());
            ps.setString(3, k.getMoTa());
            ps.setInt(4, k.getTrangThai());
            ps.execute();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int update(KieuDang k, String ma) {
        String sql = "UPDATE KieuDang SET TenKieuDang = ?, MoTa = ?, TrangThai = ? WHERE MaKD = ?";
        try {
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setString(1, k.getTenKD());
            ps.setString(2, k.getMoTa());
            ps.setInt(3, k.getTrangThai());
            ps.setObject(4, ma);
            ps.executeUpdate();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean checkTrungMa(String ma) {
        boolean check = false;
        List<KieuDang> list = this.findAllKd();
        for (KieuDang k : list) {
            if (ma == null ? k.getMaKD() == null : ma.equals(k.getMaKD())) {
                check = true;
                break;
            }
        }
        return check;
    }

}
