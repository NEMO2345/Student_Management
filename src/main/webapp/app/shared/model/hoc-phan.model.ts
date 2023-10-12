import { INganhHoc } from 'app/shared/model/nganh-hoc.model';

export interface IHocPhan {
  id?: number;
  ten?: string;
  moTa?: string | null;
  nganhHoc?: INganhHoc | null;
}

export const defaultValue: Readonly<IHocPhan> = {};
