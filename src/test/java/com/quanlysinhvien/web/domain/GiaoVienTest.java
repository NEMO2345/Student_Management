package com.quanlysinhvien.web.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.quanlysinhvien.web.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GiaoVienTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GiaoVien.class);
        GiaoVien giaoVien1 = new GiaoVien();
        giaoVien1.setId(1L);
        GiaoVien giaoVien2 = new GiaoVien();
        giaoVien2.setId(giaoVien1.getId());
        assertThat(giaoVien1).isEqualTo(giaoVien2);
        giaoVien2.setId(2L);
        assertThat(giaoVien1).isNotEqualTo(giaoVien2);
        giaoVien1.setId(null);
        assertThat(giaoVien1).isNotEqualTo(giaoVien2);
    }
}
