import {Component, OnInit, OnDestroy} from '@angular/core';
import {Subscription} from 'rxjs';
import {first} from 'rxjs/operators';

import {User} from '../_models';
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
    isProgramEnd: boolean;
    // enablePasswordValidation = false;

    constructor(
        private authenticationService: AuthenticationService,
        private userService: UserService,
        private formBuilder: FormBuilder,
        private route: ActivatedRoute,
        private alertService: AlertService,
        private router: Router,
    ) {
        this.authenticationService.isEnd().subscribe(data => {
                this.isProgramEnd = false;
            }, error => {
                this.isProgramEnd = true;
            }
        )
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
            oldPassword: ['', [Validators.required]],
            password: ['', [Validators.required]],
            repeatPassword: ['', [Validators.required]]
        });
    }

    ngOnDestroy() {
        // unsubscribe to ensure no memory leaks
        this.currentUserSubscription.unsubscribe();
    }

    deleteUser(id) {
        this.userService.delete(id).pipe(first()).subscribe(() => {
            this.loadAllUsers()
        });
    }

    blockUser(id) {
        this.userService.block(id).pipe(first()).subscribe(() => {
            this.alertService.success("???????????????????????? ????????????????????????")
            this.loadAllUsers()
        });
    }

    end() {
        this.authenticationService.end().subscribe(data => {
            this.isProgramEnd = true;
        })
    }

    unblockUser(id) {
        this.userService.unblock(id).pipe(first()).subscribe(() => {
            this.alertService.success("???????????????????????? ??????????????????????????")
            this.loadAllUsers()
        });
    }

    changePassword(user) {
        this.selectedUser = user;
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

    validatePassword(user: User, str) {
        if (user.validPassword) {
            return /(.).*\1/.test(str);
        } else {
            return false;
        }
    }

    isValidationOfPasswordEnabled(isEnabled) {
        if (isEnabled) {
            return " enabled";
        } else {
            return " disabled"
        }
    }

    setValidPassword(id, validPassword) {
        return this.authenticationService.setValidationPasswordEnabled(id, validPassword).subscribe(() => {
            this.loadAllUsers();
        })
    }

    // convenience getter for easy access to form fields
    get f() {
        return this.registerForm.controls;
    }

    onSubmit() {
        this.submitted = true;

        let oldPassword = this.registerForm.controls.oldPassword.value? this.registerForm.controls.oldPassword.value : "";
        if (this.currentUser.temporaryPassword == false && this.selectedUser == this.currentUser) {
            this.authenticationService.checkLogin(oldPassword)
                .pipe(first())
                .subscribe(data => {
                        this.setPassword();
                        // this.authenticationService.login(this.currentUser.username, this.registerForm.controls.password.value).subscribe(() => {
                        //
                        // });
                    },
                    error => {
                        this.alertService.error("???????????? ???????????? ???????????? ???? ??????????!");
                        this.loading = false;
                    });
        } else {
            this.setPassword();
        }
    }


    setPassword() {
        // stop here if form is invalid
        let user
        if (this.currentUser.role == 'ADMIN') {
            user = this.selectedUser? this.selectedUser : this.currentUser;
        } else {
            user = this.currentUser;
        }


        if (this.registerForm.controls.password.value !== this.registerForm.controls.repeatPassword.value) {
            this.alertService.error("Passwords is not equal");
            return;
        }

        if (this.validatePassword(user, this.registerForm.controls.password.value) == false) {
            this.alertService.error("Password should not repeat symbols");
            return;
        }

        this.loading = true;
        this.userService.changePassword(user.id, this.registerForm.controls.password.value)
            .subscribe(
                () => {
                    // this.authenticationService.login(this.currentUser.username, this.registerForm.controls.password.value).subscribe(() => {
                    //
                    // });
                    this.currentUser.temporaryPassword = false;
                    this.alertService.success('Password is changed', true);
                    this.isPasswordChangerOpened = false;
                    this.loading = false;
                    this.authenticationService.logout();
                    this.router.navigate(['/login']);
                },
                error => {
                    this.alertService.error("Error");
                    this.loading = false;
                });
    }
}