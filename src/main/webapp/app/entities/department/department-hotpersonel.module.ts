import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HotManSharedModule } from '../../shared';
import {
    DepartmentHotpersonelService,
    DepartmentHotpersonelPopupService,
    DepartmentHotpersonelComponent,
    DepartmentHotpersonelDetailComponent,
    DepartmentHotpersonelDialogComponent,
    DepartmentHotpersonelPopupComponent,
    DepartmentHotpersonelDeletePopupComponent,
    DepartmentHotpersonelDeleteDialogComponent,
    departmentRoute,
    departmentPopupRoute,
} from './';

const ENTITY_STATES = [
    ...departmentRoute,
    ...departmentPopupRoute,
];

@NgModule({
    imports: [
        HotManSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        DepartmentHotpersonelComponent,
        DepartmentHotpersonelDetailComponent,
        DepartmentHotpersonelDialogComponent,
        DepartmentHotpersonelDeleteDialogComponent,
        DepartmentHotpersonelPopupComponent,
        DepartmentHotpersonelDeletePopupComponent,
    ],
    entryComponents: [
        DepartmentHotpersonelComponent,
        DepartmentHotpersonelDialogComponent,
        DepartmentHotpersonelPopupComponent,
        DepartmentHotpersonelDeleteDialogComponent,
        DepartmentHotpersonelDeletePopupComponent,
    ],
    providers: [
        DepartmentHotpersonelService,
        DepartmentHotpersonelPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HotManDepartmentHotpersonelModule {}
