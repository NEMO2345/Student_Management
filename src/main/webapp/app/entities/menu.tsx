import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/giao-vien">
        <Translate contentKey="global.menu.entities.giaoVien" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/hoc-phan">
        <Translate contentKey="global.menu.entities.hocPhan" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/lop-dang-ki">
        <Translate contentKey="global.menu.entities.lopDangKi" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/lop-hoc">
        <Translate contentKey="global.menu.entities.lopHoc" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/nganh-hoc">
        <Translate contentKey="global.menu.entities.nganhHoc" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/nien-khoa">
        <Translate contentKey="global.menu.entities.nienKhoa" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/sinh-vien">
        <Translate contentKey="global.menu.entities.sinhVien" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
