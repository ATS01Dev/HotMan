import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { FonctionHotpersonelComponent } from './fonction-hotpersonel.component';
import { FonctionHotpersonelDetailComponent } from './fonction-hotpersonel-detail.component';
import { FonctionHotpersonelPopupComponent } from './fonction-hotpersonel-dialog.component';
import { FonctionHotpersonelDeletePopupComponent } from './fonction-hotpersonel-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class FonctionHotpersonelResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const fonctionRoute: Routes = [
    {
        path: 'fonction-hotpersonel',
        component: FonctionHotpersonelComponent,
        resolve: {
            'pagingParams': FonctionHotpersonelResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hotManApp.fonction.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'fonction-hotpersonel/:id',
        component: FonctionHotpersonelDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hotManApp.fonction.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const fonctionPopupRoute: Routes = [
    {
        path: 'fonction-hotpersonel-new',
        component: FonctionHotpersonelPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hotManApp.fonction.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'fonction-hotpersonel/:id/edit',
        component: FonctionHotpersonelPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hotManApp.fonction.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'fonction-hotpersonel/:id/delete',
        component: FonctionHotpersonelDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hotManApp.fonction.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
