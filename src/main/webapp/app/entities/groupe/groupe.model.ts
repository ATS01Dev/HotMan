import { BaseEntity } from './../../shared';

export class Groupe implements BaseEntity {
    constructor(
        public id?: number,
        public namegroupe?: string,
        public nbperson?: string,
        public clients?: BaseEntity[],
    ) {
    }
}
