import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Codebadge } from './codebadge.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CodebadgeService {

    private resourceUrl = 'api/codebadges';
    private resourceSearchUrl = 'api/_search/codebadges';

    constructor(private http: Http) { }

    create(codebadge: Codebadge): Observable<Codebadge> {
        const copy = this.convert(codebadge);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(codebadge: Codebadge): Observable<Codebadge> {
        const copy = this.convert(codebadge);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Codebadge> {
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

    private convert(codebadge: Codebadge): Codebadge {
        const copy: Codebadge = Object.assign({}, codebadge);
        return copy;
    }
}
