import { Component, ViewContainerRef } from '@angular/core';
import * as $ from 'jquery';
import {GlobalState} from '../../global.state';
import {BaImageLoaderService} from '../../shared/services/ba-image-loader/ba-image-loader.service';
import {BaThemeSpinner} from '../../shared/services/ba-theme-spinner/ba-theme-spinner.service';
import {BaThemeConfig} from '../../shared/theme/theme.config';
import {BaThemePreloader} from '../../shared/services/ba-theme-preloader/ba-theme-preloader.service';

/*
 * App Component
 * Top Level Component
 */
@Component({
  selector: 'jhi-app',
  styleUrls: ['./app.component.scss'],
  template: `
    <main [class.menu-collapsed]="isMenuCollapsed" baThemeRun>
      <div class="additional-bg"></div>
      <router-outlet></router-outlet>
    </main>
  `
})
export class App {

  isMenuCollapsed = false;

  constructor(private _state: GlobalState,
              private _imageLoader: BaImageLoaderService,
              private _spinner: BaThemeSpinner,
              private viewContainerRef: ViewContainerRef,
              private themeConfig: BaThemeConfig) {

    themeConfig.config();

    this._loadImages();

    this._state.subscribe('menu.isCollapsed', (isCollapsed) => {
      this.isMenuCollapsed = isCollapsed;
    });
  }

  public ngAfterViewInit(): void {
    // hide spinner once all loaders are completed
    BaThemePreloader.load().then((values) => {
      this._spinner.hide();
    });
  }

  private _loadImages(): void {
    // register some loaders
  //  BaThemePreloader.registerLoader(this._imageLoader.load('content/img/sky-bg.jpg'));
  }

}
