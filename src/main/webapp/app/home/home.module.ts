import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HotManSharedModule } from '../shared';

import { HOME_ROUTE, HomeComponent } from './';
import { BilanComponent } from '../bilan/bilan.component';
import { StatModule } from '../shared/modules/stat/stat.module';

@NgModule({
    imports: [
        HotManSharedModule,
        StatModule,
        RouterModule.forRoot([ HOME_ROUTE ], { useHash: true })
    ],
    declarations: [
        HomeComponent,
        BilanComponent,
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HotManHomeModule {}
