import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISinhVien } from 'app/shared/model/sinh-vien.model';
import { getEntities as getSinhViens } from 'app/entities/sinh-vien/sinh-vien.reducer';
import { ILopHoc } from 'app/shared/model/lop-hoc.model';
import { getEntities as getLopHocs } from 'app/entities/lop-hoc/lop-hoc.reducer';
import { ILopDangKi } from 'app/shared/model/lop-dang-ki.model';
import { getEntity, updateEntity, createEntity, reset } from './lop-dang-ki.reducer';

export const LopDangKiUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const sinhViens = useAppSelector(state => state.sinhVien.entities);
  const lopHocs = useAppSelector(state => state.lopHoc.entities);
  const lopDangKiEntity = useAppSelector(state => state.lopDangKi.entity);
  const loading = useAppSelector(state => state.lopDangKi.loading);
  const updating = useAppSelector(state => state.lopDangKi.updating);
  const updateSuccess = useAppSelector(state => state.lopDangKi.updateSuccess);

  const handleClose = () => {
    navigate('/lop-dang-ki' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getSinhViens({}));
    dispatch(getLopHocs({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...lopDangKiEntity,
      ...values,
      sinhVien: sinhViens.find(it => it.id.toString() === values.sinhVien.toString()),
      lopHoc: lopHocs.find(it => it.id.toString() === values.lopHoc.toString()),
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
          ...lopDangKiEntity,
          sinhVien: lopDangKiEntity?.sinhVien?.id,
          lopHoc: lopDangKiEntity?.lopHoc?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="quanLySinhVienApp.lopDangKi.home.createOrEditLabel" data-cy="LopDangKiCreateUpdateHeading">
            <Translate contentKey="quanLySinhVienApp.lopDangKi.home.createOrEditLabel">Create or edit a LopDangKi</Translate>
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
                  id="lop-dang-ki-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('quanLySinhVienApp.lopDangKi.diemQuaTrinh')}
                id="lop-dang-ki-diemQuaTrinh"
                name="diemQuaTrinh"
                data-cy="diemQuaTrinh"
                type="text"
              />
              <ValidatedField
                label={translate('quanLySinhVienApp.lopDangKi.diemThi')}
                id="lop-dang-ki-diemThi"
                name="diemThi"
                data-cy="diemThi"
                type="text"
              />
              <ValidatedField
                label={translate('quanLySinhVienApp.lopDangKi.diemKetThucHocPhan')}
                id="lop-dang-ki-diemKetThucHocPhan"
                name="diemKetThucHocPhan"
                data-cy="diemKetThucHocPhan"
                type="text"
              />
              <ValidatedField
                id="lop-dang-ki-sinhVien"
                name="sinhVien"
                data-cy="sinhVien"
                label={translate('quanLySinhVienApp.lopDangKi.sinhVien')}
                type="select"
              >
                <option value="" key="0" />
                {sinhViens
                  ? sinhViens.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="lop-dang-ki-lopHoc"
                name="lopHoc"
                data-cy="lopHoc"
                label={translate('quanLySinhVienApp.lopDangKi.lopHoc')}
                type="select"
              >
                <option value="" key="0" />
                {lopHocs
                  ? lopHocs.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/lop-dang-ki" replace color="info">
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

export default LopDangKiUpdate;
