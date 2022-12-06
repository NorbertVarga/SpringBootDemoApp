import {AddressModel} from "./address.model";

export interface RegisterModel {

  firstName: string;
  lastName: string;
  email: string;
  password: string;
  address: AddressModel;

}
