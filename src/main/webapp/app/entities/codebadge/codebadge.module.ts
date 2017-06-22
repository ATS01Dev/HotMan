import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HotManSharedModule } from '../../shared';
import {
    CodebadgeService,
    CodebadgePopupService,
    CodebadgeComponent,
    CodebadgeDetailComponent,
    CodebadgeDialogComponent,
    CodebadgePopupComponent,
    CodebadgeDeletePopupComponent,
    CodebadgeDeleteDialogComponent,
    codebadgeRoute,
    codebadgePopupRoute,
} from './';

const ENTITY_STATES = [
    ...codebadgeRoute,
    ...codebadgePopupRoute,
];

@NgModule({
    imports: [
        HotManSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CodebadgeComponent,
        CodebadgeDetailComponent,
        CodebadgeDialogComponent,
        CodebadgeDeleteDialogComponent,
        CodebadgePopupComponent,
        CodebadgeDeletePopupComponent,
    ],
    entryComponents: [
        CodebadgeComponent,
        CodebadgeDialogComponent,
        CodebadgePopupComponent,
        CodebadgeDeleteDialogComponent,
        CodebadgeDeletePopupComponent,
    ],
    providers: [
        CodebadgeService,
        CodebadgePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HotManCodebadgeModule {}
