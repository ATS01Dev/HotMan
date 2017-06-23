import { BaseEntity } from './../../shared';

export class HorrairesHotpersonel implements BaseEntity {
    constructor(
        public id?: number,
        public startDate?: any,
        public endDate?: any,
        public description?: string,
        public personel?: BaseEntity,
    ) {
    }
}
