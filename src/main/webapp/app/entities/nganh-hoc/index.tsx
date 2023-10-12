import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import NganhHoc from './nganh-hoc';
import NganhHocDetail from './nganh-hoc-detail';
import NganhHocUpdate from './nganh-hoc-update';
import NganhHocDeleteDialog from './nganh-hoc-delete-dialog';

const NganhHocRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<NganhHoc />} />
    <Route path="new" element={<NganhHocUpdate />} />
    <Route path=":id">
      <Route index element={<NganhHocDetail />} />
      <Route path="edit" element={<NganhHocUpdate />} />
      <Route path="delete" element={<NganhHocDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default NganhHocRoutes;
