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
public class SanPhamCTRepo {

    private Connection connection;

    public SanPhamCTRepo() {
        this.connection = (Connection) JdbcUtil.getConnection();
    }

    public ArrayList<SanPhamCT> findAllSPCT() {
        ArrayList<SanPhamCT> ds = new ArrayList<>();
        String sql = """
                     select spct.Id, spct.MaSPCT, s.TenSP, ms.TenMau, sz.TenSize, kd.TenKieuDang, th.TenTH, spct.SoLuong, spct.DonGia, spct.TrangThai
                         from SanPhamChiTiet spct
                             join SanPham s on s.Id = spct.Id_SP
                             join MauSac ms on ms.Id = spct.Id_Mau
                             join Size sz on sz.Id = spct.Id_Size
                             join KieuDang kd on kd.Id = spct.Id_KD
                             join ThuongHieu th on th.Id = spct.Id_TH""";

        try {
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SanPhamCT spct = new SanPhamCT();
                spct.setId(rs.getInt(1));
                spct.setMaSPCT(rs.getString(2));
                SanPham s = new SanPham();
                s.setTenSP(rs.getString(3));
                spct.setSp(s);
                Mau m = new Mau();
                m.setTenMau(rs.getString(4));
                spct.setM(m);
                Size size = new Size();
                size.setTenSize(rs.getString(5));
                spct.setS(size);
                KieuDang kd = new KieuDang();
                kd.setTenKD(rs.getString(6));
                spct.setKd(kd);
                ThuongHieu th = new ThuongHieu();
                th.setTenTH(rs.getString(7));
                spct.setTh(th);
                spct.setSoLuong(rs.getInt(8));
                spct.setDonGia(rs.getFloat(9));
                spct.setTrangThai(rs.getInt(10));
                ds.add(spct);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    public ArrayList<SanPhamCT> findAllProductDetailPaging(int page, int limit) {
        ArrayList<SanPhamCT> ds = new ArrayList<>();
        String sql = """
                     select spct.Id, spct.MaSPCT, s.TenSP, ms.TenMau, sz.TenSize, kd.TenKieuDang, th.TenTH, spct.SoLuong, spct.DonGia, spct.TrangThai
                     from SanPhamChiTiet spct
                          join SanPham s on s.Id = spct.Id_SP
                          join MauSac ms on ms.Id = spct.Id_Mau
                          join Size sz on sz.Id = spct.Id_Size
                          join KieuDang kd on kd.Id = spct.Id_KD
                          join ThuongHieu th on th.Id = spct.Id_TH
                     ORDER BY spct.Id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY""";
        try {
            PreparedStatement ps = this.connection.prepareStatement(sql);
            int offset = ((page - 1) * limit);
            ps.setInt(1, offset);
            ps.setInt(2, limit);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SanPhamCT spct = new SanPhamCT();
                spct.setId(rs.getInt(1));
                spct.setMaSPCT(rs.getString(2));
                SanPham s = new SanPham();
                s.setTenSP(rs.getString(3));
                spct.setSp(s);
                Mau m = new Mau();
                m.setTenMau(rs.getString(4));
                spct.setM(m);
                Size size = new Size();
                size.setTenSize(rs.getString(5));
                spct.setS(size);
                KieuDang kd = new KieuDang();
                kd.setTenKD(rs.getString(6));
                spct.setKd(kd);
                ThuongHieu th = new ThuongHieu();
                th.setTenTH(rs.getString(7));
                spct.setTh(th);
                spct.setSoLuong(rs.getInt(8));
                spct.setDonGia(rs.getFloat(9));
                spct.setTrangThai(rs.getInt(10));
                ds.add(spct);
            }
            return ds;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<SanPhamCT> findSPCT_ByIdSp(int id_sp) {
        ArrayList<SanPhamCT> ds = new ArrayList<>();
        String sql = """
                     select spct.MaSPCT, ms.TenMau, sz.TenSize, kd.TenKieuDang, th.TenTH, spct.SoLuong, spct.DonGia, spct.TrangThai
                     from SanPhamChiTiet spct
                        join SanPham s on s.Id = spct.Id_SP
                        join MauSac ms on ms.Id = spct.Id_Mau
                        join Size sz on sz.Id = spct.Id_Size
                        join KieuDang kd on kd.Id = spct.Id_KD
                        join ThuongHieu th on th.Id = spct.Id_TH
                     where s.Id = ?
                     """;

        try {
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setInt(1, id_sp);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                SanPhamCT spct = new SanPhamCT();
                spct.setMaSPCT(rs.getString(1));
                Mau m = new Mau();
                m.setTenMau(rs.getString(2));
                spct.setM(m);
                Size size = new Size();
                size.setTenSize(rs.getString(3));
                spct.setS(size);
                KieuDang kd = new KieuDang();
                kd.setTenKD(rs.getString(4));
                spct.setKd(kd);
                ThuongHieu th = new ThuongHieu();
                th.setTenTH(rs.getString(5));
                spct.setTh(th);
                spct.setSoLuong(rs.getInt(6));
                spct.setDonGia(rs.getFloat(7));
                spct.setTrangThai(rs.getInt(8));
                ds.add(spct);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    public void create(SanPhamCT spct) {
        String sql = "INSERT INTO SanPhamChiTiet(MaSPCT, Id_SP, Id_Mau, Id_Size, Id_KD, Id_TH, SoLuong, DonGia, TrangThai) VALUES (?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setObject(1, spct.getMaSPCT());
            ps.setInt(2, spct.getSp().getId());
            ps.setInt(3, spct.getM().getId());
            ps.setInt(4, spct.getS().getId());
            ps.setInt(5, spct.getKd().getId());
            ps.setInt(6, spct.getTh().getId());
            ps.setObject(7, spct.getSoLuong());
            ps.setObject(8, spct.getDonGia());
            ps.setObject(9, spct.getTrangThai());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Phương thức để lấy ID_SPCT từ cơ sở dữ liệu
    public int getSPCTID(int rowIndex) {
        int spctID = -1; // Giá trị mặc định nếu không tìm thấy ID_SPCT
        String sql = "SELECT Id FROM SanPhamChiTiet ORDER BY Id OFFSET ? ROWS FETCH NEXT 1 ROW ONLY";
        try {
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setInt(1, rowIndex);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                spctID = resultSet.getInt("Id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return spctID;
    }

    public void update(SanPhamCT spct, String ma) {
        String sql = "UPDATE SanPhamChiTiet SET Id_SP = ?, Id_Mau = ?, Id_Size = ?, Id_KD = ?, Id_TH = ?, SoLuong = ?, DonGia = ?, TrangThai = ? WHERE MaSPCT = ?";
        try {
            PreparedStatement ps = this.connection.prepareStatement(sql);
//            ps.setObject(1, spct.getMaSPCT());
            ps.setInt(1, spct.getSp().getId());
            ps.setInt(2, spct.getM().getId());
            ps.setInt(3, spct.getS().getId());
            ps.setInt(4, spct.getKd().getId());
            ps.setInt(5, spct.getTh().getId());
            ps.setObject(6, spct.getSoLuong());
            ps.setObject(7, spct.getDonGia());
            ps.setObject(8, spct.getTrangThai());
            ps.setString(9, ma);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkTrungMa(String ma) {
        boolean check = false;
        List<SanPhamCT> list = this.findAllSPCT();
        for (SanPhamCT s : list) {
            if (ma == null ? s.getMaSPCT() == null : ma.equals(s.getMaSPCT())) {
                check = true;
                break;
            }
        }
        return check;
    }

    public ArrayList<SanPhamCT> paging(int page, int limit, int id_sp) {
        ArrayList<SanPhamCT> ds = new ArrayList<>();
        String sql = """
                     select spct.MaSPCT, ms.TenMau, sz.TenSize, kd.TenKieuDang, th.TenTH, spct.SoLuong, spct.DonGia, spct.TrangThai
                     from SanPhamChiTiet spct
                          join SanPham s on s.Id = spct.Id_SP
                          join MauSac ms on ms.Id = spct.Id_Mau
                          join Size sz on sz.Id = spct.Id_Size
                          join KieuDang kd on kd.Id = spct.Id_KD
                          join ThuongHieu th on th.Id = spct.Id_TH
                     where s.Id = ?
                     ORDER BY spct.Id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY""";
        try {
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setInt(1, id_sp);
            int offset = ((page - 1) * limit);
            ps.setInt(2, offset);
            ps.setInt(3, limit);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SanPhamCT spct = new SanPhamCT();
                spct.setMaSPCT(rs.getString(1));
                Mau m = new Mau();
                m.setTenMau(rs.getString(2));
                spct.setM(m);
                Size size = new Size();
                size.setTenSize(rs.getString(3));
                spct.setS(size);
                KieuDang kd = new KieuDang();
                kd.setTenKD(rs.getString(4));
                spct.setKd(kd);
                ThuongHieu th = new ThuongHieu();
                th.setTenTH(rs.getString(5));
                spct.setTh(th);
                spct.setSoLuong(rs.getInt(6));
                spct.setDonGia(rs.getFloat(7));
                spct.setTrangThai(rs.getInt(8));
                ds.add(spct);
            }
            return ds;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getTotalItems() {
        int totalItems = 0;
        String query = "SELECT COUNT(*) FROM SanPhamChiTiet";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                totalItems = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalItems;
    }

    public boolean checkTrungSPCT(int id_mau, int id_s, int id_k, int id_th) throws SQLException {
        String query = """
                       SELECT COUNT(*)
                       FROM SanPhamChiTiet
                       WHERE Id_Mau = ?
                           AND Id_Size = ?
                           AND Id_KD = ?
                           AND Id_TH = ?""";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id_mau);
            stmt.setInt(2, id_s);
            stmt.setInt(3, id_k);
            stmt.setInt(4, id_th);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Trả về true nếu tồn tại sản phẩm với các thuộc tính tương tự
                }
            }
        }
        return false; // Trả về false nếu không tồn tại sản phẩm với các thuộc tính tương tự
    }

}
