import { Route } from '@angular/router';

import { NavbarComponent } from './layouts';
import { SidebarComponent } from "./layouts/sidebar/sidebar.component";

export const navbarRoute: Route =
    {
    path: '',
    component: NavbarComponent,
    outlet: 'navbar'
    };
export const sidebarrRoute: Route =

    {
        path: '',
        component: SidebarComponent,
        outlet: 'sidebar'
    }
;
