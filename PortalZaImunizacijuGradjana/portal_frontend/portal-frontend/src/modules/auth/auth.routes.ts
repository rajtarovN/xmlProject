import { Routes } from "@angular/router";
import { LoginGuard } from "./guards/login/login.guard";
import { LoginFormComponent } from "./pages/login/login-form.component";

export const AuthRoutes: Routes = [
  {
    path: "login",
    pathMatch: "full",
    component: LoginFormComponent,
    canActivate: [LoginGuard],
  },
];
