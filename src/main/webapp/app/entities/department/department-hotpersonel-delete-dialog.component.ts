import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { DepartmentHotpersonel } from './department-hotpersonel.model';
import { DepartmentHotpersonelPopupService } from './department-hotpersonel-popup.service';
import { DepartmentHotpersonelService } from './department-hotpersonel.service';

@Component({
    selector: 'jhi-department-hotpersonel-delete-dialog',
    templateUrl: './department-hotpersonel-delete-dialog.component.html'
})
export class DepartmentHotpersonelDeleteDialogComponent {

    department: DepartmentHotpersonel;

    constructor(
        private departmentService: DepartmentHotpersonelService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.departmentService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'departmentListModification',
                content: 'Deleted an department'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('hotManApp.department.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-department-hotpersonel-delete-popup',
    template: ''
})
export class DepartmentHotpersonelDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private departmentPopupService: DepartmentHotpersonelPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.departmentPopupService
                .open(DepartmentHotpersonelDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
