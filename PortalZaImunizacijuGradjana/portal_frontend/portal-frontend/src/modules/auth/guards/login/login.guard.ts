import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { AuthenticationService } from '../../service/authentication-service/authentication.service';

@Injectable({
  providedIn: 'root'
})
export class LoginGuard implements CanActivate {
  constructor(public auth: AuthenticationService, public router: Router) {}

  canActivate(): boolean {

    if (this.auth.isLoggedIn()) {
      const userType = localStorage.getItem("uloga");;

      if(userType === "G") {//gradjanin
        this.router.navigate(['/gradjanin-homepage']);
      }
      else if(userType === "Z"){//zdravstveni radnik
        this.router.navigate(['/zradnik-homepage']);
      }

      return false;
    }
    return true;
  }
  
}
