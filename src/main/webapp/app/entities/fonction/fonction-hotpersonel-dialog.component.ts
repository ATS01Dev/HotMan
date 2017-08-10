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
                this.fonctionService.update(this.fonction));
        } else {
            this.subscribeToSaveResponse(
                this.fonctionService.create(this.fonction));
        }
    }

    private subscribeToSaveResponse(result: Observable<FonctionHotpersonel>) {
        result.subscribe((res: FonctionHotpersonel) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: FonctionHotpersonel) {
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

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private fonctionPopupService: FonctionHotpersonelPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.fonctionPopupService
                    .open(FonctionHotpersonelDialogComponent as Component, params['id']);
            } else {
                this.fonctionPopupService
                    .open(FonctionHotpersonelDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
