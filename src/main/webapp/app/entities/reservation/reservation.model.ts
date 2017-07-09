import { BaseEntity } from './../../shared';

export class Reservation implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public evenement?: string,
        public date?: any,
        public debut?: any,
        public fin?: any,
        public duree?: number,
        public rooms?: BaseEntity,
    ) {
    }
}
