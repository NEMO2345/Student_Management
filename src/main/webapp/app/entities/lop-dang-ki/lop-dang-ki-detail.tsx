import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './lop-dang-ki.reducer';

export const LopDangKiDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const lopDangKiEntity = useAppSelector(state => state.lopDangKi.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="lopDangKiDetailsHeading">
          <Translate contentKey="quanLySinhVienApp.lopDangKi.detail.title">LopDangKi</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{lopDangKiEntity.id}</dd>
          <dt>
            <span id="diemQuaTrinh">
              <Translate contentKey="quanLySinhVienApp.lopDangKi.diemQuaTrinh">Diem Qua Trinh</Translate>
            </span>
          </dt>
          <dd>{lopDangKiEntity.diemQuaTrinh}</dd>
          <dt>
            <span id="diemThi">
              <Translate contentKey="quanLySinhVienApp.lopDangKi.diemThi">Diem Thi</Translate>
            </span>
          </dt>
          <dd>{lopDangKiEntity.diemThi}</dd>
          <dt>
            <span id="diemKetThucHocPhan">
              <Translate contentKey="quanLySinhVienApp.lopDangKi.diemKetThucHocPhan">Diem Ket Thuc Hoc Phan</Translate>
            </span>
          </dt>
          <dd>{lopDangKiEntity.diemKetThucHocPhan}</dd>
          <dt>
            <Translate contentKey="quanLySinhVienApp.lopDangKi.sinhVien">Sinh Vien</Translate>
          </dt>
          <dd>{lopDangKiEntity.sinhVien ? lopDangKiEntity.sinhVien.id : ''}</dd>
          <dt>
            <Translate contentKey="quanLySinhVienApp.lopDangKi.lopHoc">Lop Hoc</Translate>
          </dt>
          <dd>{lopDangKiEntity.lopHoc ? lopDangKiEntity.lopHoc.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/lop-dang-ki" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/lop-dang-ki/${lopDangKiEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default LopDangKiDetail;
