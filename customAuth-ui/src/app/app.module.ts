import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatButtonModule, MatToolbarModule, MatSidenavModule, MatInputModule, MatListModule, MatTableModule, MatCardModule} from '@angular/material';
import { AppRoutingModule } from './app-routing.module';

import { AppComponent } from './app.component';
import { MenuComponent } from './components/menu/menu.component';
import { SignupComponent } from './components/signup/signup.component';
import { SignupConfirmationComponent } from './components/signup-confirmation/signup-confirmation.component';
import { SignupSuccessComponent } from './components/signup-success/signup-success.component';
import { HomeComponent } from './components/home/home.component';
import { UserComponent } from './components/user/user.component';
import { LoginComponent } from './components/login/login.component';
import { RememberPasswordComponent } from './components/remember-password/remember-password.component';
import { RememberPasswordSuccessComponent } from './components/remember-password-success/remember-password-success.component';
import { ResetPasswordComponent } from './components/reset-password/reset-password.component';
import { ResetPasswordConfirmationComponent } from './components/reset-password-confirmation/reset-password-confirmation.component';

import {CustomAuthService} from './services/custom-auth.service';
import {AuthGuard} from './services/auth.guard';
import {LoginService} from './services/login.service';
import {PasswordchangeService} from './services/passwordchange.service';

@NgModule({
  declarations: [
    AppComponent,
    MenuComponent,
    SignupComponent,
    HomeComponent,
    UserComponent,
    LoginComponent,
    RememberPasswordComponent,
    ResetPasswordComponent,
    SignupConfirmationComponent,
    SignupSuccessComponent,
    RememberPasswordSuccessComponent,
    ResetPasswordConfirmationComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatToolbarModule,
    MatSidenavModule,
    MatInputModule,
    MatListModule,
    MatCardModule,
    MatTableModule,
    HttpClientModule,
    ReactiveFormsModule
  ],
  providers: [
    CustomAuthService,
    AuthGuard,
    LoginService,
    PasswordchangeService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
