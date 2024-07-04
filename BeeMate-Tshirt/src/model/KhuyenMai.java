package model;

import java.util.Date;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author maymimim
 */
public class KhuyenMai {

    private Integer id;
    private String maKM;
    private String tenChienDich;
    private int soHoaDonAD;
    private int mucGiamGia;
    private Date ngayBD;
    private Date ngayKT;
    private Date ngayTao;
    private float hoaDonToiThieu;
    private int soLuong;
    private int trangThai;

    public KhuyenMai() {
    }

    public KhuyenMai(Integer id, String maKM, String tenChienDich, int soHoaDonAD, int mucGiamGia, java.util.Date ngayBD, java.util.Date ngayKT, java.util.Date ngayTao, float hoaDonToiThieu, int soLuong, int trangThai) {
        this.id = id;
        this.maKM = maKM;
        this.tenChienDich = tenChienDich;
        this.soHoaDonAD = soHoaDonAD;
        this.mucGiamGia = mucGiamGia;
        this.ngayBD = ngayBD;
        this.ngayKT = ngayKT;
        this.ngayTao = ngayTao;
        this.hoaDonToiThieu = hoaDonToiThieu;
        this.soLuong = soLuong;
        this.trangThai = trangThai;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMaKM() {
        return maKM;
    }

    public void setMaKM(String maKM) {
        this.maKM = maKM;
    }

    public String getTenChienDich() {
        return tenChienDich;
    }

    public void setTenChienDich(String tenChienDich) {
        this.tenChienDich = tenChienDich;
    }

    public int getSoHoaDonAD() {
        return soHoaDonAD;
    }

    public void setSoHoaDonAD(int soHoaDonAD) {
        this.soHoaDonAD = soHoaDonAD;
    }

    public int getMucGiamGia() {
        return mucGiamGia;
    }

    public void setMucGiamGia(int mucGiamGia) {
        this.mucGiamGia = mucGiamGia;
    }

    public java.util.Date getNgayBD() {
        return ngayBD;
    }

    public void setNgayBD(java.util.Date ngayBD) {
        this.ngayBD = ngayBD;
    }

    public java.util.Date getNgayKT() {
        return ngayKT;
    }

    public void setNgayKT(java.util.Date ngayKT) {
        this.ngayKT = ngayKT;
    }

    public java.util.Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(java.util.Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public float getHoaDonToiThieu() {
        return hoaDonToiThieu;
    }

    public void setHoaDonToiThieu(float hoaDonToiThieu) {
        this.hoaDonToiThieu = hoaDonToiThieu;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "KhuyenMai{" + "id=" + id + ", maKM=" + maKM + ", tenChienDich=" + tenChienDich + ", soHoaDonAD=" + soHoaDonAD + ", mucGiamGia=" + mucGiamGia + ", ngayBD=" + ngayBD + ", ngayKT=" + ngayKT + ", ngayTao=" + ngayTao + ", hoaDonToiThieu=" + hoaDonToiThieu + ", soLuong=" + soLuong + ", trangThai=" + trangThai + '}';
    }

}
