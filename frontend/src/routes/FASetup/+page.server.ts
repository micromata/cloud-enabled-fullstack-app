import type {ServerLoad} from "@sveltejs/kit";
import {env} from "$env/dynamic/public";
import type {Actions} from "./$types";
import {setCookie} from "$lib/setCookie";
import {redirect} from "@sveltejs/kit";

export const load: ServerLoad = async ({fetch, locals, cookies}) => {

    const token = locals.token;

    const response = await fetch(env.PUBLIC_BACKEND_URL + 'v1/user/2fa/setup', {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`
        }
    })

    let data = await response.json();
    console.log(data);

    if(!response.ok) {
        if(response.status === 401) {
            //nicht auth cookie löschen
            return {error: 'Not Authenticated'}
        }
        else if (response.status === 409) {
            //hat schon 2fa
            return {error: '2FA is active'}
        }
        else {
            return {error: 'unexpected Error'}
        }
    }

    const expirationDateShort = new Date();
    expirationDateShort.setTime(expirationDateShort.getTime() + 60 * 60 * 1000);
    const tempAuthKey = data.tempToken;

    cookies.set('tempToken', tempAuthKey, {expires: expirationDateShort,httpOnly: true})

    return data;
}


export const actions:Actions = {default: async ({request, fetch, cookies, locals}) => {

        const token = locals.token;

        const data = await request.formData();
        const authToken = data.get('authToken');
        const tempToken = cookies.get('tempToken');

        const response = await fetch(env.PUBLIC_BACKEND_URL + `v1/user/2fa/verify/${authToken}?tempToken=${tempToken}`, {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        })

        const respondeValid = await response.json();

        console.log(respondeValid);

        if(respondeValid.valid) {
            throw redirect(303, "/settings");
        }

    }
}

