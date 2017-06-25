import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { DepartmentHotpersonel } from './department-hotpersonel.model';
import { DepartmentHotpersonelService } from './department-hotpersonel.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-department-hotpersonel',
    templateUrl: './department-hotpersonel.component.html'
})
export class DepartmentHotpersonelComponent implements OnInit, OnDestroy {
departments: DepartmentHotpersonel[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private departmentService: DepartmentHotpersonelService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch = activatedRoute.snapshot.params['search'] ? activatedRoute.snapshot.params['search'] : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.departmentService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: ResponseWrapper) => this.departments = res.json,
                    (res: ResponseWrapper) => this.onError(res.json)
                );
            return;
       }
        this.departmentService.query().subscribe(
            (res: ResponseWrapper) => {
                this.departments = res.json;
                this.currentSearch = '';
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.currentSearch = query;
        this.loadAll();
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInDepartments();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: DepartmentHotpersonel) {
        return item.id;
    }
    registerChangeInDepartments() {
        this.eventSubscriber = this.eventManager.subscribe('departmentListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
