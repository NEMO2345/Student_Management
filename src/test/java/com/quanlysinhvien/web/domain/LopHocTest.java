package com.quanlysinhvien.web.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.quanlysinhvien.web.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LopHocTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LopHoc.class);
        LopHoc lopHoc1 = new LopHoc();
        lopHoc1.setId(1L);
        LopHoc lopHoc2 = new LopHoc();
        lopHoc2.setId(lopHoc1.getId());
        assertThat(lopHoc1).isEqualTo(lopHoc2);
        lopHoc2.setId(2L);
        assertThat(lopHoc1).isNotEqualTo(lopHoc2);
        lopHoc1.setId(null);
        assertThat(lopHoc1).isNotEqualTo(lopHoc2);
    }
}
