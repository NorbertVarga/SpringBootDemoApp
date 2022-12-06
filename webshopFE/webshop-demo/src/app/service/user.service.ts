import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {RegisterModel} from "../model/register.model";

const BASE_URL = "http://localhost:8080/api/users";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) {

  }
    registerUser(command: RegisterModel): Observable<any> {
      return this.http.post(BASE_URL + "/register", command);
    }
}
