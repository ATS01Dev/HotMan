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
                this.horrairesService.update(this.horraires));
        } else {
            this.subscribeToSaveResponse(
                this.horrairesService.create(this.horraires));
        }
    }

    private subscribeToSaveResponse(result: Observable<HorrairesHotpersonel>) {
        result.subscribe((res: HorrairesHotpersonel) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: HorrairesHotpersonel) {
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

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private horrairesPopupService: HorrairesHotpersonelPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.horrairesPopupService
                    .open(HorrairesHotpersonelDialogComponent as Component, params['id']);
            } else {
                this.horrairesPopupService
                    .open(HorrairesHotpersonelDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
