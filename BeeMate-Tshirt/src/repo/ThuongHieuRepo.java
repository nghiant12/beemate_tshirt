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
import model.ThuongHieu;
import utils.JdbcUtil;

/**
 *
 * @author USER
 */
public class ThuongHieuRepo {

    private Connection connection;

    public ThuongHieuRepo() {
        this.connection = (Connection) JdbcUtil.getConnection();
    }

    public ArrayList<ThuongHieu> findAllThuongHieu() {
        ArrayList<ThuongHieu> ds = new ArrayList<>();
        String sql = "select Id,MaTH,TenTH,TrangThai from ThuongHieu where TrangThai = 0";

        try {
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("Id");
                String maTH = rs.getString("MaTH");
                String tenTH = rs.getString("TenTH");
                int trangThai = rs.getInt("TrangThai");

                ThuongHieu thuongHieu = new ThuongHieu(id, maTH, tenTH, trangThai);
                ds.add(thuongHieu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    public ArrayList<ThuongHieu> findAllThuongHieuSX() {
        ArrayList<ThuongHieu> ds = new ArrayList<>();
        String sql = "select Id,MaTH,TenTH,TrangThai from ThuongHieu order by MaTH asc";

        try {
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("Id");
                String maTH = rs.getString("MaTH");
                String tenTH = rs.getString("TenTH");
                int trangThai = rs.getInt("TrangThai");

                ThuongHieu thuongHieu = new ThuongHieu(id, maTH, tenTH, trangThai);
                ds.add(thuongHieu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    public void creat(ThuongHieu th) {
        String sql = "INSERT dbo.ThuongHieu(MaTH,TenTH,TrangThai) VALUES(?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, th.getMaTH());
            ps.setString(2, th.getTenTH());
            ps.setInt(3, th.getTrangThai());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(ThuongHieu th) {
        String sql = "UPDATE ThuongHieu SET TenTH=?, TrangThai=? WHERE MaTH=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, th.getTenTH());
            ps.setInt(2, th.getTrangThai());
            ps.setString(3, th.getMaTH());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update_TenThuongHieu_VeNull(String maTH) {
        String sql = "UPDATE ThuongHieu SET TenTH=NULL WHERE MaTH=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, maTH);
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
