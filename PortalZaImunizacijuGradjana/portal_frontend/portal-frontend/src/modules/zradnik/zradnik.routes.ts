import { Routes } from "@angular/router";
import { RoleGuard } from "../auth/guards/role/role.guard";
import { ZradnikHomepageComponent } from "./pages/zradnik-homepage/zradnik-homepage.component";

export const ZRadnikRoutes: Routes = [
  {
    path: "homepage",
    pathMatch: "full",
    component: ZradnikHomepageComponent,
    canActivate: [RoleGuard],
    data: { expectedRoles: 'Z' }
  },
];
