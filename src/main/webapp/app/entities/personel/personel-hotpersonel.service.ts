import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { PersonelHotpersonel } from './personel-hotpersonel.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PersonelHotpersonelService {

    private resourceUrl = 'api/personels';
    private resourceSearchUrl = 'api/_search/personels';

    constructor(private http: Http) { }

    create(personel: PersonelHotpersonel): Observable<PersonelHotpersonel> {
        const copy = this.convert(personel);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(personel: PersonelHotpersonel): Observable<PersonelHotpersonel> {
        const copy = this.convert(personel);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<PersonelHotpersonel> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
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
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(personel: PersonelHotpersonel): PersonelHotpersonel {
        const copy: PersonelHotpersonel = Object.assign({}, personel);
        return copy;
    }
}
