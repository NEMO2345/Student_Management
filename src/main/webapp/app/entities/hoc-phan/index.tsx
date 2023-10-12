import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import HocPhan from './hoc-phan';
import HocPhanDetail from './hoc-phan-detail';
import HocPhanUpdate from './hoc-phan-update';
import HocPhanDeleteDialog from './hoc-phan-delete-dialog';

const HocPhanRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<HocPhan />} />
    <Route path="new" element={<HocPhanUpdate />} />
    <Route path=":id">
      <Route index element={<HocPhanDetail />} />
      <Route path="edit" element={<HocPhanUpdate />} />
      <Route path="delete" element={<HocPhanDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default HocPhanRoutes;
