import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Codebadge } from './codebadge.model';
import { CodebadgePopupService } from './codebadge-popup.service';
import { CodebadgeService } from './codebadge.service';

@Component({
    selector: 'jhi-codebadge-delete-dialog',
    templateUrl: './codebadge-delete-dialog.component.html'
})
export class CodebadgeDeleteDialogComponent {

    codebadge: Codebadge;

    constructor(
        private codebadgeService: CodebadgeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.codebadgeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'codebadgeListModification',
                content: 'Deleted an codebadge'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-codebadge-delete-popup',
    template: ''
})
export class CodebadgeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private codebadgePopupService: CodebadgePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.codebadgePopupService
                .open(CodebadgeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
