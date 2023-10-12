package com.quanlysinhvien.web.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.quanlysinhvien.web.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SinhVienTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SinhVien.class);
        SinhVien sinhVien1 = new SinhVien();
        sinhVien1.setId(1L);
        SinhVien sinhVien2 = new SinhVien();
        sinhVien2.setId(sinhVien1.getId());
        assertThat(sinhVien1).isEqualTo(sinhVien2);
        sinhVien2.setId(2L);
        assertThat(sinhVien1).isNotEqualTo(sinhVien2);
        sinhVien1.setId(null);
        assertThat(sinhVien1).isNotEqualTo(sinhVien2);
    }
}
