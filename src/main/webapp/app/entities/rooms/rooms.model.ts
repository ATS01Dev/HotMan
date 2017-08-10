import { BaseEntity } from './../../shared';

const enum Typedroom {
    'VIP',
    'SIMPLE',
    'MOYEN'
}

const enum Etatromms {
    'DISPONIPLE',
    'OCCUPE',
    'RESERVATION'
}

export class Rooms implements BaseEntity {
    constructor(
        public id?: number,
        public romsnumber?: string,
        public typeroom?: Typedroom,
        public etat?: Etatromms,
        public badge?: BaseEntity,
        public client?: BaseEntity,
    ) {
    }
}
