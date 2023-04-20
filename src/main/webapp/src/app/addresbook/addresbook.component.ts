import {Component} from '@angular/core';

import {faAddressBook} from "@fortawesome/free-solid-svg-icons";
import {Contact} from "../models/contact.model";
import {BsModalService} from "ngx-bootstrap/modal";
import {ContactComponent} from "./contact/contact.component";

@Component({
  selector: 'app-addresbook',
  templateUrl: './addresbook.component.html',
  styleUrls: ['./addresbook.component.scss']
})
export class AddresbookComponent {
  constructor(private modalService: BsModalService) {
  }

  active: number = 0;

  contacts: Contact[] = []

  onClick(index: number) {
    this.active = index;
  }

  open(idContact: number) {
    const modalEbook = this.modalService.show(ContactComponent, {
      initialState: {
        title: idContact == 0 ? 'New Contact': 'Edit Contact',
        data: {
          closeBtnName: 'Cancel'
        }
      }
    });
  }

  protected readonly faAddressBook = faAddressBook;
}
