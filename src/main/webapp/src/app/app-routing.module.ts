import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {AddresbookComponent} from "./addresbook/addresbook.component";

const routes: Routes = [
  {path: 'addressbook', pathMatch: "full", component: AddresbookComponent},
  { path: '',   redirectTo: '/addressbook', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
