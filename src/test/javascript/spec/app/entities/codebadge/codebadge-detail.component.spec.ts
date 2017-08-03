/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { HotManTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CodebadgeDetailComponent } from '../../../../../../main/webapp/app/entities/codebadge/codebadge-detail.component';
import { CodebadgeService } from '../../../../../../main/webapp/app/entities/codebadge/codebadge.service';
import { Codebadge } from '../../../../../../main/webapp/app/entities/codebadge/codebadge.model';

describe('Component Tests', () => {

    describe('Codebadge Management Detail Component', () => {
        let comp: CodebadgeDetailComponent;
        let fixture: ComponentFixture<CodebadgeDetailComponent>;
        let service: CodebadgeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HotManTestModule],
                declarations: [CodebadgeDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CodebadgeService,
                    JhiEventManager
                ]
            }).overrideTemplate(CodebadgeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CodebadgeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CodebadgeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Codebadge(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.codebadge).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
