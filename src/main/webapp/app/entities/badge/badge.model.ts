import { BaseEntity } from './../../shared';

export class Badge implements BaseEntity {
    constructor(
        public id?: number,
        public numberbadge?: string,
        public matricule?: string,
        public codes?: BaseEntity[],
        public rooms?: BaseEntity,
    ) {
    }
}
