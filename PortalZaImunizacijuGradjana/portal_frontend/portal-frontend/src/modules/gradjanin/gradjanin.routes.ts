import { Routes } from "@angular/router";
import { PodnosenjeZahtevaComponent } from "./components/podnosenje-zahteva/podnosenje-zahteva.component";
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
  {
    path: "podnosenje-zahteva",
    pathMatch: "full",
    component: PodnosenjeZahtevaComponent,
  },
];
