package com.quanlysinhvien.web.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.quanlysinhvien.web.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HocPhanTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HocPhan.class);
        HocPhan hocPhan1 = new HocPhan();
        hocPhan1.setId(1L);
        HocPhan hocPhan2 = new HocPhan();
        hocPhan2.setId(hocPhan1.getId());
        assertThat(hocPhan1).isEqualTo(hocPhan2);
        hocPhan2.setId(2L);
        assertThat(hocPhan1).isNotEqualTo(hocPhan2);
        hocPhan1.setId(null);
        assertThat(hocPhan1).isNotEqualTo(hocPhan2);
    }
}
