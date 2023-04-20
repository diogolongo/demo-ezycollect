import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddresbookComponent } from './addresbook.component';

describe('AddresbookComponent', () => {
  let component: AddresbookComponent;
  let fixture: ComponentFixture<AddresbookComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddresbookComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddresbookComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
