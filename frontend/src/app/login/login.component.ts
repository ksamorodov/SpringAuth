import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first } from 'rxjs/operators';

import { AlertService, AuthenticationService } from '../_services';
import {error} from "protractor";

@Component({templateUrl: 'login.component.html'})
export class LoginComponent implements OnInit {
    loginForm: FormGroup;
    loading = false;
    submitted = false;
    returnUrl: string;
    isAuth: false;

    isBdDecrypted: boolean;
    isProgramEnd: boolean;
    bdForm: FormGroup;
    submittedBdForm = false;

    constructor(
        private formBuilder: FormBuilder,
        private route: ActivatedRoute,
        private router: Router,
        private authenticationService: AuthenticationService,
        private alertService: AlertService
    ) {
        this.authenticationService.isEnd().subscribe(data => {
                this.isProgramEnd = false;
                this.authenticationService.isPasswordDecrypted().subscribe(data => {
                    this.isBdDecrypted = true;
                    this.isProgramEnd = false;
                }, error => {
                    this.isProgramEnd = false;
                    this.isBdDecrypted = false;
                });
            }, error => {
                this.isProgramEnd = true;
            }
        )
        // redirect to home if already logged in
    }

    end() {
        this.authenticationService.end().subscribe(data => {
            this.isProgramEnd = true;
        })
    }

    ngOnInit() {
        this.authenticationService.isEnd().subscribe(data => {
            this.isProgramEnd = false;
        }, error => {
            this.isProgramEnd = true;
        })
        // this.isBdDecrypted = false;
        this.loginForm = this.formBuilder.group({
            username: ['', Validators.required],
            password: ['', []]
        });

        this.bdForm = this.formBuilder.group({
            password: ['', Validators.required]
        });

        // get return url from route parameters or default to '/'
        this.returnUrl = '/home';
    }

    // convenience getter for easy access to form fields
    get f() { return this.loginForm.controls; }

    dectyptBdByPassword() {
        this.submittedBdForm = true;

        if (this.bdForm.invalid) {
            return;
        }

        this.loading = true;
        this.authenticationService.decryptBd(this.bdForm.controls.password.value).subscribe(
            data => {
                this.isBdDecrypted = true;
                this.loading = false;
            }, error => {
                this.loading = false;
                this.isBdDecrypted = false;
                this.isProgramEnd = true;
            }
        );
    }

    onSubmit() {
        this.submitted = true;

        // stop here if form is invalid
        if (this.loginForm.invalid) {
            return;
        }

        this.loading = true;
        this.authenticationService.addConnection().subscribe(() => {
            this.authenticationService.login(this.f.username.value, this.f.password.value)
                .pipe(first())
                .subscribe(
                    data => {
                        this.loading = false;
                        this.alertService.success("Вы вошли в аккаунт");
                    },
                    error => {
                        this.alertService.error("Неправильный логин или пароль");
                        this.loading = false;
                    });
        });
    }

}
