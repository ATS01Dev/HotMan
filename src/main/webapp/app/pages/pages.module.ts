import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { routing } from './pages.routing';

import { Pages } from './pages.component';
import {NgaModule} from '../shared/nga.module';
import {HotManSharedModule} from '../shared/shared.module';

@NgModule({
  imports: [CommonModule, HotManSharedModule, NgaModule, routing],
  declarations: [Pages]
})
export class PagesModule {
}
