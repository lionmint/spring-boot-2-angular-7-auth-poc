import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from './services/auth.guard';

import { SignupComponent } from './components/signup/signup.component';
import { HomeComponent } from './components/home/home.component';
import { UserComponent } from './components/user/user.component';
import { RememberPasswordComponent } from './components/remember-password/remember-password.component';
import { RememberPasswordSuccessComponent } from './components/remember-password-success/remember-password-success.component';
import { ResetPasswordComponent } from './components/reset-password/reset-password.component';
import { ResetPasswordConfirmationComponent } from './components/reset-password-confirmation/reset-password-confirmation.component';
import { SignupConfirmationComponent } from './components/signup-confirmation/signup-confirmation.component';
import { SignupSuccessComponent } from './components/signup-success/signup-success.component';
import { LoginComponent } from './components/login/login.component';

const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    pathMatch: 'full'
  },
  {
    path: 'signup',
    component: SignupComponent
  },
  {
    path: 'user',
    component: UserComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'auth',
    component: RememberPasswordComponent
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'password/:email',
    component: ResetPasswordComponent
  },
  {
    path: 'signup/auth/:id',
    component: SignupConfirmationComponent
  },
  {
    path: 'signup/success',
    component: SignupSuccessComponent
  },
  {
    path: 'passwordconfirmation',
    component: ResetPasswordConfirmationComponent
  },
  {
    path: 'auth/success',
    component: RememberPasswordSuccessComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
