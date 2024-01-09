import type {PageServerLoad} from "./$types";
import {type Actions, redirect} from "@sveltejs/kit";
import {env} from "$env/dynamic/public";

export const load: PageServerLoad = async ({locals, url}) => {
    if(!locals.user){
        console.log("Access denied!")
        throw redirect(303, "/");
    }

    const id = url.searchParams.get("id")
    const response = await fetch(env.PUBLIC_BACKEND_URL + `v1/feed/${id}`, {
        method: "GET"
    });
    const responseData = await response.json()

    console.log(responseData);
    return await responseData;
}

export const actions:Actions = {
    default: async ({request, fetch, locals, url}) => {

        const id = url.searchParams.get("id")
        const token = locals.token;

        const data = await request.formData();
        const title = data.get('title');
        const preview = data.get('shortDescription');
        const description = data.get('content');
        const image = data.get('image');

        const payload = {
            title,
            preview,
            description,
            image,
        };

        console.log(payload);

        const response = await fetch(env.PUBLIC_BACKEND_URL + `v1/feed/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify(payload),
        });

        if (!response.ok) {
            return await response.json();
        }

        throw redirect(303, '/blogs?edited');

    }
}
