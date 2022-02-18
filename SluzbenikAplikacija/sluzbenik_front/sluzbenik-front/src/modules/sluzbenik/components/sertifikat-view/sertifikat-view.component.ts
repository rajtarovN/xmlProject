import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { SertifikatService } from '../../services/sertifikat-service/sertifikat.service';

@Component({
  selector: 'app-sertifikat-view',
  templateUrl: './sertifikat-view.component.html',
  styleUrls: ['./sertifikat-view.component.scss'],
})
export class SertifikatViewComponent implements OnInit {
  id: string;
  sertifikat: any;

  constructor(
    private route: ActivatedRoute,
    private sertifikatService: SertifikatService,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
  }

  preuzmiPDF(): void {
    this.sertifikatService.getPdf(this.id).subscribe({
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
    this.sertifikatService.getXHtml(this.id).subscribe({
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
    this.sertifikatService.getJSON(this.id).subscribe(
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
    this.sertifikatService.getRDF(this.id).subscribe(
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
