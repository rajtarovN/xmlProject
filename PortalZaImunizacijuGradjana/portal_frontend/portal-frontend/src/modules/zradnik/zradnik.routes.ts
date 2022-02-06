import { Routes } from "@angular/router";
import { ZradnikHomepageComponent } from "./pages/zradnik-homepage/zradnik-homepage.component";

export const ZRadnikRoutes: Routes = [
  {
    path: "homepage",
    pathMatch: "full",
    component: ZradnikHomepageComponent,
  },
];
