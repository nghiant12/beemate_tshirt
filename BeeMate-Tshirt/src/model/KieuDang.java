package model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author maymimim
 */
public class KieuDang {

    private int id;
    private String maKD;
    private String tenKD;
    private String moTa;
    private int trangThai;

    public KieuDang() {
    }

    public KieuDang(int id) {
        this.id = id;
    }

    public KieuDang(int id, String maKD, String tenKD, String moTa, int trangThai) {
        this.id = id;
        this.maKD = maKD;
        this.tenKD = tenKD;
        this.moTa = moTa;
        this.trangThai = trangThai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaKD() {
        return maKD;
    }

    public void setMaKD(String maKD) {
        this.maKD = maKD;
    }

    public String getTenKD() {
        return tenKD;
    }

    public void setTenKD(String tenKD) {
        this.tenKD = tenKD;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "KieuDang{" + "id=" + id + ", maKD=" + maKD + ", tenKD=" + tenKD + ", moTa=" + moTa + ", trangThai=" + trangThai + '}';
    }

}
