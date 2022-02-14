import { Select } from 'src/modules/shared/models/select';

const radniStatusSelect: Select[] = [
  {
    value: 'Zaposlen',
    viewValue: 'запослен',
  },
  {
    value: 'Nezaposlen',
    viewValue: 'незапослен',
  },
  {
    value: 'Penzioner',
    viewValue: 'пензионер',
  },
  {
    value: 'ucenik',
    viewValue: 'ученик',
  },
  {
    value: 'student',
    viewValue: 'студент',
  },
  {
    value: 'dete',
    viewValue: 'дете',
  },
];

const zanimanjeZaposlenogSelect: Select[] = [
  {
    value: 'zdravstvena zastita',
    viewValue: 'здравствена заштита',
  },
  {
    value: 'socijalna zastita',
    viewValue: 'социјална заштита',
  },
  {
    value: 'prosveta',
    viewValue: 'просвета',
  },
  {
    value: 'MUP',
    viewValue: 'МУП',
  },
  {
    value: 'vojska RS',
    viewValue: 'Војска РС',
  },
  {
    value: 'drugo',
    viewValue: 'друго',
  },
];
const drzavljanstvoSelect: Select[] = [
  {
    value: '1',
    viewValue: 'Република Србија',
  },
  {
    value: '2',
    viewValue: 'Страно државлјанство',
  },
];

export { radniStatusSelect, zanimanjeZaposlenogSelect, drzavljanstvoSelect };
