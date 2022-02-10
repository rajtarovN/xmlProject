import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { InventoryService } from '../../services/inventory-service/inventory.service';
import * as JsonToXML from 'js2xmlparser';

@Component({
  selector: 'app-inventory-form',
  templateUrl: './inventory-form.component.html',
  styleUrls: ['./inventory-form.component.scss'],
})
export class InventoryFormComponent implements OnInit {
  addInventoryForm!: FormGroup;
  parser = new DOMParser();

  pfizerInv = 0;
  pfizerRes = 0;
  sputnikInv = 0;
  sputnikRes = 0;
  sinopharmInv = 0;
  sinopharmRes = 0;
  astraZenecaInv = 0;
  astraZenecaRes = 0;
  modernaInv = 0;
  modernaRes = 0;

  constructor(
    private fb: FormBuilder,
    private service: InventoryService,
    private snackBar: MatSnackBar
  ) {
    this.initialize();
  }

  ngOnInit(): void {}

  initialize() {
    this.service.getInventory().subscribe({
      next: (success) => {
        let xmlDoc = this.parser.parseFromString(success, 'text/xml');
        let vakcine = xmlDoc.getElementsByTagName('Vakcina');
        for (var i = 0; i < vakcine.length; i++) {
          let vakcina = vakcine[i].children[0].textContent;
          let dostupno = vakcine[i].children[1].textContent;
          let rezervisano = vakcine[i].children[2].textContent;

          if (vakcina === 'Pfizer-BioNTech') {
            if (dostupno != null) this.pfizerInv = parseInt(dostupno);
            if (rezervisano != null) this.pfizerRes = parseInt(rezervisano);
          } else if (vakcina === 'Moderna') {
            if (dostupno != null) this.modernaInv = parseInt(dostupno);
            if (rezervisano != null) this.modernaRes = parseInt(rezervisano);
          } else if (vakcina === 'AstraZeneca') {
            if (dostupno != null) this.astraZenecaInv = parseInt(dostupno);
            if (rezervisano != null)
              this.astraZenecaRes = parseInt(rezervisano);
          } else if (vakcina === 'Sinopharm') {
            if (dostupno != null) this.sinopharmInv = parseInt(dostupno);
            if (rezervisano != null) this.sinopharmRes = parseInt(rezervisano);
          } else if (vakcina === 'Sputnik') {
            if (dostupno != null) this.sputnikInv = parseInt(dostupno);
            if (rezervisano != null) this.sputnikRes = parseInt(rezervisano);
          }
        }

        this.addInventoryForm = this.fb.group({
          pfizer: [this.pfizerInv, Validators.required],
          sputnik: [this.sputnikInv, Validators.required],
          sinopharm: [this.sinopharmInv, Validators.required],
          astra: [this.astraZenecaInv, Validators.required],
          moderna: [this.modernaInv, Validators.required],
        });
      },
      error: (error) => {
        this.addInventoryForm = this.fb.group({
          pfizer: [this.pfizerInv, Validators.required],
          sputnik: [this.sputnikInv, Validators.required],
          sinopharm: [this.sinopharmInv, Validators.required],
          astra: [this.astraZenecaInv, Validators.required],
          moderna: [this.modernaInv, Validators.required],
        });
      },
    });
  }

  send(): void {
    console.log(this.pfizerInv);
    let zalihe = `<?xml version="1.0" encoding="UTF-8"?>
                  <Zalihe xmlns="http://www.ftn.uns.ac.rs/xml_i_veb_servisi/dostupne_vakcine"
                          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                          xsi:schemaLocation="http://www.ftn.uns.ac.rs/xml_i_veb_servisi/odostupne_vakcine file:dostupne_vakcine.xsd">
                    <Vakcina>
                      <Naziv>Pfizer-BioNTech</Naziv>
                      <Dostupno>${this.addInventoryForm.value.pfizer}</Dostupno>
                      <Rezervisano>${this.pfizerRes}</Rezervisano>
                    </Vakcina>
                    <Vakcina>
                      <Naziv>Moderna</Naziv>
                      <Dostupno>${this.addInventoryForm.value.moderna}</Dostupno>
                      <Rezervisano>${this.modernaRes}</Rezervisano>
                    </Vakcina>
                    <Vakcina>
                      <Naziv>AstraZeneca</Naziv>
                      <Dostupno>${this.addInventoryForm.value.astra}</Dostupno>
                      <Rezervisano>${this.astraZenecaRes}</Rezervisano>
                    </Vakcina>
                    <Vakcina>
                      <Naziv>Sinopharm</Naziv>
                      <Dostupno>${this.addInventoryForm.value.sinopharm}</Dostupno>
                      <Rezervisano>${this.sinopharmRes}</Rezervisano>
                    </Vakcina>
                    <Vakcina>
                      <Naziv>Sputnik</Naziv>
                      <Dostupno>${this.addInventoryForm.value.sputnik}</Dostupno>
                      <Rezervisano>${this.sputnikRes}</Rezervisano>
                    </Vakcina>
                  </Zalihe>`;

    console.log(zalihe);

    this.service.updateInventory(zalihe).subscribe({
      next: () => {
        this.openSnackBar('Инвентар успешно ажуриран');
      },
      error: () => {
        this.openSnackBar('Инвентар није ажуриран');
      },
    });
  }

  openSnackBar(message: string): void {
    this.snackBar.open(message, 'Ок', {
      duration: 4000,
    });
  }
}
