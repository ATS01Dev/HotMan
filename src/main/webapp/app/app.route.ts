import {Route, RouterModule, Routes} from '@angular/router';

import { NavbarComponent } from './layouts';
import {ModuleWithProviders} from '@angular/core';

/*export const navbarRoute: Route = {
    path: '',
    component: NavbarComponent,
    outlet: 'navbar'
};*/

export const routes: Routes = [
    { path: '', redirectTo: 'pages', pathMatch: 'full' },
    { path: '**', redirectTo: 'pages/dashboard' }
];

export const routing: ModuleWithProviders = RouterModule.forRoot(routes, { useHash: true });
