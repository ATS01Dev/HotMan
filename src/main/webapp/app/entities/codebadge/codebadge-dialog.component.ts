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
                this.codebadgeService.update(this.codebadge));
        } else {
            this.subscribeToSaveResponse(
                this.codebadgeService.create(this.codebadge));
        }
    }

    private subscribeToSaveResponse(result: Observable<Codebadge>) {
        result.subscribe((res: Codebadge) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Codebadge) {
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

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private codebadgePopupService: CodebadgePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.codebadgePopupService
                    .open(CodebadgeDialogComponent as Component, params['id']);
            } else {
                this.codebadgePopupService
                    .open(CodebadgeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
