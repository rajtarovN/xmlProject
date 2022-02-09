import { BreakpointObserver } from '@angular/cdk/layout';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-sluzbenik-homepage',
  templateUrl: './sluzbenik-homepage.component.html',
  styleUrls: ['./sluzbenik-homepage.component.scss'],
})
export class SluzbenikHomepageComponent implements OnInit {
  @ViewChild(MatSidenav)
  sidenav!: MatSidenav;
  showModalLogout: boolean;
  activeView = '3';

  constructor(
    private observer: BreakpointObserver,
    public router: Router,
    private toastr: ToastrService
  ) {
    this.showModalLogout = false;
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

  onRegistarGradjanaClicked() {}

  onIzvestajOImunizacijiClicked() {}

  onDostupnevakcineClicked() {}

  onArhivaClicked() {}
}
