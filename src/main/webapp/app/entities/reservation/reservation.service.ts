import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { Reservation } from './reservation.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ReservationService {

    private resourceUrl = 'api/reservations';
    private resourceSearchUrl = 'api/_search/reservations';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(reservation: Reservation): Observable<Reservation> {
        const copy = this.convert(reservation);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(reservation: Reservation): Observable<Reservation> {
        const copy = this.convert(reservation);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<Reservation> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.date_Revservation = this.dateUtils
            .convertDateTimeFromServer(entity.date_Revservation);
        entity.date_debut = this.dateUtils
            .convertDateTimeFromServer(entity.date_debut);
        entity.date_fin = this.dateUtils
            .convertDateTimeFromServer(entity.date_fin);
    }

    private convert(reservation: Reservation): Reservation {
        const copy: Reservation = Object.assign({}, reservation);

        copy.date_Revservation = this.dateUtils.toDate(reservation.date_Revservation);

        copy.date_debut = this.dateUtils.toDate(reservation.date_debut);

        copy.date_fin = this.dateUtils.toDate(reservation.date_fin);
        return copy;
    }
}
