import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './nganh-hoc.reducer';

export const NganhHocDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const nganhHocEntity = useAppSelector(state => state.nganhHoc.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="nganhHocDetailsHeading">
          <Translate contentKey="quanLySinhVienApp.nganhHoc.detail.title">NganhHoc</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{nganhHocEntity.id}</dd>
          <dt>
            <span id="ten">
              <Translate contentKey="quanLySinhVienApp.nganhHoc.ten">Ten</Translate>
            </span>
          </dt>
          <dd>{nganhHocEntity.ten}</dd>
        </dl>
        <Button tag={Link} to="/nganh-hoc" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/nganh-hoc/${nganhHocEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default NganhHocDetail;
