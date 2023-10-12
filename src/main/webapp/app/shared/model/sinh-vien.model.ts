import { IUser } from 'app/shared/model/user.model';
import { INganhHoc } from 'app/shared/model/nganh-hoc.model';
import { INienKhoa } from 'app/shared/model/nien-khoa.model';

export interface ISinhVien {
  id?: number;
  ten?: string;
  email?: string;
  dienThoai?: string;
  user?: IUser | null;
  nganhHoc?: INganhHoc | null;
  nienKhoa?: INienKhoa | null;
}

export const defaultValue: Readonly<ISinhVien> = {};
