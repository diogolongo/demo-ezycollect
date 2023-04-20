import {Component, OnInit} from '@angular/core';
import {BsModalRef} from "ngx-bootstrap/modal";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ContactService} from "../../services/contact.service";

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.scss']
})
export class ContactComponent implements OnInit {
  title: any;
  contactForm = new FormGroup({
      id: new FormControl<number>({value: 0, disabled: true}),
      firstName: new FormControl('', [Validators.required, Validators.maxLength(100)]),
      lastName: new FormControl('', [Validators.required, Validators.maxLength(100)]),
      phoneNumber: new FormControl('',[Validators.required, Validators.maxLength(20)]),
    }
  )
  data: any;

  ngOnInit(): void {
    this.contactForm.setValue(this.data.contact)
  }

  get lastName() {
    return this.contactForm.get('lastName');
  }

  get firstName() {
    return this.contactForm.get('firstName');
  }

  get phoneNumber() {
    return this.contactForm.get('phoneNumber');
  }

  public modalRef: BsModalRef;
  isSaving: boolean;

  constructor(
    modalRef: BsModalRef,
    private contactService: ContactService
  ) {
    this.modalRef = modalRef;
    this.isSaving = false;
  }

  save() {
    let contact = this.contactForm.getRawValue();
    this.isSaving = true;
    try {
      if (typeof contact.id === 'number' && contact.id > 0) {
        this.contactService.update(contact).pipe().subscribe(value => this.modalRef.hide());
      } else {
        this.contactService.add(contact).pipe().subscribe(value => this.modalRef.hide());
      }
    } finally {
      this.isSaving = false;
    }

  }
}
