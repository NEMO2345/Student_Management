import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './lop-hoc.reducer';

export const LopHocDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const lopHocEntity = useAppSelector(state => state.lopHoc.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="lopHocDetailsHeading">
          <Translate contentKey="quanLySinhVienApp.lopHoc.detail.title">LopHoc</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{lopHocEntity.id}</dd>
          <dt>
            <span id="kiHoc">
              <Translate contentKey="quanLySinhVienApp.lopHoc.kiHoc">Ki Hoc</Translate>
            </span>
          </dt>
          <dd>{lopHocEntity.kiHoc}</dd>
          <dt>
            <span id="ngayHocTrongTuan">
              <Translate contentKey="quanLySinhVienApp.lopHoc.ngayHocTrongTuan">Ngay Hoc Trong Tuan</Translate>
            </span>
          </dt>
          <dd>{lopHocEntity.ngayHocTrongTuan}</dd>
          <dt>
            <span id="thoiGianBatDau">
              <Translate contentKey="quanLySinhVienApp.lopHoc.thoiGianBatDau">Thoi Gian Bat Dau</Translate>
            </span>
          </dt>
          <dd>{lopHocEntity.thoiGianBatDau}</dd>
          <dt>
            <span id="thoiGianKetThuc">
              <Translate contentKey="quanLySinhVienApp.lopHoc.thoiGianKetThuc">Thoi Gian Ket Thuc</Translate>
            </span>
          </dt>
          <dd>{lopHocEntity.thoiGianKetThuc}</dd>
          <dt>
            <span id="phongHoc">
              <Translate contentKey="quanLySinhVienApp.lopHoc.phongHoc">Phong Hoc</Translate>
            </span>
          </dt>
          <dd>{lopHocEntity.phongHoc}</dd>
          <dt>
            <span id="ngayThi">
              <Translate contentKey="quanLySinhVienApp.lopHoc.ngayThi">Ngay Thi</Translate>
            </span>
          </dt>
          <dd>{lopHocEntity.ngayThi}</dd>
          <dt>
            <span id="gioThi">
              <Translate contentKey="quanLySinhVienApp.lopHoc.gioThi">Gio Thi</Translate>
            </span>
          </dt>
          <dd>{lopHocEntity.gioThi}</dd>
          <dt>
            <span id="phongThi">
              <Translate contentKey="quanLySinhVienApp.lopHoc.phongThi">Phong Thi</Translate>
            </span>
          </dt>
          <dd>{lopHocEntity.phongThi}</dd>
          <dt>
            <Translate contentKey="quanLySinhVienApp.lopHoc.hocPhan">Hoc Phan</Translate>
          </dt>
          <dd>{lopHocEntity.hocPhan ? lopHocEntity.hocPhan.id : ''}</dd>
          <dt>
            <Translate contentKey="quanLySinhVienApp.lopHoc.giaoVien">Giao Vien</Translate>
          </dt>
          <dd>{lopHocEntity.giaoVien ? lopHocEntity.giaoVien.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/lop-hoc" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/lop-hoc/${lopHocEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default LopHocDetail;
