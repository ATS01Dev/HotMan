import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { HorrairesHotpersonelComponent } from './horraires-hotpersonel.component';
import { HorrairesHotpersonelDetailComponent } from './horraires-hotpersonel-detail.component';
import { HorrairesHotpersonelPopupComponent } from './horraires-hotpersonel-dialog.component';
import { HorrairesHotpersonelDeletePopupComponent } from './horraires-hotpersonel-delete-dialog.component';

@Injectable()
export class HorrairesHotpersonelResolvePagingParams implements Resolve<any> {

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

export const horrairesRoute: Routes = [
    {
        path: 'horraires-hotpersonel',
        component: HorrairesHotpersonelComponent,
        resolve: {
            'pagingParams': HorrairesHotpersonelResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hotManApp.horraires.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'horraires-hotpersonel/:id',
        component: HorrairesHotpersonelDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hotManApp.horraires.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const horrairesPopupRoute: Routes = [
    {
        path: 'horraires-hotpersonel-new',
        component: HorrairesHotpersonelPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hotManApp.horraires.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'horraires-hotpersonel/:id/edit',
        component: HorrairesHotpersonelPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hotManApp.horraires.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'horraires-hotpersonel/:id/delete',
        component: HorrairesHotpersonelDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hotManApp.horraires.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
