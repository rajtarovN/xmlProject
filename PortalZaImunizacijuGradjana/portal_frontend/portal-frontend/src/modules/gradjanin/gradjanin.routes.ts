import { Routes } from "@angular/router";
import { RoleGuard } from "../auth/guards/role/role.guard";
import { GradjaninHomepageComponent } from "./pages/gradjanin-homepage/gradjanin-homepage.component";

export const GradjaninRoutes: Routes = [
  {
    path: "homepage",
    pathMatch: "full",
    component: GradjaninHomepageComponent,
    canActivate: [RoleGuard],
    data: { expectedRoles: 'G' }
  },
];
