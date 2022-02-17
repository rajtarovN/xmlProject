// This file can be replaced during build by using the `fileReplacements` array.
// `ng build` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  baseUrl: 'http://localhost:8082/api',
  login: 'korisnik/prijava',
  logout: 'korisnik/logout',
  getKorisnike: 'korisnik/listaKorisnika',
  getZahteve: 'zahtev/listaZahteva',
  getSaglasnosti: 'saglasnost/getAllXmlByEmail',
  saglasnostPdf: 'saglasnost/generatePDF',
  getPotvrde: 'potvrda/getAllXmlByEmail',
  potvrdaPdf: 'potvrda/generatePDF',
  odbijZahtev: 'zahtev/odbijZahtev',
  odobriZahtev: 'zahtev/odobriZahtev',
  potvrdaXhtml: 'potvrda/generateHTML',
  saglasnostXhtml: 'saglasnost/generateHTML',
  getSertifikate: 'sertifikat/getAllXmlByEmail',
  sertifikatPdf: 'sertifikat/generatePDF',
  obicnaPretragaSagl: 'saglasnost/obicnaPretraga',
  obicnaPretragaSertif: 'sertifikat/obicnaPretraga',
  obicnaPretragaPotvrda: 'potvrda/obicnaPretraga',  
  sertifikatXhtml: 'sertifikat/generateHTML',

};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/plugins/zone-error';  // Included with Angular CLI.
