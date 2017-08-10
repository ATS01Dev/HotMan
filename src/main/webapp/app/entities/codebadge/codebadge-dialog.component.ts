import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Codebadge } from './codebadge.model';
import { CodebadgePopupService } from './codebadge-popup.service';
import { CodebadgeService } from './codebadge.service';
import { Badge, BadgeService } from '../badge';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-codebadge-dialog',
    templateUrl: './codebadge-dialog.component.html'
})
export class CodebadgeDialogComponent implements OnInit {

    codebadge: Codebadge;
    authorities: any[];
    isSaving: boolean;

    badges: Badge[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private codebadgeService: CodebadgeService,
        private badgeService: BadgeService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.badgeService.query()
            .subscribe((res: ResponseWrapper) => { this.badges = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.codebadge.id !== undefined) {
            this.subscribeToSaveResponse(
                this.codebadgeService.update(this.codebadge), false);
        } else {
            this.subscribeToSaveResponse(
                this.codebadgeService.create(this.codebadge), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<Codebadge>, isCreated: boolean) {
        result.subscribe((res: Codebadge) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Codebadge, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'hotManApp.codebadge.created'
            : 'hotManApp.codebadge.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'codebadgeListModification', content: 'OK'});
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

    trackBadgeById(index: number, item: Badge) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-codebadge-popup',
    template: ''
})
export class CodebadgePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private codebadgePopupService: CodebadgePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.codebadgePopupService
                    .open(CodebadgeDialogComponent, params['id']);
            } else {
                this.modalRef = this.codebadgePopupService
                    .open(CodebadgeDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
