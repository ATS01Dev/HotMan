import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { PersonelHotpersonel } from './personel-hotpersonel.model';
import { PersonelHotpersonelService } from './personel-hotpersonel.service';

@Injectable()
export class PersonelHotpersonelPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private personelService: PersonelHotpersonelService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.personelService.find(id).subscribe((personel) => {
                this.personelModalRef(component, personel);
            });
        } else {
            return this.personelModalRef(component, new PersonelHotpersonel());
        }
    }

    personelModalRef(component: Component, personel: PersonelHotpersonel): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.personel = personel;
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
