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
import {TranslateModule} from '@ngx-translate/core';
import {NgaModule} from './shared/nga.module';
import {DashboardModule} from './features/dashboard/dashboard.module';
import {PagesModule} from './pages/pages.module';
import {App} from './app.component';
import {GlobalState} from './global.state';
import {routing} from './app.route';
import {AppState} from './app.service';
import {RouterModule} from '@angular/router';

@NgModule({
    imports: [
        BrowserModule,
        // LayoutRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
        HotManSharedModule,
        // HotManHomeModule,
        HotManAdminModule,
        HotManAccountModule,
        HotManEntityModule,
        DashboardModule,
        NgaModule.forRoot(),
        TranslateModule.forRoot(),
        PagesModule,
        RouterModule,
       // routing
        // jhipster-needle-angular-add-module JHipster will add new module here
    ],
    declarations: [
       // JhiMainComponent,
       // NavbarComponent,
        ErrorComponent,
        PageRibbonComponent,
        ActiveMenuDirective,
        FooterComponent,
        App,
    ],
    providers: [
        ProfileService,
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService,
        GlobalState,
        AppState
    ],
    bootstrap: [ App ]
    // bootstrap: [ JhiMainComponent ] jhipster default
})
export class HotManAppModule {
    constructor(public appState: AppState) {
    }
}
