import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { DepartmentHotpersonel } from './department-hotpersonel.model';
import { DepartmentHotpersonelService } from './department-hotpersonel.service';

@Component({
    selector: 'jhi-department-hotpersonel-detail',
    templateUrl: './department-hotpersonel-detail.component.html'
})
export class DepartmentHotpersonelDetailComponent implements OnInit, OnDestroy {

    department: DepartmentHotpersonel;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private departmentService: DepartmentHotpersonelService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDepartments();
    }

    load(id) {
        this.departmentService.find(id).subscribe((department) => {
            this.department = department;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDepartments() {
        this.eventSubscriber = this.eventManager.subscribe(
            'departmentListModification',
            (response) => this.load(this.department.id)
        );
    }
}
