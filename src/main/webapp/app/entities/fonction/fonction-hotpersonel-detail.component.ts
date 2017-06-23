import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { FonctionHotpersonel } from './fonction-hotpersonel.model';
import { FonctionHotpersonelService } from './fonction-hotpersonel.service';

@Component({
    selector: 'jhi-fonction-hotpersonel-detail',
    templateUrl: './fonction-hotpersonel-detail.component.html'
})
export class FonctionHotpersonelDetailComponent implements OnInit, OnDestroy {

    fonction: FonctionHotpersonel;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private fonctionService: FonctionHotpersonelService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInFonctions();
    }

    load(id) {
        this.fonctionService.find(id).subscribe((fonction) => {
            this.fonction = fonction;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInFonctions() {
        this.eventSubscriber = this.eventManager.subscribe(
            'fonctionListModification',
            (response) => this.load(this.fonction.id)
        );
    }
}
