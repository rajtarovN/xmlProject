import { Component, OnInit } from '@angular/core';
import { PodnosenjeZahtevaService } from '../../services/podnosenje-zahteva.service';
import { ToastrService } from 'ngx-toastr'
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
    private toastr: ToastrService,
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
    try{
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
  }catch(Exception){
    this.toastr.error("Ne postoji zahtev sa tim jmbg-om")
  }
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

  getJSON(){
    this.zahtevService.getHtml(this.id).subscribe(
      data => {
        this.doJsonRdf(data, `zahtev_${this.id}.json`, 'application/json');
      },
      (error) => {
        this.toastr.error(error.error);
      }
    );
  }

  getRDF(){
    this.zahtevService.getHtml(this.id).subscribe(
      data => {
        this.doJsonRdf(data, `zahtev_${this.id}.rdf`, 'application/pdf');
      },
      (error) => {
        this.toastr.error(error.error);
      }
    );
  }


  doJsonRdf(response: any, documentNameId: string, typee: string){
    let file = new Blob([response], { type: typee });
    var fileURL = URL.createObjectURL(file);
    let a = document.createElement('a');
    document.body.appendChild(a);
    a.setAttribute('style', 'display: none');
    a.href = fileURL;
    a.download = documentNameId;
    a.click();
    window.URL.revokeObjectURL(fileURL);
    a.remove();
  }
}
