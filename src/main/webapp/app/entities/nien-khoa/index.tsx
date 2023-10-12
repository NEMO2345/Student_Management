import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import NienKhoa from './nien-khoa';
import NienKhoaDetail from './nien-khoa-detail';
import NienKhoaUpdate from './nien-khoa-update';
import NienKhoaDeleteDialog from './nien-khoa-delete-dialog';

const NienKhoaRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<NienKhoa />} />
    <Route path="new" element={<NienKhoaUpdate />} />
    <Route path=":id">
      <Route index element={<NienKhoaDetail />} />
      <Route path="edit" element={<NienKhoaUpdate />} />
      <Route path="delete" element={<NienKhoaDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default NienKhoaRoutes;
