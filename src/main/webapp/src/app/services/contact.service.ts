import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Contact} from "../models/contact.model";


@Injectable({
  providedIn: 'root'
})
export class ContactService {
  private url = 'http://localhost:8080/api/contacts/'

  constructor(
    private httpClient: HttpClient
  ) {

  }
  public getAll() {
    return this.httpClient.get(`${this.url}`)
  }

  public get() {
    return this.httpClient.get(`${this.url}`)
  }

  public filter(filter: string) {
    return this.httpClient.get(`${this.url}`)
  }

  public add(contact: Contact) {
    return this.httpClient.post(`${this.url}`, contact)
  }

  public update(id: number, contact: Contact) {
    return this.httpClient.put(`${this.url}/${id}`, contact)
  }

  public delete(id: number) {
    return this.httpClient.delete(`${this.url}/${id}`)
  }
}
