import { IUser } from 'app/shared/model/user.model';

export interface IGiaoVien {
  id?: number;
  ten?: string;
  email?: string;
  sdt?: string;
  user?: IUser | null;
}

export const defaultValue: Readonly<IGiaoVien> = {};
