import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Reservation } from './reservation.model';
import { ReservationService } from './reservation.service';

@Injectable()
export class ReservationPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private reservationService: ReservationService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.reservationService.find(id).subscribe((reservation) => {
                    reservation.date_Revservation = this.datePipe
                        .transform(reservation.date_Revservation, 'yyyy-MM-ddThh:mm');
                    reservation.date_debut = this.datePipe
                        .transform(reservation.date_debut, 'yyyy-MM-ddThh:mm');
                    reservation.date_fin = this.datePipe
                        .transform(reservation.date_fin, 'yyyy-MM-ddThh:mm');
                    this.ngbModalRef = this.reservationModalRef(component, reservation);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.reservationModalRef(component, new Reservation());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    reservationModalRef(component: Component, reservation: Reservation): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.reservation = reservation;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
