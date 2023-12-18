import {writable} from "svelte/store";

export const auth = writable<Auth | null>(null);

export type Auth = {
    username: string,
    email: string
}