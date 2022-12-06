import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomeComponent} from "./component/home/home.component";
import {ProductComponent} from "./component/product/product.component";
import {LoginComponent} from "./component/login/login.component";
import {UserProfileComponent} from "./component/user-profile/user-profile.component";
import {RegisterComponent} from "./component/register/register.component";
import {AdminComponent} from "./component/admin/admin.component";

const routes: Routes = [
  { path: '', component: HomeComponent},
  { path: 'products', component: ProductComponent},
  { path: 'my-profile', component: UserProfileComponent},
  { path: 'login', component: LoginComponent},
  { path: 'register', component: RegisterComponent},
  { path: 'admin', component: AdminComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
