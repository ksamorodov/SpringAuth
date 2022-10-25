import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { User } from '../_models';

@Injectable({ providedIn: 'root' })
export class UserService {
    constructor(private http: HttpClient) { }

    getAll() {
        return this.http.get<User[]>(`/api/auth/all-users`);
    }

    register(username) {
        return this.http.post(`/api/auth/register?username=` + username, {});
    }

    changePassword(id, password) {
        return this.http.post(`/api/auth/change-password?id=` + id, password);
    }

    delete(id: number) {
        return this.http.delete(`/api/auth/delete-user?id=${id}`);
    }

    block(id: number) {
        return this.http.put(`/api/auth/block-user?id=${id}`, "");
    }

    unblock(id: number) {
        return this.http.put(`/api/auth/unblock-user?id=${id}`, "");
    }
}