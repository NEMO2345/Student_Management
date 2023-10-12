package com.quanlysinhvien.web.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A HocPhan.
 */
@Entity
@Table(name = "hoc_phan")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class HocPhan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 2, max = 255)
    @Column(name = "ten", length = 255, nullable = false)
    private String ten;

    @Column(name = "mo_ta")
    private String moTa;

    @ManyToOne(fetch = FetchType.LAZY)
    private NganhHoc nganhHoc;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public HocPhan id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTen() {
        return this.ten;
    }

    public HocPhan ten(String ten) {
        this.setTen(ten);
        return this;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getMoTa() {
        return this.moTa;
    }

    public HocPhan moTa(String moTa) {
        this.setMoTa(moTa);
        return this;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public NganhHoc getNganhHoc() {
        return this.nganhHoc;
    }

    public void setNganhHoc(NganhHoc nganhHoc) {
        this.nganhHoc = nganhHoc;
    }

    public HocPhan nganhHoc(NganhHoc nganhHoc) {
        this.setNganhHoc(nganhHoc);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HocPhan)) {
            return false;
        }
        return id != null && id.equals(((HocPhan) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HocPhan{" +
            "id=" + getId() +
            ", ten='" + getTen() + "'" +
            ", moTa='" + getMoTa() + "'" +
            "}";
    }
}
