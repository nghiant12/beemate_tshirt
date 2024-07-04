package model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.sql.Date;

/**
 *
 * @author maymimim
 */
public class HoaDon {

    private int id;
    private String maHD;
    private NhanVien nv;
    private KhachHang kh;
    private KhuyenMai km;
    private Date ngayTao;
    private float tongTien;
    private float soTienGiamGia;
    private String hinhThucThanhToan;
    private String ghiChu;
    private float thanhTien;
    private int trangThai;

    public HoaDon() {
    }

    public HoaDon(int id, String maHD, NhanVien nv, KhachHang kh, KhuyenMai km, Date ngayTao, float tongTien, float soTienGiamGia, String hinhThucThanhToan, String ghiChu, float thanhTien, int trangThai) {
        this.id = id;
        this.maHD = maHD;
        this.nv = nv;
        this.kh = kh;
        this.km = km;
        this.ngayTao = ngayTao;
        this.tongTien = tongTien;
        this.soTienGiamGia = soTienGiamGia;
        this.hinhThucThanhToan = hinhThucThanhToan;
        this.ghiChu = ghiChu;
        this.thanhTien = thanhTien;
        this.trangThai = trangThai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public NhanVien getNv() {
        return nv;
    }

    public void setNv(NhanVien nv) {
        this.nv = nv;
    }

    public KhachHang getKh() {
        return kh;
    }

    public void setKh(KhachHang kh) {
        this.kh = kh;
    }

    public KhuyenMai getKm() {
        return km;
    }

    public void setKm(KhuyenMai km) {
        this.km = km;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public float getTongTien() {
        return tongTien;
    }

    public void setTongTien(float tongTien) {
        this.tongTien = tongTien;
    }

    public float getSoTienGiamGia() {
        return soTienGiamGia;
    }

    public void setSoTienGiamGia(float soTienGiamGia) {
        this.soTienGiamGia = soTienGiamGia;
    }

    public String getHinhThucThanhToan() {
        return hinhThucThanhToan;
    }

    public void setHinhThucThanhToan(String hinhThucThanhToan) {
        this.hinhThucThanhToan = hinhThucThanhToan;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public float getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(float thanhTien) {
        this.thanhTien = thanhTien;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "HoaDon{" + "id=" + id + ", maHD=" + maHD + ", nv=" + nv + ", kh=" + kh + ", km=" + km + ", ngayTao=" + ngayTao + ", tongTien=" + tongTien + ", soTienGiamGia=" + soTienGiamGia + ", hinhThucThanhToan=" + hinhThucThanhToan + ", ghiChu=" + ghiChu + ", thanhTien=" + thanhTien + ", trangThai=" + trangThai + '}';
    }

}
