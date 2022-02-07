import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { AuthenticationService } from 'src/modules/auth/service/authentication-service/authentication.service';

@Component({
  selector: 'app-gradjanin-homepage',
  templateUrl: './gradjanin-homepage.component.html',
  styleUrls: ['./gradjanin-homepage.component.scss']
})
export class GradjaninHomepageComponent implements OnInit {

  constructor(
    private authService: AuthenticationService,
    private toastr: ToastrService,
  ) { }

  ngOnInit(): void {
  }

 
}
