import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { FonctionHotpersonel } from './fonction-hotpersonel.model';
import { FonctionHotpersonelPopupService } from './fonction-hotpersonel-popup.service';
import { FonctionHotpersonelService } from './fonction-hotpersonel.service';

@Component({
    selector: 'jhi-fonction-hotpersonel-delete-dialog',
    templateUrl: './fonction-hotpersonel-delete-dialog.component.html'
})
export class FonctionHotpersonelDeleteDialogComponent {

    fonction: FonctionHotpersonel;

    constructor(
        private fonctionService: FonctionHotpersonelService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.fonctionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'fonctionListModification',
                content: 'Deleted an fonction'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('hotManApp.fonction.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-fonction-hotpersonel-delete-popup',
    template: ''
})
export class FonctionHotpersonelDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private fonctionPopupService: FonctionHotpersonelPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.fonctionPopupService
                .open(FonctionHotpersonelDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
