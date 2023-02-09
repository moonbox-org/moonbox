import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AppLayoutComponent } from './layout/app.layout.component';

const routes: Routes = [
  {
    path: '',
    component: AppLayoutComponent,
    children: [
      // {
      //   path: '',
      //   component: HomeComponent,
      //   data: { breadcrumb: { label: 'app home', info: 'pi pi-home' } },
      // },
      // {
      //   path: 'beneficiaries',
      //   component: BeneficiariesComponent,
      //   data: { breadcrumb: { label: 'beneficiaries', info: 'pi pi-users' } },
      //   canActivate: [AuthGuard],
      // },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
