import { AfterViewInit, Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { PodnosenjeZahtevaService } from '../../services/podnosenje-zahteva.service';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
declare const Xonomy: any;

@Component({
  selector: 'app-podnosenje-zahteva',
  templateUrl: './podnosenje-zahteva.component.html',
  styleUrls: ['./podnosenje-zahteva.component.scss']
})
export class PodnosenjeZahtevaComponent implements OnInit, AfterViewInit {
  ime: string="";
  prezime: string="";
  datumRodjenja: string="";
  pol: string="";
  vreme: string="";
  gmail: string="";
  datumPodnosenjaZhteva: string="";
  addZahtevForm: FormGroup;
  vreme2: string="";

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private zahtevService: PodnosenjeZahtevaService,
    private snackBar: MatSnackBar,
  ) {
    this.addZahtevForm = this.fb.group({

      JMBG: [
        null,
        [
          Validators.required,
          Validators.minLength(13),
          Validators.maxLength(13),
          Validators.pattern('[0-9]{13}'),
        ],
      ],
      brPasosa: [
        null,
        [
          Validators.required,
          Validators.required,
          Validators.minLength(9),
          Validators.maxLength(9),
          Validators.pattern('[0-9]{9}'),
        ]
      ],
      mesto: [
        null,
        [
          Validators.required,
        ]
      ]
    });
      this.gmail = localStorage.getItem("email")!;
      this.ime = localStorage.getItem("ime")!;
      this.prezime = localStorage.getItem("prezime")!;
      this.datumRodjenja = localStorage.getItem("rodjendan")!;
      this.pol = localStorage.getItem("pol")==="M" ? 'Musko' : 'Zensko';
      const danasnjiDan = new Date();
      if((danasnjiDan.getMonth()+1)<10){
        this.datumPodnosenjaZhteva = danasnjiDan.getFullYear() + "-0" + (danasnjiDan.getMonth()+1) + "-" +  danasnjiDan.getDate();
      }else{
      this.datumPodnosenjaZhteva = danasnjiDan.getFullYear() + "-" + (danasnjiDan.getMonth()+1) + "-" +  danasnjiDan.getDate();}
      this.vreme = "-"+danasnjiDan.getHours() + "-" + danasnjiDan.getMinutes() + "-" + danasnjiDan.getSeconds();
      let minuti =danasnjiDan.getMinutes()+"";
      let sati =danasnjiDan.getHours()+"";
      let sekunde =danasnjiDan.getSeconds()+"";
      if(danasnjiDan.getMinutes()<10)
      {
        minuti = "0"+danasnjiDan.getMinutes();
      }
      if(danasnjiDan.getSeconds()<10)
      {
        sekunde = "0"+danasnjiDan.getSeconds();
      }
      if(danasnjiDan.getHours()<10)
      {
        sati = "0"+danasnjiDan.getHours();
      }
      this.vreme2 = "T"+sati + ":" + minuti + ":" + sekunde;
   
    }

  ngOnInit(): void {
  }

  cancel(): void {
    this.addZahtevForm.reset();
  }
  ngAfterViewInit(): void {
    //prikaz xonomy editora
    let element = document.getElementById("editor");
    let xmlStr = this.zahtevService.getXmlString();
    let specification = this.zahtevService.obavestenjeSpecifikacija;
    Xonomy.render(xmlStr, element, specification);
  }

  send(): void {
    let text: string = Xonomy.harvest();
    if(text.split(">")[1].split("<")[0].trim() !==""){
      const xmlZahtev = this.getXml(text);
      this.zahtevService.addNew(xmlZahtev, this.addZahtevForm.value.JMBG, (this.datumPodnosenjaZhteva+this.vreme))
        .subscribe(
          response => {
            this.openSnackBar("Uspesno ste kreirali zahtev");
            //this.router.navigate(['/homepage/i']);
          },
          error =>{
            console.log(error)
            this.openSnackBar("Niste uspesno ste kreirali zahtev");
            this.router.navigate(['/homepage/i']);
          }
        );
    }else{
      this.openSnackBar("Potrebno je da unesete razlog.");
    }

  }

  openSnackBar(message: string): void{
    this.snackBar.open(message, 'Dismiss', {
      duration: 4000,
    });
  }

  getXml(razlog: string){
    return `
      <Zahtev_za_zeleni_sertifikat xmlns="http://www.ftn.uns.ac.rs/xml_i_veb_servisi/zahtev_za_sertifikatom"
          xmlns:xsi="http://www.ftn.uns.ac.rs/xml_i_veb_servisi/zahtev_za_sertifikatom"
          about="http://www.ftn.uns.ac.rs/xml_i_veb_servisi/zahtev_za_sertifikatom/${this.addZahtevForm.value.JMBG}/${this.datumPodnosenjaZhteva+this.vreme}"
          xmlns:pred="http://www.ftn.uns.ac.rs/xml_i_veb_servisi/rdf/zahtev_za_sertifikatom/predicate/"
          email="${this.gmail}"
          xsi:schemaLocation="http://www.ftn.uns.ac.rs/xml_i_veb_servisi/zahtev_za_sertifikatom ../schema/zahtev_sema.xsd">
          <Podnosilac_zahteva>
              <Ime property="pred:ime">${this.ime}</Ime>
              <Prezime property="pred:prezime">${this.prezime}</Prezime>
              <Datum_rodjenja>${this.datumRodjenja}</Datum_rodjenja>
              <Pol>${this.pol}</Pol>
              <Jmbg property="pred:jmbg">${this.addZahtevForm.value.JMBG}</Jmbg>
              <Broj_pasosa property="pred:broj_pasosa">${this.addZahtevForm.value.brPasosa}</Broj_pasosa>`
              + razlog + 
          `</Podnosilac_zahteva>
          <Zaglavlje>
              <Mesto_podnosenja_zahteva>${this.addZahtevForm.value.mesto}</Mesto_podnosenja_zahteva>
              <Dan_podnosenja_zahteva property="pred:dan_podnosenja_zahteva">${this.datumPodnosenjaZhteva+this.vreme2}</Dan_podnosenja_zahteva>
          </Zaglavlje>
          <Status property="pred:status">na cekanju</Status>
      </Zahtev_za_zeleni_sertifikat>`;
  }
  

}