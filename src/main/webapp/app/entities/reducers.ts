import giaoVien from 'app/entities/giao-vien/giao-vien.reducer';
import hocPhan from 'app/entities/hoc-phan/hoc-phan.reducer';
import lopDangKi from 'app/entities/lop-dang-ki/lop-dang-ki.reducer';
import lopHoc from 'app/entities/lop-hoc/lop-hoc.reducer';
import nganhHoc from 'app/entities/nganh-hoc/nganh-hoc.reducer';
import nienKhoa from 'app/entities/nien-khoa/nien-khoa.reducer';
import sinhVien from 'app/entities/sinh-vien/sinh-vien.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  giaoVien,
  hocPhan,
  lopDangKi,
  lopHoc,
  nganhHoc,
  nienKhoa,
  sinhVien,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
