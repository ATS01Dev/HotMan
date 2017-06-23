import { BaseEntity } from './../../shared';

export class FonctionHotpersonel implements BaseEntity {
    constructor(
        public id?: number,
        public poste?: string,
        public stardate?: any,
        public typecontrat?: string,
        public durrecontrat?: string,
        public datenaissane?: any,
        public phonenumber?: string,
        public email?: string,
        public salary?: number,
        public department?: BaseEntity,
    ) {
    }
}
