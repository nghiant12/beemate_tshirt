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
import model.Mau;
import utils.JdbcUtil;

/**
 *
 * @author USER
 */
public class MauSacRepo {

    private Connection connection;

    public MauSacRepo() {
        this.connection = (Connection) JdbcUtil.getConnection();
    }

    public ArrayList<Mau> findAllMauSac() {
        ArrayList<Mau> ds = new ArrayList<>();
        String sql = "SELECT Id, MaMau, TenMau, TrangThai FROM MauSac where TrangThai = 0";

        try {
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("Id");
                String maMau = rs.getString("MaMau");
                String tenMau = rs.getString("TenMau");
                int trangThai = rs.getInt("TrangThai");

                Mau mau = new Mau(id, maMau, tenMau, trangThai);
                ds.add(mau);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    public ArrayList<Mau> findAllMauSacSX() {
        ArrayList<Mau> ds = new ArrayList<>();
        String sql = "select Id,MaMau,TenMau,TrangThai from MauSac order by MaMau asc";

        try {
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("Id");
                String maMau = rs.getString("MaMau");
                String tenMau = rs.getString("TenMau");
                int trangThai = rs.getInt("TrangThai");
                Mau m = new Mau(id, maMau, tenMau, trangThai);
                ds.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    public void creat(Mau m) {
        String sql = "INSERT dbo.ThuongHieu(MaMau,TenMau,TrangThai) VALUES(?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, m.getMaMau());
            ps.setString(2, m.getTenMau());
            ps.setInt(3, m.getTrangThai());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Mau m) {
        String sql = "UPDATE MauSac SET TenMau=?, TrangThai=? WHERE MaMau=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, m.getTenMau());
            ps.setInt(2, m.getTrangThai());
            ps.setString(3, m.getMaMau());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update_TenMauSac_VeNull(String maMau) {
        String sql = "UPDATE MauSac SET TenMau=NULL WHERE MaMau=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, maMau);
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
