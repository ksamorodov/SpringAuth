import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';
import {map} from 'rxjs/operators';

import {User} from '../_models';

@Injectable({providedIn: 'root'})
export class AuthenticationService {
    private currentUserSubject: BehaviorSubject<User>;
    public currentUser: Observable<User>;

    constructor(private http: HttpClient) {
        this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('currentUser')));
        this.currentUser = this.currentUserSubject.asObservable();
    }

    public get currentUserValue(): User {
        return this.currentUserSubject.value;
    }

    setValidationPasswordEnabled(id, value) {
        return this.http.get<any>('/api/auth/set-validation-password?id=' + id + '&value=' + value);
    }

    end() {
        return this.http.get<any>('/api/auth/end')
    }

    isEnd() {
        return this.http.get<any>('/api/auth/is-end')
    }

    login(username: string, password: string) {
        const body = new HttpParams()
            .set('username', username)
            .set('password', password);
        return this.http.post<any>('/api/auth/login', body)
            .pipe(map(() => {
                this.loggedIn().subscribe(user => {
                        localStorage.setItem('currentUser', JSON.stringify(user));
                        this.currentUserSubject.next(user);
                    }
                )
            }));
    }

    addConnection() {
        return this.http.get<any>('/api/auth/get-connection')
    }

    checkLogin(password: string) {
        return this.http.post<any>('/api/auth/check-old-password', password)
            .pipe(map(() => {
                this.loggedIn().subscribe(user => {
                        // localStorage.setItem('currentUser', JSON.stringify(user));
                        // this.currentUserSubject.next(user);
                    }
                )
            }));
    }

    decryptBd(password: string) {
        return this.http.post<any>('/api/auth/decrypt-bd', password)
            .pipe(map(() => {
                this.loggedIn().subscribe(user => {
                    }
                )
            }));
    }

    isPasswordDecrypted() {
        return this.http.get<any>('/api/auth/is-bd-decrypted')
    }

    loggedIn() {
        return this.http.get<any>('/api/auth/logged-in')
            .pipe(map((user) => {
                return user;
            }));
    }

    logout() {
        // remove user from local storage to log user out
        localStorage.removeItem('currentUser');
        this.currentUserSubject.next(null);
    }
}