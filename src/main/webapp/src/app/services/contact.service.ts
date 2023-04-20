import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {IContact} from "../models/contact.model";


@Injectable({
  providedIn: 'root'
})
export class ContactService {
  private url = 'http://localhost:8080/api/contacts/'

  constructor(
    private httpClient: HttpClient
  ) {

  }
  public getAll():Observable<IContact[]> {
    return this.httpClient.get<IContact[]>(`${this.url}`)
  }

  public get() {
    return this.httpClient.get<IContact>(`${this.url}`)
  }

  public filter(filter: string) {
    let queryParams = {
      'filter': filter
    }
    return this.httpClient.get<IContact[]>(`${this.url}filter`,{params: queryParams})
  }

  public add(iContact: IContact) {
    return this.httpClient.post<IContact>(`${this.url}`, iContact)
  }

  public update(iContact: IContact) {
    return this.httpClient.put<IContact>(`${this.url} ${iContact.id}`, iContact)
  }

  public delete(id: number) {
    return this.httpClient.delete(`${this.url}${id}`)
  }
}
