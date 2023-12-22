import {writable} from "svelte/store";

export const auth = writable<Auth | null>(null);

export type Auth = {
    id: number,
    username: string,
    email: string
}