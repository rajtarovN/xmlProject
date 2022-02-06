import { Component, OnInit, Output } from '@angular/core';
import { EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { BehaviorSubject } from 'rxjs';

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
  ) {}

  ngOnInit(): void {}

  cancel() {
    this.onLogoutClose.emit(true);
  }

  confirm() {
    this.toastr.success('TODO logout!');
    this.router.navigate(['/portal/auth/login']);
  }

}
