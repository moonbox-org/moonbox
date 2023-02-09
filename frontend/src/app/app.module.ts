import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HttpClientXsrfModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppLayoutModule } from './layout/app.layout.module';
import { TableModule } from 'primeng/table';
import { AppComponent } from './app.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';

@NgModule({
  declarations: [AppComponent, DashboardComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    AppLayoutModule,
    HttpClientModule,
    HttpClientXsrfModule.withOptions({
      cookieName: 'X-XSRF-TOKEN',
    }),
    TableModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
