import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { HotManTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PersonelHotpersonelDetailComponent } from '../../../../../../main/webapp/app/entities/personel/personel-hotpersonel-detail.component';
import { PersonelHotpersonelService } from '../../../../../../main/webapp/app/entities/personel/personel-hotpersonel.service';
import { PersonelHotpersonel } from '../../../../../../main/webapp/app/entities/personel/personel-hotpersonel.model';

describe('Component Tests', () => {

    describe('PersonelHotpersonel Management Detail Component', () => {
        let comp: PersonelHotpersonelDetailComponent;
        let fixture: ComponentFixture<PersonelHotpersonelDetailComponent>;
        let service: PersonelHotpersonelService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HotManTestModule],
                declarations: [PersonelHotpersonelDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PersonelHotpersonelService,
                    JhiEventManager
                ]
            }).overrideTemplate(PersonelHotpersonelDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PersonelHotpersonelDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PersonelHotpersonelService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PersonelHotpersonel(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.personel).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
