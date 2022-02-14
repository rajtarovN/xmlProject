import { Select } from 'src/modules/shared/models/select';

const drzavljanstva: Select[] = [
  {
    value: 'Drzavljanin_republike_srbije',
    viewValue: 'Држављанин Републике Србије',
  },
  {
    value: 'Strani_drzavljanin_sa_boravkom_u_rs',
    viewValue: 'Страни држављанин са боравком у РС',
  },
  {
    value: 'Strani_drzavljanin_bez_boravka_u_rs',
    viewValue: 'Страни држављанин без боравка у РС',
  },
];

const vakcine: Select[] = [
  {
    value: 'Pfizer-BioNTech',
    viewValue: 'Pfizer-BioNTech',
  },
  {
    value: 'Sputnik',
    viewValue: 'Sputnik',
  },
  {
    value: 'Sinopharm',
    viewValue: 'Sinopharm',
  },
  {
    value: 'AstraZeneca',
    viewValue: 'AstraZeneca',
  },
  {
    value: 'Moderna',
    viewValue: 'Moderna',
  },
];

export { drzavljanstva, vakcine };
