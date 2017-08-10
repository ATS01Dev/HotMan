import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { DepartmentHotpersonel } from './department-hotpersonel.model';
import { DepartmentHotpersonelPopupService } from './department-hotpersonel-popup.service';
import { DepartmentHotpersonelService } from './department-hotpersonel.service';

@Component({
    selector: 'jhi-department-hotpersonel-dialog',
    templateUrl: './department-hotpersonel-dialog.component.html'
})
export class DepartmentHotpersonelDialogComponent implements OnInit {

    department: DepartmentHotpersonel;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private departmentService: DepartmentHotpersonelService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.department.id !== undefined) {
            this.subscribeToSaveResponse(
                this.departmentService.update(this.department), false);
        } else {
            this.subscribeToSaveResponse(
                this.departmentService.create(this.department), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<DepartmentHotpersonel>, isCreated: boolean) {
        result.subscribe((res: DepartmentHotpersonel) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: DepartmentHotpersonel, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'hotManApp.department.created'
            : 'hotManApp.department.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'departmentListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-department-hotpersonel-popup',
    template: ''
})
export class DepartmentHotpersonelPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private departmentPopupService: DepartmentHotpersonelPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.departmentPopupService
                    .open(DepartmentHotpersonelDialogComponent, params['id']);
            } else {
                this.modalRef = this.departmentPopupService
                    .open(DepartmentHotpersonelDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
