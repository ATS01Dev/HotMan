import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HotManSharedModule } from '../../shared';
import {
    FonctionHotpersonelService,
    FonctionHotpersonelPopupService,
    FonctionHotpersonelComponent,
    FonctionHotpersonelDetailComponent,
    FonctionHotpersonelDialogComponent,
    FonctionHotpersonelPopupComponent,
    FonctionHotpersonelDeletePopupComponent,
    FonctionHotpersonelDeleteDialogComponent,
    fonctionRoute,
    fonctionPopupRoute,
    FonctionHotpersonelResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...fonctionRoute,
    ...fonctionPopupRoute,
];

@NgModule({
    imports: [
        HotManSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        FonctionHotpersonelComponent,
        FonctionHotpersonelDetailComponent,
        FonctionHotpersonelDialogComponent,
        FonctionHotpersonelDeleteDialogComponent,
        FonctionHotpersonelPopupComponent,
        FonctionHotpersonelDeletePopupComponent,
    ],
    entryComponents: [
        FonctionHotpersonelComponent,
        FonctionHotpersonelDialogComponent,
        FonctionHotpersonelPopupComponent,
        FonctionHotpersonelDeleteDialogComponent,
        FonctionHotpersonelDeletePopupComponent,
    ],
    providers: [
        FonctionHotpersonelService,
        FonctionHotpersonelPopupService,
        FonctionHotpersonelResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HotManFonctionHotpersonelModule {}
