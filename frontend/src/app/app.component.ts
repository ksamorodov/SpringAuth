import {Component, OnDestroy, OnInit} from '@angular/core';
import { Router } from '@angular/router';

import { AuthenticationService } from './_services';
import { User } from './_models';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({ selector: 'app',
    styleUrls: ['app.component.css'],
    templateUrl: 'app.component.html' })
export class AppComponent implements OnInit, OnDestroy{
    currentUser: User;

    isBdDecrypted: boolean = false;
    isProgramEnd: boolean = false;
    registerForm: FormGroup;
    submitted = false;

    constructor(
        private router: Router,
        private formBuilder: FormBuilder,
        private authenticationService: AuthenticationService
    ) {
        this.authenticationService.currentUser.subscribe(x => this.currentUser = x);
    }

    home() {
        this.router.navigate(['/home']);
    }

    logout() {
        this.authenticationService.logout();
        this.router.navigate(['/login']);
    }

    addUser() {
        this.router.navigate(['/register']);
    }

    get f() {
        return this.registerForm.controls;
    }

    about() {
        this.router.navigate(['/about']);
    }

    onSubmit() {

    }

    ngOnDestroy(): void {
    }

    ngOnInit(): void {
        this.logout();
        this.registerForm = this.formBuilder.group({
            oldPassword: ['', [Validators.required]],
            password: ['', [Validators.required]],
            repeatPassword: ['', [Validators.required]]
        });
    }
}