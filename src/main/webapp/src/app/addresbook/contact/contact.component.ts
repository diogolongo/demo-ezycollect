import {Component, OnInit} from '@angular/core';
import {Contact} from "../../models/contact.model";
import {BsModalRef} from "ngx-bootstrap/modal";

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.scss']
})
export class ContactComponent  implements OnInit {
  title: any;

  data: any;
  contact: Contact;
  ngOnInit(): void {
    // this.closeBtnName = this.data['closeBtnName'];
  }

  public modalRef: BsModalRef;

  constructor(
    modalRef: BsModalRef
  ) {
    this.modalRef = modalRef;
    this.contact = new Contact(0, '','','');


  }

}
