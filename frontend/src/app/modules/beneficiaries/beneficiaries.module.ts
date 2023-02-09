import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BeneficiariesRoutingModule } from './beneficiaries-routing.module';
import { BeneficiariesComponent } from './beneficiaries.component';

@NgModule({
  declarations: [BeneficiariesComponent],
  imports: [CommonModule, BeneficiariesRoutingModule],
})
export class BeneficiariesModule {}
