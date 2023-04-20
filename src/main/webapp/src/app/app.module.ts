import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {AppBootstrapModule} from "./app-bootstrap-module/app-bootstrap.module";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import {ContactComponent} from "./addresbook/contact/contact.component";
import {AddresbookComponent} from "./addresbook/addresbook.component";
import {NgxMaskDirective, NgxMaskPipe, provideNgxMask} from "ngx-mask";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {ConfirmationdialogComponent} from './confirmationdialog/confirmationdialog.component';
import {GlobalhttpinterceptorService} from "./services/globalhttpinterceptor.service";

@NgModule({
  declarations: [
    AppComponent,
    AddresbookComponent,
    ContactComponent,
    ConfirmationdialogComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    BrowserAnimationsModule,
    AppBootstrapModule,
    AppRoutingModule,
    FontAwesomeModule,
    FormsModule,
    NgxMaskDirective,
    ReactiveFormsModule,
    NgxMaskPipe
  ],
  providers: [provideNgxMask(),
    {
      provide: HTTP_INTERCEPTORS,
      useClass: GlobalhttpinterceptorService,
      multi: true
    }],
  bootstrap: [AppComponent]
})
export class AppModule {
}
