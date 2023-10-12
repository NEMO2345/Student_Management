import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './sinh-vien.reducer';

export const SinhVienDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const sinhVienEntity = useAppSelector(state => state.sinhVien.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="sinhVienDetailsHeading">
          <Translate contentKey="quanLySinhVienApp.sinhVien.detail.title">SinhVien</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{sinhVienEntity.id}</dd>
          <dt>
            <span id="ten">
              <Translate contentKey="quanLySinhVienApp.sinhVien.ten">Ten</Translate>
            </span>
          </dt>
          <dd>{sinhVienEntity.ten}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="quanLySinhVienApp.sinhVien.email">Email</Translate>
            </span>
          </dt>
          <dd>{sinhVienEntity.email}</dd>
          <dt>
            <span id="dienThoai">
              <Translate contentKey="quanLySinhVienApp.sinhVien.dienThoai">Dien Thoai</Translate>
            </span>
          </dt>
          <dd>{sinhVienEntity.dienThoai}</dd>
          <dt>
            <Translate contentKey="quanLySinhVienApp.sinhVien.user">User</Translate>
          </dt>
          <dd>{sinhVienEntity.user ? sinhVienEntity.user.id : ''}</dd>
          <dt>
            <Translate contentKey="quanLySinhVienApp.sinhVien.nganhHoc">Nganh Hoc</Translate>
          </dt>
          <dd>{sinhVienEntity.nganhHoc ? sinhVienEntity.nganhHoc.id : ''}</dd>
          <dt>
            <Translate contentKey="quanLySinhVienApp.sinhVien.nienKhoa">Nien Khoa</Translate>
          </dt>
          <dd>{sinhVienEntity.nienKhoa ? sinhVienEntity.nienKhoa.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/sinh-vien" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/sinh-vien/${sinhVienEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SinhVienDetail;
