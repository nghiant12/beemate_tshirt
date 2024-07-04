package model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author maymimim
 */
public class SanPhamCT {

    private int id;
    private String maSPCT;
    private SanPham sp;
    private Mau m;
    private Size s;
    private KieuDang kd;
    private ThuongHieu th;
    private int soLuong;
    private float donGia;
    private int trangThai;

    public SanPhamCT() {
    }

    public SanPhamCT(int id, String maSP, SanPham sp, Mau m, Size s, KieuDang kd, ThuongHieu th, int soLuong, float donGia, int trangThai) {
        this.id = id;
        this.maSPCT = maSP;
        this.sp = sp;
        this.m = m;
        this.s = s;
        this.kd = kd;
        this.th = th;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.trangThai = trangThai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaSPCT() {
        return maSPCT;
    }

    public void setMaSPCT(String maSPCT) {
        this.maSPCT = maSPCT;
    }

    public SanPham getSp() {
        return sp;
    }

    public void setSp(SanPham sp) {
        this.sp = sp;
    }

    public Mau getM() {
        return m;
    }

    public void setM(Mau m) {
        this.m = m;
    }

    public Size getS() {
        return s;
    }

    public void setS(Size s) {
        this.s = s;
    }

    public KieuDang getKd() {
        return kd;
    }

    public void setKd(KieuDang kd) {
        this.kd = kd;
    }

    public ThuongHieu getTh() {
        return th;
    }

    public void setTh(ThuongHieu th) {
        this.th = th;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public float getDonGia() {
        return donGia;
    }

    public void setDonGia(float donGia) {
        this.donGia = donGia;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "SanPhamCT{" + "id=" + id + ", maSP=" + maSPCT + ", sp=" + sp + ", m=" + m + ", s=" + s + ", kd=" + kd + ", th=" + th + ", soLuong=" + soLuong + ", donGia=" + donGia + ", trangThai=" + trangThai + '}';
    }

}
