import { Friend } from "./friend";

export interface User {
    photoUrl: string;
    friends: Friend[] | undefined;
}