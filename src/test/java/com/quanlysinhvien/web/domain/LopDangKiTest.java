package com.quanlysinhvien.web.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.quanlysinhvien.web.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LopDangKiTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LopDangKi.class);
        LopDangKi lopDangKi1 = new LopDangKi();
        lopDangKi1.setId(1L);
        LopDangKi lopDangKi2 = new LopDangKi();
        lopDangKi2.setId(lopDangKi1.getId());
        assertThat(lopDangKi1).isEqualTo(lopDangKi2);
        lopDangKi2.setId(2L);
        assertThat(lopDangKi1).isNotEqualTo(lopDangKi2);
        lopDangKi1.setId(null);
        assertThat(lopDangKi1).isNotEqualTo(lopDangKi2);
    }
}
