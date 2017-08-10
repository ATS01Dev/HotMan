import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { HotManTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { FonctionHotpersonelDetailComponent } from '../../../../../../main/webapp/app/entities/fonction/fonction-hotpersonel-detail.component';
import { FonctionHotpersonelService } from '../../../../../../main/webapp/app/entities/fonction/fonction-hotpersonel.service';
import { FonctionHotpersonel } from '../../../../../../main/webapp/app/entities/fonction/fonction-hotpersonel.model';

describe('Component Tests', () => {

    describe('FonctionHotpersonel Management Detail Component', () => {
        let comp: FonctionHotpersonelDetailComponent;
        let fixture: ComponentFixture<FonctionHotpersonelDetailComponent>;
        let service: FonctionHotpersonelService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HotManTestModule],
                declarations: [FonctionHotpersonelDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    FonctionHotpersonelService,
                    JhiEventManager
                ]
            }).overrideTemplate(FonctionHotpersonelDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FonctionHotpersonelDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FonctionHotpersonelService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new FonctionHotpersonel(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.fonction).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
