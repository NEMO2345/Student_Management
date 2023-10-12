import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import LopHoc from './lop-hoc';
import LopHocDetail from './lop-hoc-detail';
import LopHocUpdate from './lop-hoc-update';
import LopHocDeleteDialog from './lop-hoc-delete-dialog';

const LopHocRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<LopHoc />} />
    <Route path="new" element={<LopHocUpdate />} />
    <Route path=":id">
      <Route index element={<LopHocDetail />} />
      <Route path="edit" element={<LopHocUpdate />} />
      <Route path="delete" element={<LopHocDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LopHocRoutes;
