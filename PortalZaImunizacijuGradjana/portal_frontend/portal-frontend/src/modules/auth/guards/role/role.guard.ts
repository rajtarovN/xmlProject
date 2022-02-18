import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  Router,
  RouterStateSnapshot,
  UrlTree,
} from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class RoleGuard implements CanActivate {
  constructor(public router: Router) {}

  canActivate(route: ActivatedRouteSnapshot): boolean {
    const expectedRole: string = route.data['expectedRoles'];
    const userType = localStorage.getItem('uloga');

    if (!userType) {
      this.router.navigate(['/portal/auth/login']);
      return false;
    }

    if (userType === expectedRole) {
      return true;
    } else {
      if(userType === "G") {//gradjanin
        this.router.navigate(['/portal/gradjanin/homepage']);
      }
      else if(userType === "Z"){//zdravstveni radnik
        this.router.navigate(['/portal/zradnik/homepage']);
      }
      return false;
    }

    return true;
  }
}
