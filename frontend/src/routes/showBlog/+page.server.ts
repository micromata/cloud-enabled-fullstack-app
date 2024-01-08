import type {PageServerLoad} from "./$types";
import {env} from "$env/dynamic/public";
import type {Actions} from "@sveltejs/kit";

export const load: PageServerLoad = async ({fetch, url}) => {

    const id = url.searchParams.get("id")
    const response = await fetch(env.PUBLIC_BACKEND_URL + `v1/feed/${id}`, {
        method: "GET"
    });
    const responseData = await response.json()

    console.log(responseData);

    return await responseData;
}

export const actions:Actions = {
    default: async ({request, fetch, url, locals}) => {
        const data = await request.formData();
        const id = url.searchParams.get("id")
        const content = data.get('comment');
        const token = locals.token;

        const response = await fetch(env.PUBLIC_BACKEND_URL + `v1/feed/comment/create/${id}`, {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ content }),
        })

        if(!response.ok) {
            return {error: 'unexpected Error'};
        }
    }
}