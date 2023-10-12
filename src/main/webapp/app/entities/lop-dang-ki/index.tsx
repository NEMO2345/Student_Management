import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import LopDangKi from './lop-dang-ki';
import LopDangKiDetail from './lop-dang-ki-detail';
import LopDangKiUpdate from './lop-dang-ki-update';
import LopDangKiDeleteDialog from './lop-dang-ki-delete-dialog';

const LopDangKiRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<LopDangKi />} />
    <Route path="new" element={<LopDangKiUpdate />} />
    <Route path=":id">
      <Route index element={<LopDangKiDetail />} />
      <Route path="edit" element={<LopDangKiUpdate />} />
      <Route path="delete" element={<LopDangKiDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LopDangKiRoutes;
