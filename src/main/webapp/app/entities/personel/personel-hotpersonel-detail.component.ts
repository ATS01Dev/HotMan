import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { PersonelHotpersonel } from './personel-hotpersonel.model';
import { PersonelHotpersonelService } from './personel-hotpersonel.service';

@Component({
    selector: 'jhi-personel-hotpersonel-detail',
    templateUrl: './personel-hotpersonel-detail.component.html'
})
export class PersonelHotpersonelDetailComponent implements OnInit, OnDestroy {

    personel: PersonelHotpersonel;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private personelService: PersonelHotpersonelService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPersonels();
    }

    load(id) {
        this.personelService.find(id).subscribe((personel) => {
            this.personel = personel;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPersonels() {
        this.eventSubscriber = this.eventManager.subscribe(
            'personelListModification',
            (response) => this.load(this.personel.id)
        );
    }
}
