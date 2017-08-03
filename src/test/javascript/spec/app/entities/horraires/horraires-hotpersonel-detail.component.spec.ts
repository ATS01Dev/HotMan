/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { HotManTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { HorrairesHotpersonelDetailComponent } from '../../../../../../main/webapp/app/entities/horraires/horraires-hotpersonel-detail.component';
import { HorrairesHotpersonelService } from '../../../../../../main/webapp/app/entities/horraires/horraires-hotpersonel.service';
import { HorrairesHotpersonel } from '../../../../../../main/webapp/app/entities/horraires/horraires-hotpersonel.model';

describe('Component Tests', () => {

    describe('HorrairesHotpersonel Management Detail Component', () => {
        let comp: HorrairesHotpersonelDetailComponent;
        let fixture: ComponentFixture<HorrairesHotpersonelDetailComponent>;
        let service: HorrairesHotpersonelService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HotManTestModule],
                declarations: [HorrairesHotpersonelDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    HorrairesHotpersonelService,
                    JhiEventManager
                ]
            }).overrideTemplate(HorrairesHotpersonelDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(HorrairesHotpersonelDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HorrairesHotpersonelService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new HorrairesHotpersonel(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.horraires).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
