package model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author maymimim
 */
public class ThuongHieu {

    private int id;
    private String maTH;
    private String tenTH;
    private int trangThai;

    public ThuongHieu() {
    }

    public ThuongHieu(int id) {
        this.id = id;
    }

    public ThuongHieu(int id, String maTH, String tenTH, int trangThai) {
        this.id = id;
        this.maTH = maTH;
        this.tenTH = tenTH;
        this.trangThai = trangThai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaTH() {
        return maTH;
    }

    public void setMaTH(String maTH) {
        this.maTH = maTH;
    }

    public String getTenTH() {
        return tenTH;
    }

    public void setTenTH(String tenTH) {
        this.tenTH = tenTH;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "ThuongHieu{" + "id=" + id + ", maTH=" + maTH + ", tenTH=" + tenTH + ", trangThai=" + trangThai + '}';
    }

}
