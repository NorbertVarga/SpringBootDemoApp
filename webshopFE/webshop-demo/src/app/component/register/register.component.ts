import { Component, OnInit } from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {UserService} from "../../service/user.service";
import {AddressModel} from "../../model/address.model";
import {RegisterModel} from "../../model/register.model";
import {validationHandler} from "../utils/validationHandler";

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
    email: ['', [Validators.required, Validators.min(0)]],
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
    let addressCommand: AddressModel = {
      country: this.registerForm.get('country').value,
      city: this.registerForm.get('city').value,
      zipcode: this.registerForm.get('zipcode').value,
      street: this.registerForm.get('street').value,
      houseNumber: parseInt(this.registerForm.get('houseNumber').value),
      additionalInfo: this.registerForm.get('country').value,
    }

    let registerCommand: RegisterModel = {
      firstName: this.registerForm.get('firstName').value,
      lastName: this.registerForm.get('lastName').value,
      email: this.registerForm.get('email').value,
      password: this.registerForm.get('password').value,
      address: addressCommand
    }

    this.userService.registerUser(registerCommand).subscribe(
      result => {
        this.router.navigate(['login']);
      },
      error => {
        validationHandler(error, this.registerForm);
      }
    )
  }

}
