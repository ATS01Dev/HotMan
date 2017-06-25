import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { HorrairesHotpersonel } from './horraires-hotpersonel.model';
import { HorrairesHotpersonelService } from './horraires-hotpersonel.service';

@Component({
    selector: 'jhi-horraires-hotpersonel-detail',
    templateUrl: './horraires-hotpersonel-detail.component.html'
})
export class HorrairesHotpersonelDetailComponent implements OnInit, OnDestroy {

    horraires: HorrairesHotpersonel;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private horrairesService: HorrairesHotpersonelService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInHorraires();
    }

    load(id) {
        this.horrairesService.find(id).subscribe((horraires) => {
            this.horraires = horraires;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInHorraires() {
        this.eventSubscriber = this.eventManager.subscribe(
            'horrairesListModification',
            (response) => this.load(this.horraires.id)
        );
    }
}
