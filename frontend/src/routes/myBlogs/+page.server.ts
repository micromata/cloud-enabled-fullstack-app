import type {Actions} from "./$types";
import {env} from "$env/dynamic/public";
import type {PageServerLoad} from "./$types";
import {setCookie} from "$lib/setCookie";

export const actions:Actions = {
    delete: async ({request, fetch, locals}) => {

        const token = locals.token;

        const data = await request.formData();
        const blogID = data.get('blogId');

        const response = await fetch(env.PUBLIC_BACKEND_URL + `v1/feed/${blogID}`, {
            method: 'DELETE',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        })

        if(!response.ok) {
            return {error: "unexpected error"};
        }
    }
}

export const load: PageServerLoad = async ({fetch}) => {
    const response = await fetch(env.PUBLIC_BACKEND_URL + "v1/myFeed", {
        method: "GET"
    });

    return await response.json();
}