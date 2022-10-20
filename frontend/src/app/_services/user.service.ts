import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { User } from '../_models';

@Injectable({ providedIn: 'root' })
export class UserService {
    constructor(private http: HttpClient) { }

    getAll() {
        return this.http.get<User[]>(`/api/auth/all-users`);
    }

    register(user: User) {
        return this.http.post(`/api/auth/register`, user);
    }

    delete(id: number) {
        return this.http.delete(`/api/auth/delete-user?id=${id}`);
    }
}