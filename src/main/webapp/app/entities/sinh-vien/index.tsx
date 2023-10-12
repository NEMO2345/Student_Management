import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import SinhVien from './sinh-vien';
import SinhVienDetail from './sinh-vien-detail';
import SinhVienUpdate from './sinh-vien-update';
import SinhVienDeleteDialog from './sinh-vien-delete-dialog';

const SinhVienRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<SinhVien />} />
    <Route path="new" element={<SinhVienUpdate />} />
    <Route path=":id">
      <Route index element={<SinhVienDetail />} />
      <Route path="edit" element={<SinhVienUpdate />} />
      <Route path="delete" element={<SinhVienDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SinhVienRoutes;
