import { BaseEntity } from './../../shared';

export class Codebadge implements BaseEntity {
    constructor(
        public id?: number,
        public description?: string,
        public code?: string,
        public badges?: BaseEntity,
    ) {
    }
}
