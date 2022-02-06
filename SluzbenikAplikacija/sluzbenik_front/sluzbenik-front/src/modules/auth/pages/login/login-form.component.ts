import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import {
  FormGroup,
  Validators,
  FormBuilder,
} from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import * as JsonToXML from  'js2xmlparser';
import { AuthenticationService } from '../../service/authentication-service/authentication.service';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.scss'],
})
export class LoginFormComponent implements OnInit {
  loginForm: FormGroup;
  @Output() onLoginClose = new EventEmitter();
  parser = new DOMParser();
  hide = true;

  constructor(
    private fb: FormBuilder,
    public router: Router,
    private toastr: ToastrService,
    private authService: AuthenticationService
  ) {
    this.loginForm = this.fb.group({
      email: [null, Validators.required],
      password: [null, Validators.required],
    });
    
  }

  ngOnInit() {}

  login() {
    if (
      this.loginForm.value.email === null ||
      this.loginForm.value.password === null
    ) {
      this.toastr.error('Sva polja moraju biti popunjena!');
    } else {
      let user = {
        email: this.loginForm.value.email,
        lozinka: this.loginForm.value.password
      };
      const options = {
        declaration: {
          include: false
        }
      };
      let data: any = JsonToXML.parse("korisnikPrijavaDTO", user, options);
      this.loginForm.reset();
      this.toastr.success('TODO logovanje!');
      this.router.navigate(['/sluzbenik/sluzbenik/homepage']);
      /*this.authService.login(data).subscribe(response => {
        this.toastr.success('Uspesno logovanje!');
        localStorage.setItem('email',response.email);
        localStorage.setItem('accessToken', response.authenticationToken);
        localStorage.setItem('uloga', response.uloga);
        localStorage.setItem('imeIprezime', response.imeIprezime);

        if(response.uloga === "S") {//sluzbenik
          this.router.navigate(['/sluzbenik/sluzbenik/homepage']);
        }
      }, error => {
        this.toastr.error(error.error);
      })*/
    }
  }



 }
