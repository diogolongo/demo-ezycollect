import {Component, OnInit} from '@angular/core';
import {BsModalRef} from "ngx-bootstrap/modal";
import {ContactService} from "../services/contact.service";
import {faTimes, faTrashCan} from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: 'app-confirmationdialog',
  templateUrl: './confirmationdialog.component.html',
  styleUrls: ['./confirmationdialog.component.scss']
})
export class ConfirmationdialogComponent  implements OnInit {
  modalRef: BsModalRef;
  contact: any;
  data: any;

  constructor(
    private contactService: ContactService,
    modalRef: BsModalRef,
  ) {
    this.modalRef = modalRef;
  }

  confirmDelete(id: number) {
    this.contactService.delete(this.contact.id!).subscribe(value => {this.modalRef.hide()})
  }

  ngOnInit(): void {
        this.contact = this.data.contact;
    }

  protected readonly faTrashCan = faTrashCan;
  protected readonly faTimes = faTimes;
}
