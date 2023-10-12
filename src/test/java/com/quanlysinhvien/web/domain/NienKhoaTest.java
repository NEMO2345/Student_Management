package com.quanlysinhvien.web.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.quanlysinhvien.web.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NienKhoaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NienKhoa.class);
        NienKhoa nienKhoa1 = new NienKhoa();
        nienKhoa1.setId(1L);
        NienKhoa nienKhoa2 = new NienKhoa();
        nienKhoa2.setId(nienKhoa1.getId());
        assertThat(nienKhoa1).isEqualTo(nienKhoa2);
        nienKhoa2.setId(2L);
        assertThat(nienKhoa1).isNotEqualTo(nienKhoa2);
        nienKhoa1.setId(null);
        assertThat(nienKhoa1).isNotEqualTo(nienKhoa2);
    }
}
