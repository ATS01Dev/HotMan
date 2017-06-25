import { BaseEntity } from './../../shared';

export class Client implements BaseEntity {
    constructor(
        public id?: number,
        public fistname?: string,
        public lastname?: string,
        public cartnumenber?: string,
        public telephone?: string,
        public email?: string,
        public datecome?: any,
        public datego?: any,
        public duration?: number,
        public reservation?: boolean,
        public room?: BaseEntity,
        public groupe?: BaseEntity,
    ) {
        this.reservation = false;
    }
}
