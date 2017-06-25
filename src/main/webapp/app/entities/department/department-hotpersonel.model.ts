import { BaseEntity } from './../../shared';

export class DepartmentHotpersonel implements BaseEntity {
    constructor(
        public id?: number,
        public namedepartement?: string,
        public description?: string,
    ) {
    }
}
