import { Routes } from "@angular/router";
import { SluzbenikHomepageComponent } from "./pages/sluzbenik-homepage/sluzbenik-homepage.component";

export const SluzbenikRoutes: Routes = [
  {
    path: "homepage",
    pathMatch: "full",
    component: SluzbenikHomepageComponent,
  },
];
