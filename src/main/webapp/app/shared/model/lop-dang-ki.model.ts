import { ISinhVien } from 'app/shared/model/sinh-vien.model';
import { ILopHoc } from 'app/shared/model/lop-hoc.model';

export interface ILopDangKi {
  id?: number;
  diemQuaTrinh?: number | null;
  diemThi?: number | null;
  diemKetThucHocPhan?: number | null;
  sinhVien?: ISinhVien | null;
  lopHoc?: ILopHoc | null;
}

export const defaultValue: Readonly<ILopDangKi> = {};
