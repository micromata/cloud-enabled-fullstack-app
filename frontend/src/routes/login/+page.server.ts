import type {Actions} from "./$types";
import {env} from "$env/dynamic/public";

export const actions:Actions = {
default: async ({request, fetch}) => {

    const data = await request.formData();
    const username = data.get('username');
    const password = data.get('password');

    const response = await fetch(env.PUBLIC_BACKEND_URL, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ username, password }),
    });

    if (!response.ok) {
        throw new Error('An error occurred during the API call.');
    }
}
}