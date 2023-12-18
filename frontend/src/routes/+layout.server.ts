import type {ServerLoad} from "@sveltejs/kit";
import type {Auth} from "$lib/user";

export const load: ServerLoad = ({locals}): {user: Auth | null} => {
    return {user: locals.user ?? null}
}