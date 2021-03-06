import { BreakpointObserver } from '@angular/cdk/layout';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthenticationService } from 'src/modules/auth/service/authentication-service/authentication.service';

@Component({
  selector: 'app-gradjanin-homepage',
  templateUrl: './gradjanin-homepage.component.html',
  styleUrls: ['./gradjanin-homepage.component.scss'],
})
export class GradjaninHomepageComponent implements OnInit {
  @ViewChild(MatSidenav)
  sidenav!: MatSidenav;
  showModalLogout: boolean;
  mail: string = '';
  activeView = '1';
  selectedTipDokumenta: string;
  showPrikazDokumenata: boolean;

  constructor(
    private observer: BreakpointObserver,
    public router: Router,
    private toastr: ToastrService
  ) {
    this.showModalLogout = false;
    this.mail = localStorage.getItem('email')!;
    this.selectedTipDokumenta = '';
    this.showPrikazDokumenata = false;
  }

  ngOnInit(): void {}

  ngAfterViewInit() {
    this.observer.observe(['(max-width: 800px)']).subscribe((res) => {
      if (res.matches) {
        this.sidenav.mode = 'over';
        this.sidenav.close();
      } else {
        this.sidenav.mode = 'side';
        this.sidenav.open();
      }
    });
  }

  onLogoutButtonClicked() {
    this.showModalLogout = true;
  }

  onLogoutCloseClicked(item: boolean) {
    this.showModalLogout = false;
  }


  openSertifikat(){
    console.log("aaaaa")
    this.selectedTipDokumenta = "Sertifikati";
    this.showPrikazDokumenata = true;
  }
  openSaglasnost(){
    this.selectedTipDokumenta = "Saglasnosti";
    this.showPrikazDokumenata = true;
  }
  openPotvrda(){
    this.selectedTipDokumenta = "Potvrde";
    this.showPrikazDokumenata = true;
  }
  openInteresovanja(){
    this.selectedTipDokumenta = "Interesovanje";
    this.showPrikazDokumenata = true;
  }
}
