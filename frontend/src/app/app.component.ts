import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  title = 'frontend';

  constructor(private http: HttpClient) {}

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
