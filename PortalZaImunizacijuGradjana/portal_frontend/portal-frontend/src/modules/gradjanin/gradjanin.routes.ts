import { Routes } from "@angular/router";
import { GradjaninHomepageComponent } from "./pages/gradjanin-homepage/gradjanin-homepage.component";

export const GradjaninRoutes: Routes = [
  {
    path: "homepage",
    pathMatch: "full",
    component: GradjaninHomepageComponent,
  },
];
