import {NgModule, CUSTOM_ELEMENTS_SCHEMA, ModuleWithProviders} from '@angular/core';
import { DatePipe } from '@angular/common';

import {
    HotManSharedLibsModule,
    HotManSharedCommonModule,
    CSRFService,
    AuthServerProvider,
    AccountService,
    UserService,
    StateStorageService,
    LoginService,
    LoginModalService,
    Principal,
    HasAnyAuthorityDirective,
    JhiLoginModalComponent
} from './';

@NgModule({
    imports: [
        HotManSharedLibsModule,
        HotManSharedCommonModule
    ],
    declarations: [
        JhiLoginModalComponent,
        HasAnyAuthorityDirective
    ],
    providers: [
        LoginService,
        LoginModalService,
        AccountService,
        StateStorageService,
        Principal,
        CSRFService,
        AuthServerProvider,
        UserService,
        DatePipe
    ],
    entryComponents: [JhiLoginModalComponent],
    exports: [
        HotManSharedCommonModule,
        JhiLoginModalComponent,
        HasAnyAuthorityDirective,
        DatePipe
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]

})
export class HotManSharedModule {
    static forRoot(): ModuleWithProviders {
        return {
            ngModule: HotManSharedModule
        };
    }
}
