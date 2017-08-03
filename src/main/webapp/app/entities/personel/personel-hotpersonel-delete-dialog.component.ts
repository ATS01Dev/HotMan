import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PersonelHotpersonel } from './personel-hotpersonel.model';
import { PersonelHotpersonelPopupService } from './personel-hotpersonel-popup.service';
import { PersonelHotpersonelService } from './personel-hotpersonel.service';

@Component({
    selector: 'jhi-personel-hotpersonel-delete-dialog',
    templateUrl: './personel-hotpersonel-delete-dialog.component.html'
})
export class PersonelHotpersonelDeleteDialogComponent {

    personel: PersonelHotpersonel;

    constructor(
        private personelService: PersonelHotpersonelService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.personelService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'personelListModification',
                content: 'Deleted an personel'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-personel-hotpersonel-delete-popup',
    template: ''
})
export class PersonelHotpersonelDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private personelPopupService: PersonelHotpersonelPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.personelPopupService
                .open(PersonelHotpersonelDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
