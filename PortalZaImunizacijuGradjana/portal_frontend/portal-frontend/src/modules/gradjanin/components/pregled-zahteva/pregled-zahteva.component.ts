import { Component, OnInit } from '@angular/core';
import { PodnosenjeZahtevaService } from '../../services/podnosenje-zahteva.service';

@Component({
  selector: 'app-pregled-zahteva',
  templateUrl: './pregled-zahteva.component.html',
  styleUrls: ['./pregled-zahteva.component.scss']
})
export class PregledZahtevaComponent implements OnInit {

  ime: string="";
  prezime: string="";
  datumRodjenja: string="";
  pol: string="";
  vreme: string="";
  gmail: string="";
  datumPodnosenjaZhteva: string="";
  jmbg: string="";
  brPasosa: string="";
  mesto: string="";
  razlog: string="";
  id="";

  constructor(
    private zahtevService: PodnosenjeZahtevaService,
  ) { 
    
  }

  ngOnInit(): void {
  }

  onSubmit(){
    console.log("aaa")
    this.zahtevService.getZahtev(this.jmbg).subscribe(
      (response) => {
        console.log(response);
      },
      error =>{
        console.log(error.error.text);
        this.setText(error.error.text);
      }
      
    )
  }

  setText(text: string){
    console.log(text.split(">"));
    const first_part = text.split(">");
    this.id = first_part[2].split("za_sertifikatom/")[1].split('\"')[0];
    this.id = "zahtev_"+ this.id.split("/")[0]+"_" + this.id.split("/")[1]+".xml";
    this.ime = first_part[5].split("<")[0];
    this.prezime = first_part[7].split("<")[0]
    this.datumRodjenja = first_part[9].split("<")[0]
    this.pol = first_part[11].split("<")[0]
    this.brPasosa = first_part[13].split("<")[0]
    this.razlog = first_part[17].split("<")[0]
    this.mesto = first_part[21].split("<")[0]
    this.datumPodnosenjaZhteva = first_part[23].split("<")[0]
  }

  getPdf() {
    this.zahtevService.getPdf(this.id).subscribe(
      data => {
        let file = new Blob([data], { type: 'application/pdf' });
        var fileURL = URL.createObjectURL(file);

        let a = document.createElement('a');
        document.body.appendChild(a);
        a.setAttribute('style', 'display: none');
        a.href = fileURL;
        a.download = `${this.id}.pdf`;
        a.click();
        window.URL.revokeObjectURL(fileURL);
        a.remove();
      }
    );
  }

  getHtml() {
    
    this.zahtevService.getHtml(this.id).subscribe(
      data => {
        let file = new Blob([data], { type: 'text/html' });
        var fileURL = URL.createObjectURL(file);

        let a = document.createElement('a');
        document.body.appendChild(a);
        a.setAttribute('style', 'display: none');
        a.href = fileURL;
        a.download = `${this.id}.html`;
        a.click();
        window.URL.revokeObjectURL(fileURL);
        a.remove();
      }
    );
  }

}