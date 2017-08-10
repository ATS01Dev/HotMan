import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { FonctionHotpersonel } from './fonction-hotpersonel.model';
import { FonctionHotpersonelPopupService } from './fonction-hotpersonel-popup.service';
import { FonctionHotpersonelService } from './fonction-hotpersonel.service';
import { DepartmentHotpersonel, DepartmentHotpersonelService } from '../department';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-fonction-hotpersonel-dialog',
    templateUrl: './fonction-hotpersonel-dialog.component.html'
})
export class FonctionHotpersonelDialogComponent implements OnInit {

    fonction: FonctionHotpersonel;
    authorities: any[];
    isSaving: boolean;

    departments: DepartmentHotpersonel[];
    stardateDp: any;
    datenaissaneDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private fonctionService: FonctionHotpersonelService,
        private departmentService: DepartmentHotpersonelService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.departmentService
            .query({filter: 'fonction-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.fonction.department || !this.fonction.department.id) {
                    this.departments = res.json;
                } else {
                    this.departmentService
                        .find(this.fonction.department.id)
                        .subscribe((subRes: DepartmentHotpersonel) => {
                            this.departments = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.fonction.id !== undefined) {
            this.subscribeToSaveResponse(
                this.fonctionService.update(this.fonction), false);
        } else {
            this.subscribeToSaveResponse(
                this.fonctionService.create(this.fonction), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<FonctionHotpersonel>, isCreated: boolean) {
        result.subscribe((res: FonctionHotpersonel) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: FonctionHotpersonel, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'hotManApp.fonction.created'
            : 'hotManApp.fonction.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'fonctionListModification', content: 'OK'});
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

    trackDepartmentById(index: number, item: DepartmentHotpersonel) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-fonction-hotpersonel-popup',
    template: ''
})
export class FonctionHotpersonelPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private fonctionPopupService: FonctionHotpersonelPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.fonctionPopupService
                    .open(FonctionHotpersonelDialogComponent, params['id']);
            } else {
                this.modalRef = this.fonctionPopupService
                    .open(FonctionHotpersonelDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
