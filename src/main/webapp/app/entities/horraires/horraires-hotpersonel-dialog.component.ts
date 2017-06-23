import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { HorrairesHotpersonel } from './horraires-hotpersonel.model';
import { HorrairesHotpersonelPopupService } from './horraires-hotpersonel-popup.service';
import { HorrairesHotpersonelService } from './horraires-hotpersonel.service';
import { PersonelHotpersonel, PersonelHotpersonelService } from '../personel';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-horraires-hotpersonel-dialog',
    templateUrl: './horraires-hotpersonel-dialog.component.html'
})
export class HorrairesHotpersonelDialogComponent implements OnInit {

    horraires: HorrairesHotpersonel;
    authorities: any[];
    isSaving: boolean;

    personels: PersonelHotpersonel[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private horrairesService: HorrairesHotpersonelService,
        private personelService: PersonelHotpersonelService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.personelService.query()
            .subscribe((res: ResponseWrapper) => { this.personels = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.horraires.id !== undefined) {
            this.subscribeToSaveResponse(
                this.horrairesService.update(this.horraires), false);
        } else {
            this.subscribeToSaveResponse(
                this.horrairesService.create(this.horraires), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<HorrairesHotpersonel>, isCreated: boolean) {
        result.subscribe((res: HorrairesHotpersonel) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: HorrairesHotpersonel, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'hotManApp.horraires.created'
            : 'hotManApp.horraires.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'horrairesListModification', content: 'OK'});
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

    trackPersonelById(index: number, item: PersonelHotpersonel) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-horraires-hotpersonel-popup',
    template: ''
})
export class HorrairesHotpersonelPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private horrairesPopupService: HorrairesHotpersonelPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.horrairesPopupService
                    .open(HorrairesHotpersonelDialogComponent, params['id']);
            } else {
                this.modalRef = this.horrairesPopupService
                    .open(HorrairesHotpersonelDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
