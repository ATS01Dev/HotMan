import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { FonctionHotpersonel } from './fonction-hotpersonel.model';
import { FonctionHotpersonelService } from './fonction-hotpersonel.service';

@Injectable()
export class FonctionHotpersonelPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private fonctionService: FonctionHotpersonelService

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
                this.fonctionService.find(id).subscribe((fonction) => {
                    if (fonction.stardate) {
                        fonction.stardate = {
                            year: fonction.stardate.getFullYear(),
                            month: fonction.stardate.getMonth() + 1,
                            day: fonction.stardate.getDate()
                        };
                    }
                    if (fonction.datenaissane) {
                        fonction.datenaissane = {
                            year: fonction.datenaissane.getFullYear(),
                            month: fonction.datenaissane.getMonth() + 1,
                            day: fonction.datenaissane.getDate()
                        };
                    }
                    this.ngbModalRef = this.fonctionModalRef(component, fonction);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.fonctionModalRef(component, new FonctionHotpersonel());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    fonctionModalRef(component: Component, fonction: FonctionHotpersonel): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.fonction = fonction;
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
