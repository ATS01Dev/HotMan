import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { HotManTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ReservationDetailComponent } from '../../../../../../main/webapp/app/entities/reservation/reservation-detail.component';
import { ReservationService } from '../../../../../../main/webapp/app/entities/reservation/reservation.service';
import { Reservation } from '../../../../../../main/webapp/app/entities/reservation/reservation.model';

describe('Component Tests', () => {

    describe('Reservation Management Detail Component', () => {
        let comp: ReservationDetailComponent;
        let fixture: ComponentFixture<ReservationDetailComponent>;
        let service: ReservationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HotManTestModule],
                declarations: [ReservationDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ReservationService,
                    JhiEventManager
                ]
            }).overrideTemplate(ReservationDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ReservationDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReservationService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Reservation(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.reservation).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
