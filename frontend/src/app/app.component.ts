import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { PrimeNGConfig } from 'primeng/api';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent implements OnInit {
  title = 'frontend';

  constructor(private http: HttpClient, private primengConfig: PrimeNGConfig) {}

  ngOnInit(): void {
    this.primengConfig.ripple = true;
  }

  demoGet() {
    this.http
      .get('/backend/api/v1/beneficiaries/demo', {
        responseType: 'text',
        withCredentials: true,
      })
      .subscribe();
  }

  demoPost() {
    this.http
      .post('/backend/api/v1/beneficiaries/demo', null, {
        responseType: 'text',
        withCredentials: true,
      })
      .subscribe();
  }
}
