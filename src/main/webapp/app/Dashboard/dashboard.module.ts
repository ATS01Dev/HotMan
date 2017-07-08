
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HotManSharedModule } from '../shared';
import { PierChartComponent } from './pier-chart/pier-chart.component';

// import { HOME_ROUTE, HomeComponent } from './';

@NgModule({
    imports: [
        HotManSharedModule,
        // RouterModule.forRoot([ HOME_ROUTE ], { useHash: true })
    ],
    declarations: [
        PierChartComponent,
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DashBoardModule {}
