package com.quanlysinhvien.web.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A NienKhoa.
 */
@Entity
@Table(name = "nien_khoa")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NienKhoa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 2, max = 255)
    @Column(name = "ten", length = 255, nullable = false)
    private String ten;

    @Column(name = "nam_bat_dau")
    private Integer namBatDau;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public NienKhoa id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTen() {
        return this.ten;
    }

    public NienKhoa ten(String ten) {
        this.setTen(ten);
        return this;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public Integer getNamBatDau() {
        return this.namBatDau;
    }

    public NienKhoa namBatDau(Integer namBatDau) {
        this.setNamBatDau(namBatDau);
        return this;
    }

    public void setNamBatDau(Integer namBatDau) {
        this.namBatDau = namBatDau;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NienKhoa)) {
            return false;
        }
        return id != null && id.equals(((NienKhoa) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NienKhoa{" +
            "id=" + getId() +
            ", ten='" + getTen() + "'" +
            ", namBatDau=" + getNamBatDau() +
            "}";
    }
}
