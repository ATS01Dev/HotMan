import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Reservation } from './reservation.model';
import { ReservationPopupService } from './reservation-popup.service';
import { ReservationService } from './reservation.service';
import { Rooms, RoomsService } from '../rooms';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-reservation-dialog',
    templateUrl: './reservation-dialog.component.html'
})
export class ReservationDialogComponent implements OnInit {

    reservation: Reservation;
    isSaving: boolean;

    rooms: Rooms[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private reservationService: ReservationService,
        private roomsService: RoomsService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.roomsService
            .query({filter: 'reservation-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.reservation.rooms || !this.reservation.rooms.id) {
                    this.rooms = res.json;
                } else {
                    this.roomsService
                        .find(this.reservation.rooms.id)
                        .subscribe((subRes: Rooms) => {
                            this.rooms = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.reservation.id !== undefined) {
            this.subscribeToSaveResponse(
                this.reservationService.update(this.reservation));
        } else {
            this.subscribeToSaveResponse(
                this.reservationService.create(this.reservation));
        }
    }

    private subscribeToSaveResponse(result: Observable<Reservation>) {
        result.subscribe((res: Reservation) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Reservation) {
        this.eventManager.broadcast({ name: 'reservationListModification', content: 'OK'});
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

    trackRoomsById(index: number, item: Rooms) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-reservation-popup',
    template: ''
})
export class ReservationPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private reservationPopupService: ReservationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.reservationPopupService
                    .open(ReservationDialogComponent as Component, params['id']);
            } else {
                this.reservationPopupService
                    .open(ReservationDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
