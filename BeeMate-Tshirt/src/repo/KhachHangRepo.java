/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repo;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import model.KhachHang;
import utils.JdbcUtil;

/**
 *
 * @author maymimim
 */
public class KhachHangRepo {

    private Connection conn;

    public KhachHangRepo() {
        this.conn = JdbcUtil.getConnection();
    }

    public ArrayList<KhachHang> getData() {
        String sql = "Select * from KhachHang";
        try {
            Connection con = utils.JdbcUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.execute();
            ArrayList<KhachHang> list = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                KhachHang kh = new KhachHang(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6));
                list.add(kh);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<KhachHang> getAllKhachHangTheoSDT(String SDT) {
        String sql = """
                     SELECT * FROM KhachHang
                     WHERE Sdt like '%""" + SDT + "%'";
        try {
            Connection con = utils.JdbcUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.execute();
            ArrayList<KhachHang> list = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                KhachHang kh = new KhachHang(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6));
                list.add(kh);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int findId_KHBySdt(String sdt) {
        int khId = -1;

        try (Connection conn = JdbcUtil.getConnection()) {
            // Check if customer exists
            String selectSQL = "SELECT id FROM KhachHang WHERE sdt = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
            preparedStatement.setString(1, sdt);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                khId = resultSet.getInt("id");
            } else {
                // Customer does not exist, create a new one
                String insertSQL = "INSERT INTO KhachHang (MaKH, HoTen, Sdt, TrangThai) VALUES (?, ?, ?,0)";
                PreparedStatement ps = conn.prepareStatement(insertSQL, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setString(1, generateCode());
                ps.setString(2, "Khách vãng lai");
                ps.setString(3, sdt);
                ps.executeUpdate();

                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    khId = generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return khId;
    }

    public String findSdtKHByTen(String hoTen) {
        String sdt = null;

        try (Connection conn = JdbcUtil.getConnection()) {
            // Check if customer exists
            String selectSQL = "SELECT Sdt FROM KhachHang WHERE HoTen = ?";
            PreparedStatement ps = conn.prepareStatement(selectSQL);
            ps.setString(1, hoTen);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                sdt = rs.getString("Sdt");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sdt;
    }

    public static String generateCode() {
        Random random = new SecureRandom();
        StringBuilder code = new StringBuilder(CODE_LENGTH);

        for (int i = 0; i < CODE_LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            code.append(CHARACTERS.charAt(randomIndex));
        }

        return "KH" + code.toString();
    }
    private static final String CHARACTERS = "0123456789";
    private static final int CODE_LENGTH = 5;

    public ArrayList<KhachHang> findAll() {
        ArrayList<KhachHang> ds = new ArrayList<>();
        String sql = """
                     select Id, MaKH, HoTen, Sdt, DiaChi, TrangThai
                     from KhachHang""";
        try {
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                KhachHang k = new KhachHang();
                k.setId(rs.getInt(1));
                k.setMaKH(rs.getString(2));
                k.setHoTen(rs.getString(3));
                k.setSdt(rs.getString(4));
                k.setDiaChi(rs.getString(5));
                k.setTrangThai(rs.getInt(6));
                ds.add(k);
            }
            return ds;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

//    public ArrayList<KhachHang> findAllActive() {
//        ArrayList<KhachHang> ds = new ArrayList<>();
//        String sql = """
//                     select Id, MaKH, HoTen, Sdt, DiaChi, TrangThai
//                     from KhachHang
//                     where TrangThai = 0""";
//        try {
//            PreparedStatement ps = this.conn.prepareStatement(sql);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                KhachHang k = new KhachHang();
//                k.setId(rs.getInt(1));
//                k.setMaKH(rs.getString(2));
//                k.setHoTen(rs.getString(3));
//                k.setSdt(rs.getString(4));
//                k.setDiaChi(rs.getString(5));
//                k.setTrangThai(rs.getInt(6));
//                ds.add(k);
//            }
//            return ds;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public ArrayList<KhachHang> findAllUnactive() {
//        ArrayList<KhachHang> ds = new ArrayList<>();
//        String sql = """
//                     select Id, MaKH, HoTen, Sdt, DiaChi, TrangThai
//                     from KhachHang
//                     where TrangThai = 1""";
//        try {
//            PreparedStatement ps = this.conn.prepareStatement(sql);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                KhachHang k = new KhachHang();
//                k.setId(rs.getInt(1));
//                k.setMaKH(rs.getString(2));
//                k.setHoTen(rs.getString(3));
//                k.setSdt(rs.getString(4));
//                k.setDiaChi(rs.getString(5));
//                k.setTrangThai(rs.getInt(6));
//                ds.add(k);
//            }
//            return ds;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
    public void create(KhachHang k) {
        String sql = "INSERT INTO KhachHang(MaKH, HoTen, Sdt, DiaChi, TrangThai) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setString(1, k.getMaKH());
            ps.setString(2, k.getHoTen());
            ps.setString(3, k.getSdt());
            ps.setString(4, k.getDiaChi());
            ps.setInt(5, k.getTrangThai());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(KhachHang k, int id_kh) {
        String sql = "UPDATE KhachHang SET MaKH = ?, HoTen = ?, Sdt = ?, DiaChi = ?, TrangThai = ? WHERE Id = ?";
        try {
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setString(1, k.getMaKH());
            ps.setString(2, k.getHoTen());
            ps.setString(3, k.getSdt());
            ps.setString(4, k.getDiaChi());
            ps.setInt(5, k.getTrangThai());
            ps.setInt(6, id_kh);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkTrungMa(String ma) {
        boolean check = false;
        List<KhachHang> list = this.findAll();
        for (KhachHang k : list) {
            if (ma == null ? k.getMaKH() == null : ma.equals(k.getMaKH())) {
                check = true;
                break;
            }
        }
        return check;
    }

    public ArrayList<KhachHang> searchAndPaging(String keyword, int trangThai, int page, int limit) {
        ArrayList<KhachHang> ds = new ArrayList<>();
        String sql = "SELECT * FROM KhachHang k WHERE TrangThai = ?";
        if (keyword.length() != 0) {
            sql += "AND (MaKH LIKE ? OR HoTen LIKE ?)";
        }
        sql += "ORDER BY k.Id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
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
                String ma = rs.getString("MaKH");
                String ten = rs.getString("HoTen");
                String s = rs.getString("Sdt");
                String dc = rs.getString("DiaChi");
                int stt = rs.getInt("TrangThai");
                KhachHang k = new KhachHang(id, ma, ten, s, dc, stt);
                ds.add(k);
            }
            return ds;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getTotalItems() {
        int totalItems = 0;
        String query = "SELECT COUNT(*) FROM KhachHang";
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
}
