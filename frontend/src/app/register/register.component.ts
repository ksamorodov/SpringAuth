import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first } from 'rxjs/operators';

import { AlertService, UserService, AuthenticationService } from '../_services';

@Component({templateUrl: 'register.component.html'})
export class RegisterComponent implements OnInit {
    registerForm: FormGroup;
    loading = false;
    submitted = false;
    // enablePasswordValidation = false;

    constructor(
        private formBuilder: FormBuilder,
        private router: Router,
        private authenticationService: AuthenticationService,
        private userService: UserService,
        private alertService: AlertService
    ) {
        // redirect to home if already logged in
        if (this.authenticationService.currentUserValue.role !== 'ADMIN') {
            this.router.navigate(['/']);
        }
    }

    // setPasswordValidation() {
    //     this.enablePasswordValidation = !this.enablePasswordValidation
    // }

    ngOnInit() {
        this.registerForm = this.formBuilder.group({
            firstName: ['', ],
            lastName: ['', ],
            username: ['', Validators.required],
            password: ['', []]
        });
    }

    hasDuplicateSymbols(str) {
        return new Set(str.toLocaleLowerCase()).size !== [...str].length;
    }


    // convenience getter for easy access to form fields
    get f() { return this.registerForm.controls; }

    onSubmit() {
        this.submitted = true;

        // stop here if form is invalid
        if (this.registerForm.invalid || this.hasDuplicateSymbols(this.registerForm.controls.password.value)) {
            this.alertService.error("Duplicate password");
            return;
        }

        this.loading = true;
        this.userService.register(this.registerForm.value)
            .subscribe(
                () => {
                    this.alertService.success('Registration successful', true);
                    this.router.navigate(['/login']);
                },
                error => {
                    this.alertService.error("This username exists");
                    this.loading = false;
                });
    }


}
