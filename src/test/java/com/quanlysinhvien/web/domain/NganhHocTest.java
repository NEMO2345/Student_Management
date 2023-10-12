package com.quanlysinhvien.web.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.quanlysinhvien.web.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NganhHocTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NganhHoc.class);
        NganhHoc nganhHoc1 = new NganhHoc();
        nganhHoc1.setId(1L);
        NganhHoc nganhHoc2 = new NganhHoc();
        nganhHoc2.setId(nganhHoc1.getId());
        assertThat(nganhHoc1).isEqualTo(nganhHoc2);
        nganhHoc2.setId(2L);
        assertThat(nganhHoc1).isNotEqualTo(nganhHoc2);
        nganhHoc1.setId(null);
        assertThat(nganhHoc1).isNotEqualTo(nganhHoc2);
    }
}
