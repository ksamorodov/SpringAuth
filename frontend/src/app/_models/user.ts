export class User {
    id: string;
    username: string;
    password: string;
    role: string;
    blockedAt: string;
    token: string;
    validPassword: boolean;
    temporaryPassword: boolean;
}