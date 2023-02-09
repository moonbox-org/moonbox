import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { AppLayoutComponent } from './layout/app.layout.component';

const routes: Routes = [
  {
    path: '',
    component: AppLayoutComponent,
    children: [
      {
        path: '',
        component: DashboardComponent,
        data: { breadcrumb: { label: 'app home', info: 'pi pi-home' } },
      },
      {
        path: 'beneficiaries',
        loadChildren: () =>
          import('./modules/beneficiaries/beneficiaries.module').then(
            (m) => m.BeneficiariesModule
          ),
        data: { breadcrumb: { label: 'beneficiaries', info: 'pi pi-users' } },
        // canLoad: [AuthGuard],
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
