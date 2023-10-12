import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import GiaoVien from './giao-vien';
import GiaoVienDetail from './giao-vien-detail';
import GiaoVienUpdate from './giao-vien-update';
import GiaoVienDeleteDialog from './giao-vien-delete-dialog';

const GiaoVienRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<GiaoVien />} />
    <Route path="new" element={<GiaoVienUpdate />} />
    <Route path=":id">
      <Route index element={<GiaoVienDetail />} />
      <Route path="edit" element={<GiaoVienUpdate />} />
      <Route path="delete" element={<GiaoVienDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default GiaoVienRoutes;
