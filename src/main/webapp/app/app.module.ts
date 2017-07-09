import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2Webstorage } from 'ng2-webstorage';

import { HotManSharedModule, UserRouteAccessService } from './shared';
import { HotManHomeModule } from './home/home.module';
import { HotManAdminModule } from './admin/admin.module';
import { HotManAccountModule } from './account/account.module';
import { HotManEntityModule } from './entities/entity.module';

import { customHttpProvider } from './blocks/interceptor/http.provider';
import { PaginationConfig } from './blocks/config/uib-pagination.config';

// jhipster-needle-angular-add-module-import JHipster will add new module here

import {
    JhiMainComponent,
    LayoutRoutingModule,
    NavbarComponent,
    FooterComponent,
    ProfileService,
    PageRibbonComponent,
    ActiveMenuDirective,
    ErrorComponent
} from './layouts';
import { DashBoardModule } from './Dashboard/dashboard.module';
import { BilanService} from './bilan/bilan.service';
import { SidebarComponent } from './layouts/sidebar/sidebar.component';

@NgModule({
    imports: [
        BrowserModule,
        LayoutRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
        HotManSharedModule,
        HotManHomeModule,
        HotManAdminModule,
        HotManAccountModule,
        HotManEntityModule,
        DashBoardModule,
        // jhipster-needle-angular-add-module JHipster will add new module here
    ],
    declarations: [
        JhiMainComponent,
        NavbarComponent,
        ErrorComponent,
        PageRibbonComponent,
        ActiveMenuDirective,
        FooterComponent,
        SidebarComponent,
    ],
    providers: [
        ProfileService,
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService,
        BilanService
    ],
    bootstrap: [ JhiMainComponent ]
})
export class HotManAppModule {}
