/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import model.HoaDon;
import model.KhuyenMai;
import utils.JdbcUtil;

/**
 *
 * @author 84931
 */
public class KhuyenMaiRepo {

    private Connection conn;

    // Constructor to initialize database connection
    public KhuyenMaiRepo() {
        this.conn = JdbcUtil.getConnection();
    }

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public ArrayList<KhuyenMai> getAllKhuyenMai() {
        String sql = "Select * from KhuyenMai";
        try {
            Connection con = utils.JdbcUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            ArrayList<KhuyenMai> list = new ArrayList<>();
            while (rs.next()) {
                KhuyenMai km = new KhuyenMai(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getInt(5),
                        rs.getDate(6),
                        rs.getDate(7),
                        rs.getDate(8),
                        rs.getFloat(9),
                        rs.getInt(10),
                        rs.getInt(11)
                );
                list.add(km);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<KhuyenMai> getAllKhuyenMaiByMa(String MaKhuyenMai) {
        String sql = "SELECT * FROM KhuyenMai WHERE MaKM like '%" + MaKhuyenMai + "%'";
        try {
            Connection con = utils.JdbcUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            ArrayList<KhuyenMai> list = new ArrayList<>();
            while (rs.next()) {
                KhuyenMai km = new KhuyenMai(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getInt(5),
                        rs.getDate(6),
                        rs.getDate(7),
                        rs.getDate(8),
                        rs.getFloat(9),
                        rs.getInt(10),
                        rs.getInt(11)
                );
                list.add(km);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getId_KmByMaKhuyenMai(String maKM) {
        String sql = "SELECT Id FROM KhuyenMai WHERE MaKM like ?";
        try {
            Connection con = utils.JdbcUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maKM);
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

    public int InsertKhuyenMai(KhuyenMai km) {
        Connection conn = null;
        PreparedStatement sttm = null;
        try {
            String sSQL = "INSERT INTO KhuyenMai (MaKM, TenChienDich, SoHoaDonDuocApDung, [MucGiamGia(%)], NgayBatDau, NgayKetThuc,NgayTao, HoaDonToiThieu, SoLuong, TrangThai) VALUES (?, ?, 0, ?, ?, ?,GETDATE(), ?, ?,1)";
            conn = utils.JdbcUtil.getConnection();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, km.getMaKM());
            sttm.setString(2, km.getTenChienDich());
            sttm.setInt(3, km.getMucGiamGia());
            sttm.setString(4, dateFormat.format(km.getNgayBD()));
            sttm.setString(5, dateFormat.format(km.getNgayKT()));
            sttm.setFloat(6, km.getHoaDonToiThieu());
            sttm.setInt(7, km.getSoLuong());
            return sttm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (sttm != null) {
                    sttm.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return 0;
    }

    public int UpdateKhuyenMai(KhuyenMai km, int id) {
        HoaDon hd = null;
        Connection conn = null;
        PreparedStatement sttm = null;
        try {
            String sSQL = """
                          UPDATE KhuyenMai SET MaKM = ?,TenChienDich = ?,
                          [MucGiamGia(%)] = ?,NgayBatDau = ?,NgayKetThuc = ?,
                          HoaDonToiThieu = ?,SoLuong = ?,TrangThai = ?
                          WHERE Id = ?""";
            conn = utils.JdbcUtil.getConnection();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, km.getMaKM());
            sttm.setString(2, km.getTenChienDich());
            sttm.setInt(3, km.getMucGiamGia());
            sttm.setString(4, dateFormat.format(km.getNgayBD()));
            sttm.setString(5, dateFormat.format(km.getNgayKT()));
            sttm.setFloat(6, km.getHoaDonToiThieu());
            sttm.setInt(7, km.getSoLuong());
            sttm.setInt(8, km.getTrangThai());
            sttm.setInt(9, id);
            return sttm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            sttm.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int UpdateTrangThaiKM(KhuyenMai km) {
        HoaDon hd = null;
        Connection conn = null;
        PreparedStatement sttm = null;
        try {
            String sSQL = "EXEC Update_TrangThaiKM ?,?,?";
            conn = utils.JdbcUtil.getConnection();
            sttm = conn.prepareCall(sSQL);
            sttm.setString(1, km.getMaKM());
            sttm.setString(2, dateFormat.format(km.getNgayBD()));
            sttm.setString(3, dateFormat.format(km.getNgayKT()));
            return sttm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            sttm.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int getIdKM(int rowIndex) {
        int idKM = -1;
        String sql = "SELECT id FROM KhuyenMai ORDER BY id OFFSET ? ROWS FETCH NEXT 1 ROW ONLY";
        try {
            Connection con = utils.JdbcUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, rowIndex);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                idKM = rs.getInt("Id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idKM;
    }

    public int UpdateTrangThaiKMTrucTiep(int id_km) {
        Connection conn = null;
        PreparedStatement sttm = null;
        try {
            String sSQL = """
                      UPDATE KhuyenMai SET TrangThai = 0
                      WHERE Id = ?""";
            conn = utils.JdbcUtil.getConnection();
            sttm = conn.prepareStatement(sSQL);
            sttm.setInt(1, id_km);
            return sttm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } finally {
            if (sttm != null) {
                try {
                    sttm.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public ArrayList<KhuyenMai> paging(int page, int limit) {
        ArrayList<KhuyenMai> ds = new ArrayList<>();
        String sql = """
                     Select * from KhuyenMai k
                     ORDER BY k.Id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY""";
        try {
            Connection con = utils.JdbcUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            int offset = ((page - 1) * limit);
            ps.setInt(1, offset);
            ps.setInt(2, limit);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                KhuyenMai km = new KhuyenMai(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getInt(5),
                        rs.getDate(6),
                        rs.getDate(7),
                        rs.getDate(8),
                        rs.getFloat(9),
                        rs.getInt(10),
                        rs.getInt(11)
                );
                ds.add(km);
            }
            return ds;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getTotalItems() {
        int totalItems = 0;
        String query = "SELECT COUNT(*) FROM KhuyenMai";
        try {
            Connection con = utils.JdbcUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                totalItems = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalItems;
    }

    public boolean checkTrungMa(String ma) {
        boolean check = false;
        List<KhuyenMai> list = this.getAllKhuyenMai();
        for (KhuyenMai k : list) {
            if (ma == null ? k.getMaKM() == null : ma.equals(k.getMaKM())) {
                check = true;
                break;
            }
        }
        return check;
    }

    // Lấy ID của mã khuyến mãi từ cơ sở dữ liệu
    public Integer getId_KmByMaKM(String maKhuyenMai) {
        String query = "SELECT Id FROM KhuyenMai where MaKM = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, maKhuyenMai);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("Id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Kiểm tra mã khuyến mãi có hợp lệ và còn số lượng hay không
    public boolean checkMaKhuyenMai(String maKhuyenMai) {
        String query = "SELECT SoLuong FROM KhuyenMai WHERE MaKM = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, maKhuyenMai);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int soLuong = rs.getInt("SoLuong");
                return soLuong > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật số lượng mã khuyến mãi và số hóa đơn đã áp dụng
    public void updateMaKhuyenMai(String maKhuyenMai) {
        String query = "UPDATE KhuyenMai SET SoLuong = SoLuong - 1, SoHoaDonDuocApDung = SoHoaDonDuocApDung + 1 WHERE MaKM = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, maKhuyenMai);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
