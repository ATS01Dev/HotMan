import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CodebadgeComponent } from './codebadge.component';
import { CodebadgeDetailComponent } from './codebadge-detail.component';
import { CodebadgePopupComponent } from './codebadge-dialog.component';
import { CodebadgeDeletePopupComponent } from './codebadge-delete-dialog.component';

import { Principal } from '../../shared';

export const codebadgeRoute: Routes = [
    {
        path: 'codebadge',
        component: CodebadgeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hotManApp.codebadge.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'codebadge/:id',
        component: CodebadgeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hotManApp.codebadge.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const codebadgePopupRoute: Routes = [
    {
        path: 'codebadge-new',
        component: CodebadgePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hotManApp.codebadge.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'codebadge/:id/edit',
        component: CodebadgePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hotManApp.codebadge.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'codebadge/:id/delete',
        component: CodebadgeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hotManApp.codebadge.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
