import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Codebadge } from './codebadge.model';
import { CodebadgeService } from './codebadge.service';

@Injectable()
export class CodebadgePopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private codebadgeService: CodebadgeService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.codebadgeService.find(id).subscribe((codebadge) => {
                this.codebadgeModalRef(component, codebadge);
            });
        } else {
            return this.codebadgeModalRef(component, new Codebadge());
        }
    }

    codebadgeModalRef(component: Component, codebadge: Codebadge): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.codebadge = codebadge;
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
