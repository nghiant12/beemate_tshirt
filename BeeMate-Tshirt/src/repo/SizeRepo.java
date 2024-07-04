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
import model.Size;
import utils.JdbcUtil;

/**
 *
 * @author USER
 */
public class SizeRepo {

    private Connection connection;

    public SizeRepo() {
        this.connection = (Connection) JdbcUtil.getConnection();
    }

    public ArrayList<Size> findAllSize() {
        ArrayList<Size> ds = new ArrayList<>();
        String sql = "SELECT Id, MaSize, TenSize, TrangThai FROM Size where TrangThai = 0";

        try {
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("Id");
                String maSize = rs.getString("MaSize");
                String tenSize = rs.getString("TenSize");
                int trangThai = rs.getInt("TrangThai");

                Size size = new Size(id, maSize, tenSize, trangThai);
                ds.add(size);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    public ArrayList<Size> findAllSizeSX() {
        ArrayList<Size> ds = new ArrayList<>();
        String sql = "select Id,MaSize,TenSize,TrangThai from Size order by MaSize asc";

        try {
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("Id");
                String maSize = rs.getString("MaSize");
                String tenSize = rs.getString("TenSize");
                int trangThai = rs.getInt("TrangThai");
                Size s = new Size(id, maSize, tenSize, trangThai);
                ds.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    public void creat(Size s) {
        String sql = "INSERT dbo.Size(MaSize,TenSize,TrangThai) VALUES(?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, s.getMaSize());
            ps.setString(2, s.getTenSize());
            ps.setInt(3, s.getTrangThai());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Size s) {
        String sql = "UPDATE Size SET TenSize=?, TrangThai=? WHERE MaSize=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, s.getTenSize());
            ps.setInt(2, s.getTrangThai());
            ps.setString(3, s.getMaSize());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update_TenSize_VeNull(String maSize) {
        String sql = "UPDATE Size SET TenSize=NULL WHERE MaSize=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, maSize);
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
