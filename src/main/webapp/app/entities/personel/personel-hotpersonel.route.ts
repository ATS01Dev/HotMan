import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PersonelHotpersonelComponent } from './personel-hotpersonel.component';
import { PersonelHotpersonelDetailComponent } from './personel-hotpersonel-detail.component';
import { PersonelHotpersonelPopupComponent } from './personel-hotpersonel-dialog.component';
import { PersonelHotpersonelDeletePopupComponent } from './personel-hotpersonel-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class PersonelHotpersonelResolvePagingParams implements Resolve<any> {

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

export const personelRoute: Routes = [
    {
        path: 'personel-hotpersonel',
        component: PersonelHotpersonelComponent,
        resolve: {
            'pagingParams': PersonelHotpersonelResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hotManApp.personel.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'personel-hotpersonel/:id',
        component: PersonelHotpersonelDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hotManApp.personel.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const personelPopupRoute: Routes = [
    {
        path: 'personel-hotpersonel-new',
        component: PersonelHotpersonelPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hotManApp.personel.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'personel-hotpersonel/:id/edit',
        component: PersonelHotpersonelPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hotManApp.personel.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'personel-hotpersonel/:id/delete',
        component: PersonelHotpersonelDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hotManApp.personel.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
