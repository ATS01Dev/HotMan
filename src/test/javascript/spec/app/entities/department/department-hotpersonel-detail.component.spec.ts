/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { HotManTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { DepartmentHotpersonelDetailComponent } from '../../../../../../main/webapp/app/entities/department/department-hotpersonel-detail.component';
import { DepartmentHotpersonelService } from '../../../../../../main/webapp/app/entities/department/department-hotpersonel.service';
import { DepartmentHotpersonel } from '../../../../../../main/webapp/app/entities/department/department-hotpersonel.model';

describe('Component Tests', () => {

    describe('DepartmentHotpersonel Management Detail Component', () => {
        let comp: DepartmentHotpersonelDetailComponent;
        let fixture: ComponentFixture<DepartmentHotpersonelDetailComponent>;
        let service: DepartmentHotpersonelService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HotManTestModule],
                declarations: [DepartmentHotpersonelDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    DepartmentHotpersonelService,
                    JhiEventManager
                ]
            }).overrideTemplate(DepartmentHotpersonelDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DepartmentHotpersonelDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DepartmentHotpersonelService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new DepartmentHotpersonel(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.department).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
