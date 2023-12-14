import type {Actions} from "./$types";
import {env} from "$env/dynamic/public";
import { goto } from '$app/navigation';

export const actions:Actions = {
default: async ({request, fetch, cookies}) => {

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
    const responseData = await response.json();
    const authToken = responseData.token;

    // Setze das Cookie mit einer Gültigkeitsdauer von 30 Tagen
    const expirationDate = new Date();
    expirationDate.setDate(expirationDate.getDate() + 30);

    cookies.set('authToken', authToken, { expires: expirationDate });

    goto('/');

}
}