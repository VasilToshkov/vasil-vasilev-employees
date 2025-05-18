import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { PairModel } from "../model/pair.model";

const BEST_PAIR_URL = "http://localhost:8080/api/employee/bestPair"
@Injectable({providedIn: 'root'})
export class LoadFileService {

  constructor(
    private http: HttpClient
  ){}

  getBestPair(formData: FormData): Observable<PairModel> {
    return this.http.post<PairModel>(BEST_PAIR_URL, formData);
  }
}