import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { INganhHoc } from 'app/shared/model/nganh-hoc.model';
import { getEntities as getNganhHocs } from 'app/entities/nganh-hoc/nganh-hoc.reducer';
import { IHocPhan } from 'app/shared/model/hoc-phan.model';
import { getEntity, updateEntity, createEntity, reset } from './hoc-phan.reducer';

export const HocPhanUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const nganhHocs = useAppSelector(state => state.nganhHoc.entities);
  const hocPhanEntity = useAppSelector(state => state.hocPhan.entity);
  const loading = useAppSelector(state => state.hocPhan.loading);
  const updating = useAppSelector(state => state.hocPhan.updating);
  const updateSuccess = useAppSelector(state => state.hocPhan.updateSuccess);

  const handleClose = () => {
    navigate('/hoc-phan' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getNganhHocs({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...hocPhanEntity,
      ...values,
      nganhHoc: nganhHocs.find(it => it.id.toString() === values.nganhHoc.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...hocPhanEntity,
          nganhHoc: hocPhanEntity?.nganhHoc?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="quanLySinhVienApp.hocPhan.home.createOrEditLabel" data-cy="HocPhanCreateUpdateHeading">
            <Translate contentKey="quanLySinhVienApp.hocPhan.home.createOrEditLabel">Create or edit a HocPhan</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="hoc-phan-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('quanLySinhVienApp.hocPhan.ten')}
                id="hoc-phan-ten"
                name="ten"
                data-cy="ten"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  minLength: { value: 2, message: translate('entity.validation.minlength', { min: 2 }) },
                  maxLength: { value: 255, message: translate('entity.validation.maxlength', { max: 255 }) },
                }}
              />
              <ValidatedField
                label={translate('quanLySinhVienApp.hocPhan.moTa')}
                id="hoc-phan-moTa"
                name="moTa"
                data-cy="moTa"
                type="text"
              />
              <ValidatedField
                id="hoc-phan-nganhHoc"
                name="nganhHoc"
                data-cy="nganhHoc"
                label={translate('quanLySinhVienApp.hocPhan.nganhHoc')}
                type="select"
              >
                <option value="" key="0" />
                {nganhHocs
                  ? nganhHocs.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/hoc-phan" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default HocPhanUpdate;
