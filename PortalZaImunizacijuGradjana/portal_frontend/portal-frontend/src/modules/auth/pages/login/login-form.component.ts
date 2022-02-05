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
      let data: any = JsonToXML.parse("korisnikPrijavaDto", user, options);
      this.loginForm.reset();
      this.authService.login(data).subscribe(response => {
        this.toastr.success('Successful login!');
        /*let xmlDoc = this.parser.parseFromString(response,"text/xml");
        let token = xmlDoc.getElementsByTagName("authenticationToken")[0].childNodes[0].nodeValue;
        let uloga = xmlDoc.getElementsByTagName("uloga")[0].childNodes[0].nodeValue;
        let email = xmlDoc.getElementsByTagName("email")[0].childNodes[0].nodeValue;*/
        localStorage.setItem('email',response.email);
        /*let imeIprezime = xmlDoc.getElementsByTagName("imeIprezime")[0].childNodes[0].nodeValue;*/
        localStorage.setItem('accessToken', response.authenticationToken);
        localStorage.setItem('uloga', response.uloga);
        localStorage.setItem('imeIprezime', response.imeIprezime);

        if(response.uloga === "G") {//gradjanin
          this.router.navigate(['/gradjanin-homepage']);
        }
        else if(response.uloga === "Z"){//zdravstveni radnik
          this.router.navigate(['/zradnik-homepage']);
        }
      }, error => {
        this.toastr.error(error.error);
      })
    }
  }



 }
