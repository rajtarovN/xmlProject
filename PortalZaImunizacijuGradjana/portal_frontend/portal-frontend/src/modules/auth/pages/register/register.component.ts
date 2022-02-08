import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthenticationService } from '../../service/authentication-service/authentication.service';
import * as JsonToXML from 'js2xmlparser';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent implements OnInit {
  loginForm: FormGroup;
  parser = new DOMParser();
  hide = true;

  constructor(
    private fb: FormBuilder,
    public router: Router,
    private toastr: ToastrService,
    private authService: AuthenticationService
  ) {
    this.loginForm = this.fb.group({
      name: [null, Validators.required],
      surname: [null, Validators.required],
      gender: [null, Validators.required],
      birthday: [null, Validators.required],
      email: [null, Validators.required],
      password: [null, Validators.required],
    });
  }

  ngOnInit() {}

  register() {
    if (
      this.loginForm.value.name === null ||
      this.loginForm.value.email === null ||
      this.loginForm.value.password === null
    ) {
      this.toastr.error('Sva polja moraju biti popunjena!');
    } else {
      let user = {
        ime: this.loginForm.value.name,
        prezime: this.loginForm.value.surname,
        pol: this.loginForm.value.gender,
        rodjendan: this.loginForm.value.birthday,
        email: this.loginForm.value.email,
        lozinka: this.loginForm.value.password,
      };
      const options = {
        declaration: {
          include: false,
        },
      };
      let data: any = JsonToXML.parse('korisnikRegistracijaDTO', user, options);
      this.loginForm.reset();
      this.authService.register(data).subscribe(
        (response) => {
          this.toastr.success('Uspesno kreiran novi nalog!');
          this.router.navigate(['/portal/auth/login']);
        },
        (error) => {
          this.toastr.error(error.error);
        }
      );
    }
  }
}
