import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HotManSharedModule } from '../../shared';
import {
    HorrairesHotpersonelService,
    HorrairesHotpersonelPopupService,
    HorrairesHotpersonelComponent,
    HorrairesHotpersonelDetailComponent,
    HorrairesHotpersonelDialogComponent,
    HorrairesHotpersonelPopupComponent,
    HorrairesHotpersonelDeletePopupComponent,
    HorrairesHotpersonelDeleteDialogComponent,
    horrairesRoute,
    horrairesPopupRoute,
    HorrairesHotpersonelResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...horrairesRoute,
    ...horrairesPopupRoute,
];

@NgModule({
    imports: [
        HotManSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        HorrairesHotpersonelComponent,
        HorrairesHotpersonelDetailComponent,
        HorrairesHotpersonelDialogComponent,
        HorrairesHotpersonelDeleteDialogComponent,
        HorrairesHotpersonelPopupComponent,
        HorrairesHotpersonelDeletePopupComponent,
    ],
    entryComponents: [
        HorrairesHotpersonelComponent,
        HorrairesHotpersonelDialogComponent,
        HorrairesHotpersonelPopupComponent,
        HorrairesHotpersonelDeleteDialogComponent,
        HorrairesHotpersonelDeletePopupComponent,
    ],
    providers: [
        HorrairesHotpersonelService,
        HorrairesHotpersonelPopupService,
        HorrairesHotpersonelResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HotManHorrairesHotpersonelModule {}
