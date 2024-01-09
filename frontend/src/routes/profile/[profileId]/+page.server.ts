import type {Actions, PageServerLoad} from "./$types";
import {env} from "$env/dynamic/public";
import {error} from "@sveltejs/kit";

export const load: PageServerLoad = async ({params, locals, fetch}) => {
    const profileId = params.profileId;

    let headers: Record<string, string> = {};

    if (locals.token) {
        headers['Authorization'] = "Bearer " + locals.token;
    }

    const response = await fetch(env.PUBLIC_BACKEND_URL + `v1/profile/${profileId}`, {
        headers,
        method: "GET"
    });

    if (response.ok) {
        return await response.json();
    }

    if (response.status === 404) {
        throw error(404);
    } else {
        return {error: "An unexpected error occurred."}
    }
}

export const actions: Actions = {
    follow: async ({request, fetch, locals, params}) => {
        const formData = await request.formData();
        const state = formData.get("state");

        let method = state === "true" ? "POST" : "DELETE";

        const response = await fetch(env.PUBLIC_BACKEND_URL + `v1/profile/${params.profileId}/follow`, {
            method: method,
            headers: {
                'Authorization': `Bearer ${locals.token}`
            }
        });
    }
}