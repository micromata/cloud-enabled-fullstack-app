import type {Actions} from "./$types";
import {env} from "$env/dynamic/public";
import {setCookie} from "$lib/setCookie";

export const actions:Actions = {
    username: async ({request, fetch, cookies}) => {

        const data = await request.formData();
        const username = data.get('username');
        const password = data.get('password');

        const response = await fetch(env.PUBLIC_BACKEND_URL + 'v1/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ username, password }),
        });

        if (!response.ok) {
            if (response.status === 401) {
                return {error: "Username or Password is wrong."}
            } else {
                return {error: "Unexpected error!"}
            }
        }
  
        await setCookie(response, cookies);
    },
    sso: async ({request, cookies}) => {
        let formData = await request.formData();

        const sso = formData.get("sso");

        const response = await fetch(env.PUBLIC_BACKEND_URL + "v1/auth/sso/google", {
            method: "POST",
            body: JSON.stringify({idToken: sso}),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            return {error: "Unexpected error!"}
        }

        await setCookie(response, cookies);
    }
}