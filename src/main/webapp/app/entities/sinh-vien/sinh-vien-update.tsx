import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { INganhHoc } from 'app/shared/model/nganh-hoc.model';
import { getEntities as getNganhHocs } from 'app/entities/nganh-hoc/nganh-hoc.reducer';
import { INienKhoa } from 'app/shared/model/nien-khoa.model';
import { getEntities as getNienKhoas } from 'app/entities/nien-khoa/nien-khoa.reducer';
import { ISinhVien } from 'app/shared/model/sinh-vien.model';
import { getEntity, updateEntity, createEntity, reset } from './sinh-vien.reducer';

export const SinhVienUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const users = useAppSelector(state => state.userManagement.users);
  const nganhHocs = useAppSelector(state => state.nganhHoc.entities);
  const nienKhoas = useAppSelector(state => state.nienKhoa.entities);
  const sinhVienEntity = useAppSelector(state => state.sinhVien.entity);
  const loading = useAppSelector(state => state.sinhVien.loading);
  const updating = useAppSelector(state => state.sinhVien.updating);
  const updateSuccess = useAppSelector(state => state.sinhVien.updateSuccess);

  const handleClose = () => {
    navigate('/sinh-vien' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getUsers({}));
    dispatch(getNganhHocs({}));
    dispatch(getNienKhoas({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...sinhVienEntity,
      ...values,
      user: users.find(it => it.id.toString() === values.user.toString()),
      nganhHoc: nganhHocs.find(it => it.id.toString() === values.nganhHoc.toString()),
      nienKhoa: nienKhoas.find(it => it.id.toString() === values.nienKhoa.toString()),
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
          ...sinhVienEntity,
          user: sinhVienEntity?.user?.id,
          nganhHoc: sinhVienEntity?.nganhHoc?.id,
          nienKhoa: sinhVienEntity?.nienKhoa?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="quanLySinhVienApp.sinhVien.home.createOrEditLabel" data-cy="SinhVienCreateUpdateHeading">
            <Translate contentKey="quanLySinhVienApp.sinhVien.home.createOrEditLabel">Create or edit a SinhVien</Translate>
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
                  id="sinh-vien-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('quanLySinhVienApp.sinhVien.ten')}
                id="sinh-vien-ten"
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
                label={translate('quanLySinhVienApp.sinhVien.email')}
                id="sinh-vien-email"
                name="email"
                data-cy="email"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  minLength: { value: 2, message: translate('entity.validation.minlength', { min: 2 }) },
                  maxLength: { value: 255, message: translate('entity.validation.maxlength', { max: 255 }) },
                }}
              />
              <ValidatedField
                label={translate('quanLySinhVienApp.sinhVien.dienThoai')}
                id="sinh-vien-dienThoai"
                name="dienThoai"
                data-cy="dienThoai"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  minLength: { value: 10, message: translate('entity.validation.minlength', { min: 10 }) },
                  maxLength: { value: 11, message: translate('entity.validation.maxlength', { max: 11 }) },
                }}
              />
              <ValidatedField
                id="sinh-vien-user"
                name="user"
                data-cy="user"
                label={translate('quanLySinhVienApp.sinhVien.user')}
                type="select"
              >
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="sinh-vien-nganhHoc"
                name="nganhHoc"
                data-cy="nganhHoc"
                label={translate('quanLySinhVienApp.sinhVien.nganhHoc')}
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
              <ValidatedField
                id="sinh-vien-nienKhoa"
                name="nienKhoa"
                data-cy="nienKhoa"
                label={translate('quanLySinhVienApp.sinhVien.nienKhoa')}
                type="select"
              >
                <option value="" key="0" />
                {nienKhoas
                  ? nienKhoas.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/sinh-vien" replace color="info">
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

export default SinhVienUpdate;
