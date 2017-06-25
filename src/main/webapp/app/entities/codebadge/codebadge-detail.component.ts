import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Codebadge } from './codebadge.model';
import { CodebadgeService } from './codebadge.service';

@Component({
    selector: 'jhi-codebadge-detail',
    templateUrl: './codebadge-detail.component.html'
})
export class CodebadgeDetailComponent implements OnInit, OnDestroy {

    codebadge: Codebadge;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private codebadgeService: CodebadgeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCodebadges();
    }

    load(id) {
        this.codebadgeService.find(id).subscribe((codebadge) => {
            this.codebadge = codebadge;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCodebadges() {
        this.eventSubscriber = this.eventManager.subscribe(
            'codebadgeListModification',
            (response) => this.load(this.codebadge.id)
        );
    }
}
