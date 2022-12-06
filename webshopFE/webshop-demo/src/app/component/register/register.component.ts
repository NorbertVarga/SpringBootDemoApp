import { Component, OnInit } from '@angular/core';
import {FormBuilder} from "@angular/forms";
import {Router} from "@angular/router";
import {UserService} from "../../service/user.service";
import {AddressModel} from "../../model/address.model";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  constructor(

    private formBuilder: FormBuilder,
    private router: Router,
    private userService: UserService
  ) { }

  registerForm = this.formBuilder.group({
    firstName: [''],
    lastName: [''],
    email: [''],
    password: [''],
    country: [''],
    city: [''],
    zipcode: [''],
    street: [''],
    houseNumber: [''],
    additionalInfo: ['']
  });

  ngOnInit(): void {
  }

  submitRegister() {
    let address: AddressModel = {
      country: this.registerForm.get('country').value,
      city: this.registerForm.get('city').value,
      zipcode: this.registerForm.get('zipcode').value,
      street: this.registerForm.get('street').value,
      houseNumber: parseInt(this.registerForm.get('houseNumber').value),
      additionalInfo: this.registerForm.get('country').value,
    }
  }

}
