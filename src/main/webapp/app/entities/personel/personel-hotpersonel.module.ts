import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HotManSharedModule } from '../../shared';
import {
    PersonelHotpersonelService,
    PersonelHotpersonelPopupService,
    PersonelHotpersonelComponent,
    PersonelHotpersonelDetailComponent,
    PersonelHotpersonelDialogComponent,
    PersonelHotpersonelPopupComponent,
    PersonelHotpersonelDeletePopupComponent,
    PersonelHotpersonelDeleteDialogComponent,
    personelRoute,
    personelPopupRoute,
    PersonelHotpersonelResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...personelRoute,
    ...personelPopupRoute,
];

@NgModule({
    imports: [
        HotManSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PersonelHotpersonelComponent,
        PersonelHotpersonelDetailComponent,
        PersonelHotpersonelDialogComponent,
        PersonelHotpersonelDeleteDialogComponent,
        PersonelHotpersonelPopupComponent,
        PersonelHotpersonelDeletePopupComponent,
    ],
    entryComponents: [
        PersonelHotpersonelComponent,
        PersonelHotpersonelDialogComponent,
        PersonelHotpersonelPopupComponent,
        PersonelHotpersonelDeleteDialogComponent,
        PersonelHotpersonelDeletePopupComponent,
    ],
    providers: [
        PersonelHotpersonelService,
        PersonelHotpersonelPopupService,
        PersonelHotpersonelResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HotManPersonelHotpersonelModule {}
