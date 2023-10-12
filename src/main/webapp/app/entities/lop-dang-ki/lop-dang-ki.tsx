import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getPaginationState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './lop-dang-ki.reducer';

export const LopDangKi = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const lopDangKiList = useAppSelector(state => state.lopDangKi.entities);
  const loading = useAppSelector(state => state.lopDangKi.loading);
  const totalItems = useAppSelector(state => state.lopDangKi.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (location.search !== endURL) {
      navigate(`${location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(location.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = paginationState.sort;
    const order = paginationState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div>
      <h2 id="lop-dang-ki-heading" data-cy="LopDangKiHeading">
        <Translate contentKey="quanLySinhVienApp.lopDangKi.home.title">Lop Dang Kis</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="quanLySinhVienApp.lopDangKi.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/lop-dang-ki/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="quanLySinhVienApp.lopDangKi.home.createLabel">Create new Lop Dang Ki</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {lopDangKiList && lopDangKiList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="quanLySinhVienApp.lopDangKi.id">ID</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('diemQuaTrinh')}>
                  <Translate contentKey="quanLySinhVienApp.lopDangKi.diemQuaTrinh">Diem Qua Trinh</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('diemQuaTrinh')} />
                </th>
                <th className="hand" onClick={sort('diemThi')}>
                  <Translate contentKey="quanLySinhVienApp.lopDangKi.diemThi">Diem Thi</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('diemThi')} />
                </th>
                <th className="hand" onClick={sort('diemKetThucHocPhan')}>
                  <Translate contentKey="quanLySinhVienApp.lopDangKi.diemKetThucHocPhan">Diem Ket Thuc Hoc Phan</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('diemKetThucHocPhan')} />
                </th>
                <th>
                  <Translate contentKey="quanLySinhVienApp.lopDangKi.sinhVien">Sinh Vien</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="quanLySinhVienApp.lopDangKi.lopHoc">Lop Hoc</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {lopDangKiList.map((lopDangKi, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/lop-dang-ki/${lopDangKi.id}`} color="link" size="sm">
                      {lopDangKi.id}
                    </Button>
                  </td>
                  <td>{lopDangKi.diemQuaTrinh}</td>
                  <td>{lopDangKi.diemThi}</td>
                  <td>{lopDangKi.diemKetThucHocPhan}</td>
                  <td>{lopDangKi.sinhVien ? <Link to={`/sinh-vien/${lopDangKi.sinhVien.id}`}>{lopDangKi.sinhVien.id}</Link> : ''}</td>
                  <td>{lopDangKi.lopHoc ? <Link to={`/lop-hoc/${lopDangKi.lopHoc.id}`}>{lopDangKi.lopHoc.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/lop-dang-ki/${lopDangKi.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/lop-dang-ki/${lopDangKi.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/lop-dang-ki/${lopDangKi.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="quanLySinhVienApp.lopDangKi.home.notFound">No Lop Dang Kis found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={lopDangKiList && lopDangKiList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default LopDangKi;
