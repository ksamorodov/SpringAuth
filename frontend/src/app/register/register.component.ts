import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

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
        // if (this.authenticationService.currentUserValue.role !== 'ADMIN') {
        //     this.router.navigate(['/']);
        // }
    }

    // setPasswordValidation() {
    //     this.enablePasswordValidation = !this.enablePasswordValidation
    // }

    ngOnInit() {
        this.registerForm = this.formBuilder.group({
            username: ['', Validators.required]
        });
    }

    // convenience getter for easy access to form fields
    get f() { return this.registerForm.controls; }

    onSubmit() {
        this.submitted = true;

        this.loading = true;
        this.userService.register(this.registerForm.controls.username.value)
            .subscribe(
                () => {
                    this.loading = false;
                    this.alertService.success('Пользователь успешно добавлен', true);
                },
                error => {
                    this.alertService.error("Имя пользователя уже занято");
                    this.loading = false;
                });
    }


}
