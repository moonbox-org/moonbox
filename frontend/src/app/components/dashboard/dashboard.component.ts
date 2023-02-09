import { Component } from '@angular/core';

const beneficiaries = require('../../mocks/MOCK_DATA.json');

export interface Beneficiary {
  id: number;
  first_name: string;
  last_name: string;
  email: string;
  gender: string;
  ip_address: string;
}

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
})
export class DashboardComponent {
  beneficiaries: Beneficiary[] = beneficiaries;

  getBeneficiaries(): Beneficiary[] {
    return beneficiaries;
  }
}
