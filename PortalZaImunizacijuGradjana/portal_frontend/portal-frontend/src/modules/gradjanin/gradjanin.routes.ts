import { Routes } from "@angular/router";
import { PodnosenjeZahtevaComponent } from "./components/podnosenje-zahteva/podnosenje-zahteva.component";
import { GradjaninHomepageComponent } from "./pages/gradjanin-homepage/gradjanin-homepage.component";

export const GradjaninRoutes: Routes = [
  {
    path: "homepage",
    pathMatch: "full",
    component: GradjaninHomepageComponent,
  },
  {
    path: "podnosenje-zahteva",
    pathMatch: "full",
    component: PodnosenjeZahtevaComponent,
  },
];
