import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Rooms } from './rooms.model';
import { RoomsPopupService } from './rooms-popup.service';
import { RoomsService } from './rooms.service';

@Component({
    selector: 'jhi-rooms-delete-dialog',
    templateUrl: './rooms-delete-dialog.component.html'
})
export class RoomsDeleteDialogComponent {

    rooms: Rooms;

    constructor(
        private roomsService: RoomsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.roomsService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'roomsListModification',
                content: 'Deleted an rooms'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rooms-delete-popup',
    template: ''
})
export class RoomsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private roomsPopupService: RoomsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.roomsPopupService
                .open(RoomsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
