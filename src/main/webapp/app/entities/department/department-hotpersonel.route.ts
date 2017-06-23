import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { DepartmentHotpersonelComponent } from './department-hotpersonel.component';
import { DepartmentHotpersonelDetailComponent } from './department-hotpersonel-detail.component';
import { DepartmentHotpersonelPopupComponent } from './department-hotpersonel-dialog.component';
import { DepartmentHotpersonelDeletePopupComponent } from './department-hotpersonel-delete-dialog.component';

import { Principal } from '../../shared';

export const departmentRoute: Routes = [
    {
        path: 'department-hotpersonel',
        component: DepartmentHotpersonelComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hotManApp.department.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'department-hotpersonel/:id',
        component: DepartmentHotpersonelDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hotManApp.department.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const departmentPopupRoute: Routes = [
    {
        path: 'department-hotpersonel-new',
        component: DepartmentHotpersonelPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hotManApp.department.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'department-hotpersonel/:id/edit',
        component: DepartmentHotpersonelPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hotManApp.department.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'department-hotpersonel/:id/delete',
        component: DepartmentHotpersonelDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hotManApp.department.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
