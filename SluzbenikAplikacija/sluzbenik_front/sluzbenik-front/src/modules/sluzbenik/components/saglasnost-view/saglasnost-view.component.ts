import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { SaglasnostService } from '../../services/saglasnost-service/saglasnost.service';

@Component({
  selector: 'app-saglasnost-view',
  templateUrl: './saglasnost-view.component.html',
  styleUrls: ['./saglasnost-view.component.scss'],
})
export class SaglasnostViewComponent implements OnInit {
  id: string;
  saglasnost: any;

  constructor(
    private route: ActivatedRoute,
    private saglasnostService: SaglasnostService,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
  }

  preuzmiPDF(): void {
    this.saglasnostService.getPdf(this.id).subscribe({
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
    this.saglasnostService.getXHtml(this.id).subscribe({
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
    this.saglasnostService.getJSON(this.id).subscribe(
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
    this.saglasnostService.getRDF(this.id).subscribe(
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
