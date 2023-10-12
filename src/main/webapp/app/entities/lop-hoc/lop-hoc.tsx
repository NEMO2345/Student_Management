import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getPaginationState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './lop-hoc.reducer';

export const LopHoc = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const lopHocList = useAppSelector(state => state.lopHoc.entities);
  const loading = useAppSelector(state => state.lopHoc.loading);
  const totalItems = useAppSelector(state => state.lopHoc.totalItems);

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
      <h2 id="lop-hoc-heading" data-cy="LopHocHeading">
        <Translate contentKey="quanLySinhVienApp.lopHoc.home.title">Lop Hocs</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="quanLySinhVienApp.lopHoc.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/lop-hoc/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="quanLySinhVienApp.lopHoc.home.createLabel">Create new Lop Hoc</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {lopHocList && lopHocList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="quanLySinhVienApp.lopHoc.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('kiHoc')}>
                  <Translate contentKey="quanLySinhVienApp.lopHoc.kiHoc">Ki Hoc</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('kiHoc')} />
                </th>
                <th className="hand" onClick={sort('ngayHocTrongTuan')}>
                  <Translate contentKey="quanLySinhVienApp.lopHoc.ngayHocTrongTuan">Ngay Hoc Trong Tuan</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('ngayHocTrongTuan')} />
                </th>
                <th className="hand" onClick={sort('thoiGianBatDau')}>
                  <Translate contentKey="quanLySinhVienApp.lopHoc.thoiGianBatDau">Thoi Gian Bat Dau</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('thoiGianBatDau')} />
                </th>
                <th className="hand" onClick={sort('thoiGianKetThuc')}>
                  <Translate contentKey="quanLySinhVienApp.lopHoc.thoiGianKetThuc">Thoi Gian Ket Thuc</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('thoiGianKetThuc')} />
                </th>
                <th className="hand" onClick={sort('phongHoc')}>
                  <Translate contentKey="quanLySinhVienApp.lopHoc.phongHoc">Phong Hoc</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('phongHoc')} />
                </th>
                <th className="hand" onClick={sort('ngayThi')}>
                  <Translate contentKey="quanLySinhVienApp.lopHoc.ngayThi">Ngay Thi</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('ngayThi')} />
                </th>
                <th className="hand" onClick={sort('gioThi')}>
                  <Translate contentKey="quanLySinhVienApp.lopHoc.gioThi">Gio Thi</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('gioThi')} />
                </th>
                <th className="hand" onClick={sort('phongThi')}>
                  <Translate contentKey="quanLySinhVienApp.lopHoc.phongThi">Phong Thi</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('phongThi')} />
                </th>
                <th>
                  <Translate contentKey="quanLySinhVienApp.lopHoc.hocPhan">Hoc Phan</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="quanLySinhVienApp.lopHoc.giaoVien">Giao Vien</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {lopHocList.map((lopHoc, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/lop-hoc/${lopHoc.id}`} color="link" size="sm">
                      {lopHoc.id}
                    </Button>
                  </td>
                  <td>{lopHoc.kiHoc}</td>
                  <td>{lopHoc.ngayHocTrongTuan}</td>
                  <td>{lopHoc.thoiGianBatDau}</td>
                  <td>{lopHoc.thoiGianKetThuc}</td>
                  <td>{lopHoc.phongHoc}</td>
                  <td>{lopHoc.ngayThi}</td>
                  <td>{lopHoc.gioThi}</td>
                  <td>{lopHoc.phongThi}</td>
                  <td>{lopHoc.hocPhan ? <Link to={`/hoc-phan/${lopHoc.hocPhan.id}`}>{lopHoc.hocPhan.id}</Link> : ''}</td>
                  <td>{lopHoc.giaoVien ? <Link to={`/giao-vien/${lopHoc.giaoVien.id}`}>{lopHoc.giaoVien.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/lop-hoc/${lopHoc.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/lop-hoc/${lopHoc.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`/lop-hoc/${lopHoc.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="quanLySinhVienApp.lopHoc.home.notFound">No Lop Hocs found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={lopHocList && lopHocList.length > 0 ? '' : 'd-none'}>
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

export default LopHoc;
