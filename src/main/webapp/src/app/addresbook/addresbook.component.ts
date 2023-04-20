import {Component, OnInit} from '@angular/core';
import {Contact} from "../models/contact.model";
import {BsModalService} from "ngx-bootstrap/modal";
import {ContactComponent} from "./contact/contact.component";
import {ContactService} from "../services/contact.service";
import {faAddressBook, faPhone, faTrashCan} from "@fortawesome/free-solid-svg-icons";
import {ConfirmationdialogComponent} from "../confirmationdialog/confirmationdialog.component";
import {FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-addresbook',
  templateUrl: './addresbook.component.html',
  styleUrls: ['./addresbook.component.scss']
})
export class AddresbookComponent implements OnInit {
  constructor(private modalService: BsModalService, private contactsService: ContactService) {
  }

  active: number = -1;

  contacts: Contact[] = []

  loadContacts() {
    this.contactsService.getAll().subscribe(
      value =>
        this.contacts = value
    );
  }

  open(contact: Contact | undefined) {
    let bsModalRef = this.modalService.show(ContactComponent, {
      initialState: {
        title: contact == undefined ? 'New Contact' : 'Edit Contact',
        data: {
          closeBtnName: 'Cancel',
          contact: (contact == undefined ? new Contact(0, '', '', '') : contact)
        }
      }
    });
    bsModalRef.onHidden?.subscribe(value => this.loadContacts());
  }
  delete(contact: Contact) {
    let bsModalRef = this.modalService.show(ConfirmationdialogComponent, {
      initialState: {
        data: {
          contact: contact
        }
      }
    });
    bsModalRef.onHidden?.subscribe(value => this.loadContacts());
  }


  ngOnInit(): void {
    this.loadContacts();
  }

  protected readonly faAddressBook = faAddressBook;
  protected readonly faPhone = faPhone;
  protected readonly faTrashCan = faTrashCan;
  searchForm = new FormGroup({
    search: new FormControl('')
  });


  filterContacts() {
    this.contactsService.filter(this.searchForm.controls['search'].value!).subscribe(value => this.contacts = value);
  }
}
