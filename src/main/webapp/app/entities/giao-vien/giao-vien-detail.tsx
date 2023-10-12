import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './giao-vien.reducer';

export const GiaoVienDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const giaoVienEntity = useAppSelector(state => state.giaoVien.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="giaoVienDetailsHeading">
          <Translate contentKey="quanLySinhVienApp.giaoVien.detail.title">GiaoVien</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{giaoVienEntity.id}</dd>
          <dt>
            <span id="ten">
              <Translate contentKey="quanLySinhVienApp.giaoVien.ten">Ten</Translate>
            </span>
          </dt>
          <dd>{giaoVienEntity.ten}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="quanLySinhVienApp.giaoVien.email">Email</Translate>
            </span>
          </dt>
          <dd>{giaoVienEntity.email}</dd>
          <dt>
            <span id="sdt">
              <Translate contentKey="quanLySinhVienApp.giaoVien.sdt">Sdt</Translate>
            </span>
          </dt>
          <dd>{giaoVienEntity.sdt}</dd>
          <dt>
            <Translate contentKey="quanLySinhVienApp.giaoVien.user">User</Translate>
          </dt>
          <dd>{giaoVienEntity.user ? giaoVienEntity.user.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/giao-vien" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/giao-vien/${giaoVienEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default GiaoVienDetail;
