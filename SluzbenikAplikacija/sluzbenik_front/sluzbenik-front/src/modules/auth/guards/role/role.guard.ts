import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  Router,
  RouterStateSnapshot,
  UrlTree,
} from '@angular/router';
import { AuthenticationService } from '../../service/authentication-service/authentication.service';

@Injectable({
  providedIn: 'root',
})
export class RoleGuard implements CanActivate {
  constructor(public auth: AuthenticationService, public router: Router) {}

  canActivate(route: ActivatedRouteSnapshot): boolean {
    const expectedRole: string = route.data['expectedRoles'];
    const token = localStorage.getItem('currentUser');

    if (!token) {
      this.router.navigate(['/login']);
      return false;
    }

    const user: {
      token: string;
      expiresIn: number;
      username: string;
      userType: string;
    } = JSON.parse(token);
    const userType = user.userType;

    if (userType === expectedRole) {
      return true;
    } else {
      if (userType === 'S') {
        this.router.navigate(['/sluzbenik/homepage']);
      }
      return false;
    }

    return true;
  }
}
