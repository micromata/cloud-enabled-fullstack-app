import type {Actions} from "@sveltejs/kit";
import {redirect} from "@sveltejs/kit";

export const actions: Actions = {
    default: ({cookies}) => {
        cookies.delete("authToken", {
            path: "/",
            httpOnly: true
        });
        throw redirect(302, "/");
    }
}