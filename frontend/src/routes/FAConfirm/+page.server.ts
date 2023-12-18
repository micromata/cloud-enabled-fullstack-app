import type {ServerLoad} from "@sveltejs/kit";
import {env} from "$env/dynamic/public";
import type {Actions} from "./$types";
import {setCookie} from "$lib/setCookie";
import {redirect} from "@sveltejs/kit";

export const actions:Actions = {default: async ({request, fetch, cookies, locals}) => {

            const token = locals.token;

            const data = await request.formData();
            const authToken = data.get('authToken');
            const tempToken = cookies.get('tempToken');

            const response = await fetch(env.PUBLIC_BACKEND_URL + `v1/auth/verify/${authToken}?tempToken=${tempToken}`, {
                method: "POST"
            })

            if(!response.ok){
                if (response.status === 401) {
                    return {error: "Please check your Token and try again!"}
                } else {
                    return {error: "Unexpected error!"}
                }
            }

            const responseData = await response.json();

            const accessToken = responseData.accessToken;

            const expirationDate = new Date();
            expirationDate.setDate(expirationDate.getDate() + 30);

            cookies.set('authToken', accessToken, {expires: expirationDate, httpOnly: true});

            throw redirect(302, '/')
        }
    }