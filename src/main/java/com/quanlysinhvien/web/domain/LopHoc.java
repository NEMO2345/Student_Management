package com.quanlysinhvien.web.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A LopHoc.
 */
@Entity
@Table(name = "lop_hoc")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LopHoc implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 2, max = 255)
    @Column(name = "ki_hoc", length = 255, nullable = false)
    private String kiHoc;

    @NotNull
    @Size(min = 2, max = 255)
    @Column(name = "ngay_hoc_trong_tuan", length = 255, nullable = false)
    private String ngayHocTrongTuan;

    @NotNull
    @Size(min = 2, max = 255)
    @Column(name = "thoi_gian_bat_dau", length = 255, nullable = false)
    private String thoiGianBatDau;

    @NotNull
    @Size(min = 2, max = 255)
    @Column(name = "thoi_gian_ket_thuc", length = 255, nullable = false)
    private String thoiGianKetThuc;

    @NotNull
    @Size(min = 2, max = 255)
    @Column(name = "phong_hoc", length = 255, nullable = false)
    private String phongHoc;

    @NotNull
    @Size(min = 2, max = 255)
    @Column(name = "ngay_thi", length = 255, nullable = false)
    private String ngayThi;

    @NotNull
    @Size(min = 2, max = 255)
    @Column(name = "gio_thi", length = 255, nullable = false)
    private String gioThi;

    @NotNull
    @Size(min = 2, max = 255)
    @Column(name = "phong_thi", length = 255, nullable = false)
    private String phongThi;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "nganhHoc" }, allowSetters = true)
    private HocPhan hocPhan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "user" }, allowSetters = true)
    private GiaoVien giaoVien;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LopHoc id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKiHoc() {
        return this.kiHoc;
    }

    public LopHoc kiHoc(String kiHoc) {
        this.setKiHoc(kiHoc);
        return this;
    }

    public void setKiHoc(String kiHoc) {
        this.kiHoc = kiHoc;
    }

    public String getNgayHocTrongTuan() {
        return this.ngayHocTrongTuan;
    }

    public LopHoc ngayHocTrongTuan(String ngayHocTrongTuan) {
        this.setNgayHocTrongTuan(ngayHocTrongTuan);
        return this;
    }

    public void setNgayHocTrongTuan(String ngayHocTrongTuan) {
        this.ngayHocTrongTuan = ngayHocTrongTuan;
    }

    public String getThoiGianBatDau() {
        return this.thoiGianBatDau;
    }

    public LopHoc thoiGianBatDau(String thoiGianBatDau) {
        this.setThoiGianBatDau(thoiGianBatDau);
        return this;
    }

    public void setThoiGianBatDau(String thoiGianBatDau) {
        this.thoiGianBatDau = thoiGianBatDau;
    }

    public String getThoiGianKetThuc() {
        return this.thoiGianKetThuc;
    }

    public LopHoc thoiGianKetThuc(String thoiGianKetThuc) {
        this.setThoiGianKetThuc(thoiGianKetThuc);
        return this;
    }

    public void setThoiGianKetThuc(String thoiGianKetThuc) {
        this.thoiGianKetThuc = thoiGianKetThuc;
    }

    public String getPhongHoc() {
        return this.phongHoc;
    }

    public LopHoc phongHoc(String phongHoc) {
        this.setPhongHoc(phongHoc);
        return this;
    }

    public void setPhongHoc(String phongHoc) {
        this.phongHoc = phongHoc;
    }

    public String getNgayThi() {
        return this.ngayThi;
    }

    public LopHoc ngayThi(String ngayThi) {
        this.setNgayThi(ngayThi);
        return this;
    }

    public void setNgayThi(String ngayThi) {
        this.ngayThi = ngayThi;
    }

    public String getGioThi() {
        return this.gioThi;
    }

    public LopHoc gioThi(String gioThi) {
        this.setGioThi(gioThi);
        return this;
    }

    public void setGioThi(String gioThi) {
        this.gioThi = gioThi;
    }

    public String getPhongThi() {
        return this.phongThi;
    }

    public LopHoc phongThi(String phongThi) {
        this.setPhongThi(phongThi);
        return this;
    }

    public void setPhongThi(String phongThi) {
        this.phongThi = phongThi;
    }

    public HocPhan getHocPhan() {
        return this.hocPhan;
    }

    public void setHocPhan(HocPhan hocPhan) {
        this.hocPhan = hocPhan;
    }

    public LopHoc hocPhan(HocPhan hocPhan) {
        this.setHocPhan(hocPhan);
        return this;
    }

    public GiaoVien getGiaoVien() {
        return this.giaoVien;
    }

    public void setGiaoVien(GiaoVien giaoVien) {
        this.giaoVien = giaoVien;
    }

    public LopHoc giaoVien(GiaoVien giaoVien) {
        this.setGiaoVien(giaoVien);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LopHoc)) {
            return false;
        }
        return id != null && id.equals(((LopHoc) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LopHoc{" +
            "id=" + getId() +
            ", kiHoc='" + getKiHoc() + "'" +
            ", ngayHocTrongTuan='" + getNgayHocTrongTuan() + "'" +
            ", thoiGianBatDau='" + getThoiGianBatDau() + "'" +
            ", thoiGianKetThuc='" + getThoiGianKetThuc() + "'" +
            ", phongHoc='" + getPhongHoc() + "'" +
            ", ngayThi='" + getNgayThi() + "'" +
            ", gioThi='" + getGioThi() + "'" +
            ", phongThi='" + getPhongThi() + "'" +
            "}";
    }
}
