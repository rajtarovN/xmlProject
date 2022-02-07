import { Component, OnInit, Output } from '@angular/core';
import { EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { BehaviorSubject } from 'rxjs';
import { AuthenticationService } from 'src/modules/auth/service/authentication-service/authentication.service';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.scss'],
})
export class LogoutComponent implements OnInit {
  @Output() onLogoutClose = new EventEmitter();

  constructor(
    private toastr: ToastrService,
    public router: Router,
    private authService: AuthenticationService
  ) {}

  ngOnInit(): void {}

  cancel() {
    this.onLogoutClose.emit(true);
  }

  confirm() {
    this.authService.logout().subscribe(response => {
      localStorage.removeItem('email');
      localStorage.removeItem('accessToken');
      localStorage.removeItem('uloga');
      localStorage.removeItem('imeIprezime');
      this.router.navigate(['/portal/auth/login']);
      this.toastr.success('Uspesan logout!');
      
    }, error => {
      this.toastr.error(error.error);
    })
  }

}
