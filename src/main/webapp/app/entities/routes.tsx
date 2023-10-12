import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import GiaoVien from './giao-vien';
import HocPhan from './hoc-phan';
import LopDangKi from './lop-dang-ki';
import LopHoc from './lop-hoc';
import NganhHoc from './nganh-hoc';
import NienKhoa from './nien-khoa';
import SinhVien from './sinh-vien';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="giao-vien/*" element={<GiaoVien />} />
        <Route path="hoc-phan/*" element={<HocPhan />} />
        <Route path="lop-dang-ki/*" element={<LopDangKi />} />
        <Route path="lop-hoc/*" element={<LopHoc />} />
        <Route path="nganh-hoc/*" element={<NganhHoc />} />
        <Route path="nien-khoa/*" element={<NienKhoa />} />
        <Route path="sinh-vien/*" element={<SinhVien />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
