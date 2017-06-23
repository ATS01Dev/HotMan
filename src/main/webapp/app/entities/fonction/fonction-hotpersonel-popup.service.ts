import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { FonctionHotpersonel } from './fonction-hotpersonel.model';
import { FonctionHotpersonelService } from './fonction-hotpersonel.service';

@Injectable()
export class FonctionHotpersonelPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private fonctionService: FonctionHotpersonelService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

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
                this.fonctionModalRef(component, fonction);
            });
        } else {
            return this.fonctionModalRef(component, new FonctionHotpersonel());
        }
    }

    fonctionModalRef(component: Component, fonction: FonctionHotpersonel): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.fonction = fonction;
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
