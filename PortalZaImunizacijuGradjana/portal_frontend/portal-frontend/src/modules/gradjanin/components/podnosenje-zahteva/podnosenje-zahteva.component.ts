import { AfterViewInit, Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { PodnosenjeZahtevaService } from '../../services/podnosenje-zahteva.service';
declare const Xonomy: any;

@Component({
  selector: 'app-podnosenje-zahteva',
  templateUrl: './podnosenje-zahteva.component.html',
  styleUrls: ['./podnosenje-zahteva.component.scss']
})
export class PodnosenjeZahtevaComponent implements OnInit, AfterViewInit {

  constructor(
    private router: Router,
    private zahtevService: PodnosenjeZahtevaService,
    private snackBar: MatSnackBar,
  ) { }

  ngOnInit(): void {
  }

  ngAfterViewInit(): void {
    //prikaz xonomy editora
    let element = document.getElementById("editor");
    let xmlStr = this.zahtevService.xmlString;
    let specification = this.zahtevService.obavestenjeSpecifikacija;
    Xonomy.render(xmlStr, element, specification);
  }

  send(): void {
    let text: string = Xonomy.harvest();
    // console.log(text);  // ispis xml-a koji se salje

    this.zahtevService.addNew(text)
      .subscribe(
        response => {
          console.log ("jej")
         // this.openSnackBar("Uspesno ste kreirali zahtev");
          //this.router.navigate(['/homepage/i']);
        },
        error =>{
          console.log(error)
          //this.openSnackBar("Niste uspesno ste kreirali zahtev");
          this.router.navigate(['/homepage/i']);
        }
      );

  }

  openSnackBar(message: string): void{
    this.snackBar.open(message, 'Dismiss', {
      duration: 4000,
    });
  }

}