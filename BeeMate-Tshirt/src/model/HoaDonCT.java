package model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.util.UUID;

/**
 *
 * @author maymimim
 */
public class HoaDonCT {

    private UUID id;
    private HoaDon hd;
    private SanPhamCT spct;
    private int soLuong;
    private float giaHienHanh;
    private int idHDCT_Old;
    private int trangThai;

    public HoaDonCT() {
    }

    public HoaDonCT(UUID id, HoaDon hd, SanPhamCT spct, int soLuong, float giaHienHanh, int idHDCT_Old, int trangThai) {
        this.id = id;
        this.hd = hd;
        this.spct = spct;
        this.soLuong = soLuong;
        this.giaHienHanh = giaHienHanh;
        this.idHDCT_Old = idHDCT_Old;
        this.trangThai = trangThai;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public HoaDon getHd() {
        return hd;
    }

    public void setHd(HoaDon hd) {
        this.hd = hd;
    }

    public SanPhamCT getSpct() {
        return spct;
    }

    public void setSpct(SanPhamCT spct) {
        this.spct = spct;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public float getGiaHienHanh() {
        return giaHienHanh;
    }

    public void setGiaHienHanh(float giaHienHanh) {
        this.giaHienHanh = giaHienHanh;
    }

    public int getIdHDCT_Old() {
        return idHDCT_Old;
    }

    public void setIdHDCT_Old(int idHDCT_Old) {
        this.idHDCT_Old = idHDCT_Old;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "HoaDonCT{" + "id=" + id + ", hd=" + hd + ", spct=" + spct + ", soLuong=" + soLuong + ", giaHienHanh=" + giaHienHanh + ", idHDCT_Old=" + idHDCT_Old + ", trangThai=" + trangThai + '}';
    }

}
