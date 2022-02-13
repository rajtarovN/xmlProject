import { LiveAnnouncer } from '@angular/cdk/a11y';
import { Component, EventEmitter, OnInit, Output, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort, Sort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import * as txml from 'txml';
import { AuthenticationService } from 'src/modules/auth/service/authentication-service/authentication.service';
import { User } from 'src/modules/shared/models/user';

@Component({
  selector: 'app-registar-gradjana',
  templateUrl: './registar-gradjana.component.html',
  styleUrls: ['./registar-gradjana.component.scss']
})
export class RegistarGradjanaComponent implements OnInit {
  data: any[];
  dataSource!: MatTableDataSource<any>;
  searchForm: FormGroup;
  searchString: string;
  @ViewChild(MatSort, { static: true }) sort!: MatSort;
  @ViewChild(MatPaginator, { static: true }) paginator!: MatPaginator;
  parser = new DOMParser();
  showEvidenciju: boolean;
  showPotvrdu: boolean;
  selectedEmail: string;
  selectedBrojSaglasnosti: string;

  displayedColumns: string[] = [
    'Email',
    'Ime',
    'Prezime',
    'Saglasnosti',
    'Potvrde',
    'Sertifikati',
  ];

  constructor(
    private fb: FormBuilder,
    public router: Router,
    private toastr: ToastrService,
    private liveAnnouncer: LiveAnnouncer,
    private authService: AuthenticationService
  ) {
    this.data = [];
    this.searchForm = this.fb.group({
      search: [null],
    });
    this.searchString = '';
    this.showEvidenciju = false;
    this.showPotvrdu = false;
    this.selectedEmail = '';
    this.selectedBrojSaglasnosti = '';
  }

  ngOnInit(): void {
    this.getData();
  }

  getData() {
    this.authService.getKorisnike('all').subscribe(
      (response) => {
        
          let obj: any = txml.parse(response);
          let list: Array<User> = [];
          obj[0].children.forEach((element: any) => {
            const temp: User = {
              email: String(element.children[0].children[0]),
              ime: String(element.children[1].children[0]),
              prezime: String(element.children[2].children[0])
            };
            list.push(temp);
            
          });
          this.setData(list);
        
      },
      (error) => {
        this.toastr.error(error.error);
      }
    );
  }

  search() {
    this.searchString =
      this.searchForm.value.search != null
        ? this.searchForm.value.search
        : 'all';
    this.authService
      .getKorisnike(this.searchString)
      .subscribe(
        (response) => {
            let obj: any = txml.parse(response);
            let list: Array<User> = [];
            obj[0].children.forEach((element: any) => {
              const temp: User = {
                email: String(element.children[0].children[0]),
                ime: String(element.children[1].children[0]),
                prezime: String(element.children[2].children[0])
              };
              list.push(temp);
              this.data.push(temp);
            });
            this.setData(list);
          
        },
        (error) => {
          this.toastr.error(error.error);
        }
      );
  }

  setData(data: any[]) {
    this.data = data;
    this.dataSource = new MatTableDataSource<any>(data);
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  announceSortChange(sortState: Sort) {
    if (sortState.direction) {
      this.liveAnnouncer.announce(`Sorted ${sortState.direction}ending`);
    } else {
      this.liveAnnouncer.announce('Sorting cleared');
    }
  }

  openSaglasnosti(email: string){}

  openPotvrde(email: string){}

  openSertifikate(email: string){}
}
