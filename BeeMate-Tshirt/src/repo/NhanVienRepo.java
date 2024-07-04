/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repo;

import java.sql.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.NhanVien;
import utils.JdbcHelper;
import utils.JdbcUtil;

/**
 *
 * @author maymimim
 */
public class NhanVienRepo {

    private Connection connection;

    public NhanVienRepo() {
        this.connection = (Connection) JdbcUtil.getConnection();
    }

    protected List<NhanVien> selectBySql(String sql, Object... args) {
        List<NhanVien> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setId(rs.getInt("Id"));
                nv.setMaNV(rs.getString("MaNV"));
                nv.setHoTen(rs.getString("HoTen"));
                nv.setDiaChi(rs.getString("DiaChi"));
                nv.setSdt(rs.getString("Sdt"));
                nv.setEmail(rs.getString("Email"));
                nv.setGioiTinh(rs.getInt("GioiTinh"));
                nv.setTenTaiKhoan(rs.getString("TenTaiKhoan"));
                nv.setMatKhau(rs.getString("MatKhau"));
                nv.setVaiTro(rs.getInt("VaiTro"));
                nv.setTrangThai(rs.getInt("TrangThai"));
                list.add(nv);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<NhanVien> getAll() {
        String sql = "select * from NhanVien";
        return this.selectBySql(sql);
    }

    public NhanVien selectByMa(String ma) {
        String selectByMa = """
                        select * from NhanVien
                        WHERE TenTaiKhoan = ?
                        """;
        List<NhanVien> list = this.selectBySql(selectByMa, ma);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    public ArrayList<NhanVien> findAllNhanVien() {
        ArrayList<NhanVien> ds = new ArrayList<>();
        String sql = "select Id, MaNV, HoTen, DiaChi, Sdt, Email, GioiTinh,TenTaiKhoan, MatKhau, VaiTro, TrangThai from NhanVien order by MaNV asc";

        try {
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("Id");
                String maNV = rs.getString("MaNV");
                String hoTen = rs.getString("HoTen");
                String diaChi = rs.getString("DiaChi");
                String sdt = rs.getString("Sdt");
                String email = rs.getString("Email");
                int gioiTinh = rs.getInt("GioiTinh");
                String taiKhoan = rs.getString("TenTaiKhoan");
                String matKhau = rs.getString("MatKhau");
                int vaiTro = rs.getInt("VaiTro");
                int trangThai = rs.getInt("TrangThai");

                NhanVien nv = new NhanVien(id, maNV, hoTen, diaChi, sdt, email, gioiTinh, taiKhoan, matKhau, vaiTro, trangThai);
                ds.add(nv);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    public void creat(NhanVien nv) {
        String sql = "INSERT dbo.NhanVien(MaNV,HoTen,DiaChi,Sdt,Email,GioiTinh,TenTaiKhoan,MatKhau,VaiTro,TrangThai) VALUES(?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, nv.getMaNV());
            ps.setString(2, nv.getHoTen());
            ps.setString(3, nv.getDiaChi());
            ps.setString(4, nv.getSdt());
            ps.setString(5, nv.getEmail());
            ps.setInt(6, nv.getGioiTinh());
            ps.setString(7, nv.getTenTaiKhoan());
            ps.setString(8, nv.getMatKhau());
            ps.setInt(9, nv.getVaiTro());
            ps.setInt(10, nv.getTrangThai());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(NhanVien nv) {
        String sql = "UPDATE dbo.NhanVien SET HoTen=?, DiaChi=?, Sdt=?, Email=?, GioiTinh=?, TenTaiKhoan=?, MatKhau=?, VaiTro=?, TrangThai=? WHERE MaNV=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, nv.getHoTen());
            ps.setString(2, nv.getDiaChi());
            ps.setString(3, nv.getSdt());
            ps.setString(4, nv.getEmail());
            ps.setInt(5, nv.getGioiTinh());
            ps.setString(6, nv.getTenTaiKhoan());
            ps.setString(7, nv.getMatKhau());
            ps.setInt(8, nv.getVaiTro());
            ps.setInt(9, nv.getTrangThai());
            ps.setString(10, nv.getMaNV());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateTaiKhoan_Email_Sdt_VeNull(String maNV) {
        String sql = "UPDATE dbo.NhanVien SET TenTaiKhoan=NULL, Email=Null, Sdt=Null  WHERE MaNV=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, maNV);
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void choNhanVienNghiLam(String maNV) {
        String sql = "update NhanVien set TrangThai=1 where MaNV=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, maNV);
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void choNhanVienDiLam(String maNV) {
        String sql = "update NhanVien set TrangThai=0 where MaNV=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, maNV);
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
