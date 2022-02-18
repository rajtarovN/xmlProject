import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { PotvrdeService } from '../../services/potvrde-service/potvrde.service';

@Component({
  selector: 'app-potvrda-view',
  templateUrl: './potvrda-view.component.html',
  styleUrls: ['./potvrda-view.component.scss'],
})
export class PotvrdaViewComponent implements OnInit {
  id: string;
  potvrda: any;

  constructor(
    private route: ActivatedRoute,
    private potvrdaService: PotvrdeService,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
  }

  preuzmiPDF(): void {
    this.potvrdaService.getPdf(this.id).subscribe({
      next: (response) => {
        let file = new Blob([response], { type: 'application/pdf' });
        var fileURL = URL.createObjectURL(file);

        let a = document.createElement('a');
        document.body.appendChild(a);
        a.setAttribute('style', 'display: none');
        a.href = fileURL;
        a.download = `${this.id}.pdf`;
        a.click();
        window.URL.revokeObjectURL(fileURL);
        a.remove();
      },
      error: (error) => {
        this.toastr.error(error.error);
      },
    });
  }

  preuzmiXHTML(): void {
    this.potvrdaService.getXHtml(this.id).subscribe({
      next: (response) => {
        let file = new Blob([response], { type: 'text/html' });
        var fileURL = URL.createObjectURL(file);

        let a = document.createElement('a');
        document.body.appendChild(a);
        a.setAttribute('style', 'display: none');
        a.href = fileURL;
        a.download = `${this.id}.html`;
        a.click();
        window.URL.revokeObjectURL(fileURL);
        a.remove();
      },
      error: (error) => {
        this.toastr.error(error.error);
      },
    });
  }

  doJsonRdf(response: any, documentNameId: string, typee: string) {
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

  extractMetadataAsJSON() {
    this.potvrdaService.getJSON(this.id).subscribe(
      (response) => {
        this.doJsonRdf(
          response,
          `saglasnost_${this.id}.json`,
          'application/json'
        );
      },
      (error) => {
        this.toastr.error(error.error);
      }
    );
  }

  extractMetadataAsRDF() {
    this.potvrdaService.getRDF(this.id).subscribe(
      (response) => {
        this.doJsonRdf(
          response,
          `saglasnost_${this.id}.rdf`,
          'application/pdf'
        );
      },
      (error) => {
        this.toastr.error(error.error);
      }
    );
  }
}
