import {setCookie} from "$lib/setCookie";
import type {Actions} from "@sveltejs/kit";
import {env} from "$env/dynamic/public";
import {error, redirect} from "@sveltejs/kit";
import type {PageServerLoad} from "./$types";

export const actions:Actions = {
    default: async ({request, fetch, locals}) => {

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

        const response = await fetch(env.PUBLIC_BACKEND_URL + 'v1/feed/new', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify(payload),
        });

        if (!response.ok) {
            return await response.json();
        }

        throw redirect(303, '/blogs?created');

    }
}

export const load: PageServerLoad = async ({locals}) => {
    if(!locals.user){
        throw redirect(303, "/login");
    }
}