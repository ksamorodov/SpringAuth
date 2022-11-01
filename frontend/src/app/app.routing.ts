import { Routes, RouterModule } from '@angular/router';

import { HomeComponent } from './home';
import { LoginComponent } from './login';
import { RegisterComponent } from './register';
import { AuthGuard } from './_helpers';
import { AboutComponent } from "./about";

const routes: Routes = [
    { path: 'home', component: HomeComponent },
    { path: 'login', component: LoginComponent },
    { path: 'register', component: RegisterComponent },
    { path: 'about', component: AboutComponent },

    // otherwise redirect to home
    { path: '**', redirectTo: 'login' }
];

export const appRoutingModule = RouterModule.forRoot(routes);