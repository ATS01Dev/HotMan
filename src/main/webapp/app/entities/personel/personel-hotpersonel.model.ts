import { BaseEntity } from './../../shared';

const enum Sexe {
    'HOMME',
    'FEMME'
}

export class PersonelHotpersonel implements BaseEntity {
    constructor(
        public id?: number,
        public fistname?: string,
        public lastname?: string,
        public sexe?: Sexe,
        public fonction?: BaseEntity,
        public horraires?: BaseEntity[],
    ) {
    }
}
