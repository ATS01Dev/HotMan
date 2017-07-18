import { BaseEntity } from './../../shared';

export class Reservation implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public evenement?: string,
        public date_Revservation?: any,
        public date_debut?: any,
        public date_fin?: any,
        public duree?: number,
        public rooms?: BaseEntity,
    ) {
    }
}
