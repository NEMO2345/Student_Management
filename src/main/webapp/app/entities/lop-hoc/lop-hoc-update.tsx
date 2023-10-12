import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IHocPhan } from 'app/shared/model/hoc-phan.model';
import { getEntities as getHocPhans } from 'app/entities/hoc-phan/hoc-phan.reducer';
import { IGiaoVien } from 'app/shared/model/giao-vien.model';
import { getEntities as getGiaoViens } from 'app/entities/giao-vien/giao-vien.reducer';
import { ILopHoc } from 'app/shared/model/lop-hoc.model';
import { getEntity, updateEntity, createEntity, reset } from './lop-hoc.reducer';

export const LopHocUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const hocPhans = useAppSelector(state => state.hocPhan.entities);
  const giaoViens = useAppSelector(state => state.giaoVien.entities);
  const lopHocEntity = useAppSelector(state => state.lopHoc.entity);
  const loading = useAppSelector(state => state.lopHoc.loading);
  const updating = useAppSelector(state => state.lopHoc.updating);
  const updateSuccess = useAppSelector(state => state.lopHoc.updateSuccess);

  const handleClose = () => {
    navigate('/lop-hoc' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getHocPhans({}));
    dispatch(getGiaoViens({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...lopHocEntity,
      ...values,
      hocPhan: hocPhans.find(it => it.id.toString() === values.hocPhan.toString()),
      giaoVien: giaoViens.find(it => it.id.toString() === values.giaoVien.toString()),
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
          ...lopHocEntity,
          hocPhan: lopHocEntity?.hocPhan?.id,
          giaoVien: lopHocEntity?.giaoVien?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="quanLySinhVienApp.lopHoc.home.createOrEditLabel" data-cy="LopHocCreateUpdateHeading">
            <Translate contentKey="quanLySinhVienApp.lopHoc.home.createOrEditLabel">Create or edit a LopHoc</Translate>
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
                  id="lop-hoc-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('quanLySinhVienApp.lopHoc.kiHoc')}
                id="lop-hoc-kiHoc"
                name="kiHoc"
                data-cy="kiHoc"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  minLength: { value: 2, message: translate('entity.validation.minlength', { min: 2 }) },
                  maxLength: { value: 255, message: translate('entity.validation.maxlength', { max: 255 }) },
                }}
              />
              <ValidatedField
                label={translate('quanLySinhVienApp.lopHoc.ngayHocTrongTuan')}
                id="lop-hoc-ngayHocTrongTuan"
                name="ngayHocTrongTuan"
                data-cy="ngayHocTrongTuan"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  minLength: { value: 2, message: translate('entity.validation.minlength', { min: 2 }) },
                  maxLength: { value: 255, message: translate('entity.validation.maxlength', { max: 255 }) },
                }}
              />
              <ValidatedField
                label={translate('quanLySinhVienApp.lopHoc.thoiGianBatDau')}
                id="lop-hoc-thoiGianBatDau"
                name="thoiGianBatDau"
                data-cy="thoiGianBatDau"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  minLength: { value: 2, message: translate('entity.validation.minlength', { min: 2 }) },
                  maxLength: { value: 255, message: translate('entity.validation.maxlength', { max: 255 }) },
                }}
              />
              <ValidatedField
                label={translate('quanLySinhVienApp.lopHoc.thoiGianKetThuc')}
                id="lop-hoc-thoiGianKetThuc"
                name="thoiGianKetThuc"
                data-cy="thoiGianKetThuc"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  minLength: { value: 2, message: translate('entity.validation.minlength', { min: 2 }) },
                  maxLength: { value: 255, message: translate('entity.validation.maxlength', { max: 255 }) },
                }}
              />
              <ValidatedField
                label={translate('quanLySinhVienApp.lopHoc.phongHoc')}
                id="lop-hoc-phongHoc"
                name="phongHoc"
                data-cy="phongHoc"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  minLength: { value: 2, message: translate('entity.validation.minlength', { min: 2 }) },
                  maxLength: { value: 255, message: translate('entity.validation.maxlength', { max: 255 }) },
                }}
              />
              <ValidatedField
                label={translate('quanLySinhVienApp.lopHoc.ngayThi')}
                id="lop-hoc-ngayThi"
                name="ngayThi"
                data-cy="ngayThi"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  minLength: { value: 2, message: translate('entity.validation.minlength', { min: 2 }) },
                  maxLength: { value: 255, message: translate('entity.validation.maxlength', { max: 255 }) },
                }}
              />
              <ValidatedField
                label={translate('quanLySinhVienApp.lopHoc.gioThi')}
                id="lop-hoc-gioThi"
                name="gioThi"
                data-cy="gioThi"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  minLength: { value: 2, message: translate('entity.validation.minlength', { min: 2 }) },
                  maxLength: { value: 255, message: translate('entity.validation.maxlength', { max: 255 }) },
                }}
              />
              <ValidatedField
                label={translate('quanLySinhVienApp.lopHoc.phongThi')}
                id="lop-hoc-phongThi"
                name="phongThi"
                data-cy="phongThi"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  minLength: { value: 2, message: translate('entity.validation.minlength', { min: 2 }) },
                  maxLength: { value: 255, message: translate('entity.validation.maxlength', { max: 255 }) },
                }}
              />
              <ValidatedField
                id="lop-hoc-hocPhan"
                name="hocPhan"
                data-cy="hocPhan"
                label={translate('quanLySinhVienApp.lopHoc.hocPhan')}
                type="select"
              >
                <option value="" key="0" />
                {hocPhans
                  ? hocPhans.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="lop-hoc-giaoVien"
                name="giaoVien"
                data-cy="giaoVien"
                label={translate('quanLySinhVienApp.lopHoc.giaoVien')}
                type="select"
              >
                <option value="" key="0" />
                {giaoViens
                  ? giaoViens.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/lop-hoc" replace color="info">
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

export default LopHocUpdate;
