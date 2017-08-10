import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PersonelHotpersonel } from './personel-hotpersonel.model';
import { PersonelHotpersonelPopupService } from './personel-hotpersonel-popup.service';
import { PersonelHotpersonelService } from './personel-hotpersonel.service';
import { FonctionHotpersonel, FonctionHotpersonelService } from '../fonction';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-personel-hotpersonel-dialog',
    templateUrl: './personel-hotpersonel-dialog.component.html'
})
export class PersonelHotpersonelDialogComponent implements OnInit {

    personel: PersonelHotpersonel;
    authorities: any[];
    isSaving: boolean;

    fonctions: FonctionHotpersonel[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private personelService: PersonelHotpersonelService,
        private fonctionService: FonctionHotpersonelService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.fonctionService
            .query({filter: 'personel-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.personel.fonction || !this.personel.fonction.id) {
                    this.fonctions = res.json;
                } else {
                    this.fonctionService
                        .find(this.personel.fonction.id)
                        .subscribe((subRes: FonctionHotpersonel) => {
                            this.fonctions = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.personel.id !== undefined) {
            this.subscribeToSaveResponse(
                this.personelService.update(this.personel), false);
        } else {
            this.subscribeToSaveResponse(
                this.personelService.create(this.personel), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<PersonelHotpersonel>, isCreated: boolean) {
        result.subscribe((res: PersonelHotpersonel) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: PersonelHotpersonel, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'hotManApp.personel.created'
            : 'hotManApp.personel.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'personelListModification', content: 'OK'});
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

    trackFonctionById(index: number, item: FonctionHotpersonel) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-personel-hotpersonel-popup',
    template: ''
})
export class PersonelHotpersonelPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private personelPopupService: PersonelHotpersonelPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.personelPopupService
                    .open(PersonelHotpersonelDialogComponent, params['id']);
            } else {
                this.modalRef = this.personelPopupService
                    .open(PersonelHotpersonelDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
