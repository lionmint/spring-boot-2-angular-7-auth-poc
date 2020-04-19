export interface AuthorizationEntity {
  authorized: boolean;
  email: string;
  firstName: string;
  lastName: string;
  expiresIn: number;
  accessToken: string;
  admin: boolean;
  newUser: boolean;
}
