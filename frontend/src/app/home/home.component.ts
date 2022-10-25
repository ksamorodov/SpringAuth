import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { first } from 'rxjs/operators';

import { User } from '../_models';
import {UserService, AuthenticationService, AlertService} from '../_services';
import {ActivatedRoute, Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit, OnDestroy {
    currentUser: User;
    currentUserSubscription: Subscription;
    users: User[] = [];
    selectedUser = null;
    isPasswordChangerOpened = false;
    registerForm: FormGroup;
    loading = false;
    submitted = false;
    // enablePasswordValidation = false;

    constructor(
        private authenticationService: AuthenticationService,
        private userService: UserService,
        private formBuilder: FormBuilder,
        private route: ActivatedRoute,
        private alertService: AlertService,
        private router: Router,
    ) {
        this.currentUserSubscription = this.authenticationService.currentUser.subscribe(user => {
            this.currentUser = user;
        });
    }

    setIsPasswordChangerOpened() {
        this.isPasswordChangerOpened = !this.isPasswordChangerOpened;
    }

    ngOnInit() {
        this.loadAllUsers();
        this.registerForm = this.formBuilder.group({
            password: ['', [Validators.required]]
        });
    }

    ngOnDestroy() {
        // unsubscribe to ensure no memory leaks
        this.currentUserSubscription.unsubscribe();
    }

    deleteUser(id: number) {
        this.userService.delete(id).pipe(first()).subscribe(() => {
            this.loadAllUsers()
        });
    }

    blockUser(id: number) {
        this.userService.block(id).pipe(first()).subscribe(() => {
            this.alertService.success("User was blocked")
            this.loadAllUsers()
        });
    }

    unblockUser(id: number) {
        this.userService.unblock(id).pipe(first()).subscribe(() => {
            this.alertService.success("User was unblocked")
            this.loadAllUsers()
        });
    }

    changePassword(id: number) {
        this.selectedUser = id;
        this.setIsPasswordChangerOpened();
    }

    private loadAllUsers() {
        this.userService.getAll().pipe(first()).subscribe(users => {
            this.users = users;
        });
    }

    setPasswordValidation() {
        // this.enablePasswordValidation = !this.enablePasswordValidation
    }

    hasDuplicateSymbols(str) {
        // if (this.enablePasswordValidation == false) {
            return /(.).*\1/.test(str);
        // } else {
        //     return false;
        // }
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
        // if (this.enablePasswordValidation && !this.hasDuplicateSymbols(this.registerForm.value)) {

            this.loading = true;
            this.userService.changePassword(this.selectedUser, this.registerForm.controls.password.value)
                .subscribe(
                    () => {
                        this.alertService.success('Change password was successful', true);
                        this.router.navigate(['/login']);
                    },
                    error => {
                        this.alertService.error("Error");
                        this.loading = false;
                    });
        }
    // }
}