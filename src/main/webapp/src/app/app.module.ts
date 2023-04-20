import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {AppBootstrapModule} from "./app-bootstrap-module/app-bootstrap.module";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import {ContactComponent} from "./addresbook/contact/contact.component";
import {AddresbookComponent} from "./addresbook/addresbook.component";

@NgModule({
  declarations: [
    AppComponent,
    AddresbookComponent,
    ContactComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppBootstrapModule,
    AppRoutingModule,
    FontAwesomeModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
