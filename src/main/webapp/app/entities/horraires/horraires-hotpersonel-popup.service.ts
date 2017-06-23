import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { HorrairesHotpersonel } from './horraires-hotpersonel.model';
import { HorrairesHotpersonelService } from './horraires-hotpersonel.service';

@Injectable()
export class HorrairesHotpersonelPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private horrairesService: HorrairesHotpersonelService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.horrairesService.find(id).subscribe((horraires) => {
                horraires.startDate = this.datePipe
                    .transform(horraires.startDate, 'yyyy-MM-ddThh:mm');
                horraires.endDate = this.datePipe
                    .transform(horraires.endDate, 'yyyy-MM-ddThh:mm');
                this.horrairesModalRef(component, horraires);
            });
        } else {
            return this.horrairesModalRef(component, new HorrairesHotpersonel());
        }
    }

    horrairesModalRef(component: Component, horraires: HorrairesHotpersonel): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.horraires = horraires;
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
