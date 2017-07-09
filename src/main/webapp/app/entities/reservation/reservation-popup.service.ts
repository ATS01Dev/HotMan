import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Reservation } from './reservation.model';
import { ReservationService } from './reservation.service';

@Injectable()
export class ReservationPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private reservationService: ReservationService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.reservationService.find(id).subscribe((reservation) => {
                reservation.date = this.datePipe
                    .transform(reservation.date, 'yyyy-MM-ddThh:mm');
                reservation.debut = this.datePipe
                    .transform(reservation.debut, 'yyyy-MM-ddThh:mm');
                reservation.fin = this.datePipe
                    .transform(reservation.fin, 'yyyy-MM-ddThh:mm');
                this.reservationModalRef(component, reservation);
            });
        } else {
            return this.reservationModalRef(component, new Reservation());
        }
    }

    reservationModalRef(component: Component, reservation: Reservation): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.reservation = reservation;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
