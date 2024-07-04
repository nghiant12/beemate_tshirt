package model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author maymimim
 */
public class Mau {

    private int id;
    private String maMau;
    private String tenMau;
    private int trangThai;

    public Mau() {
    }

    public Mau(int id) {
        this.id = id;
    }

    public Mau(int id, String maMau, String tenMau, int trangThai) {
        this.id = id;
        this.maMau = maMau;
        this.tenMau = tenMau;
        this.trangThai = trangThai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaMau() {
        return maMau;
    }

    public void setMaMau(String maMau) {
        this.maMau = maMau;
    }

    public String getTenMau() {
        return tenMau;
    }

    public void setTenMau(String tenMau) {
        this.tenMau = tenMau;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "Mau{" + "id=" + id + ", maMau=" + maMau + ", tenMau=" + tenMau + ", trangThai=" + trangThai + '}';
    }

}
