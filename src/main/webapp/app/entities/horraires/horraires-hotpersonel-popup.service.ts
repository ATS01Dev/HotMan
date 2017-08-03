import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { HorrairesHotpersonel } from './horraires-hotpersonel.model';
import { HorrairesHotpersonelService } from './horraires-hotpersonel.service';

@Injectable()
export class HorrairesHotpersonelPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private horrairesService: HorrairesHotpersonelService

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
                this.horrairesService.find(id).subscribe((horraires) => {
                    horraires.startDate = this.datePipe
                        .transform(horraires.startDate, 'yyyy-MM-ddThh:mm');
                    horraires.endDate = this.datePipe
                        .transform(horraires.endDate, 'yyyy-MM-ddThh:mm');
                    this.ngbModalRef = this.horrairesModalRef(component, horraires);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.horrairesModalRef(component, new HorrairesHotpersonel());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    horrairesModalRef(component: Component, horraires: HorrairesHotpersonel): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.horraires = horraires;
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
