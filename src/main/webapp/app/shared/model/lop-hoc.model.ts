import { IHocPhan } from 'app/shared/model/hoc-phan.model';
import { IGiaoVien } from 'app/shared/model/giao-vien.model';

export interface ILopHoc {
  id?: number;
  kiHoc?: string;
  ngayHocTrongTuan?: string;
  thoiGianBatDau?: string;
  thoiGianKetThuc?: string;
  phongHoc?: string;
  ngayThi?: string;
  gioThi?: string;
  phongThi?: string;
  hocPhan?: IHocPhan | null;
  giaoVien?: IGiaoVien | null;
}

export const defaultValue: Readonly<ILopHoc> = {};
