import {type Actions, redirect, type ServerLoad} from "@sveltejs/kit";
import {env} from "$env/dynamic/public";
import {redirect} from "@sveltejs/kit";

export const load: ServerLoad = async ({fetch, locals}) => {

    if(!locals.user){
        console.log("Access denied!")
        throw redirect(303, "/login");
    }

    if(!locals.user){
        console.log("Access denied!")
        throw redirect(303, "/login");
    }

    const token = locals.token;


    const response = await fetch(env.PUBLIC_BACKEND_URL + "v1/user/settings", {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`
        }
    })

    const response1 = await fetch(env.PUBLIC_BACKEND_URL + "v1/user/sso", {
        method: "GET",
        headers: {
            'Authorization': `Bearer ${token}`
        }
    });

    const responseValid = await response.json();
    const ssoProviders = await response1.json();

    const activeSettings = responseValid.activeSettings;
    if (activeSettings.includes("TWO_FACTOR_AUTH")) {
        return {value: true, ssoProviders};
    }


    return {ssoProviders};
}

export const actions: Actions = {
    deleteSSO: async ({locals, request, fetch}) => {
        if (!locals.token) {
            throw redirect(302, "/login");
        }

        const formData = await request.formData();
        const providerId = formData.get("providerId");

        if (!providerId) {
            return {error: "Invalid request!"}
        }

        const response = await fetch(env.PUBLIC_BACKEND_URL + `v1/user/sso/remove/${providerId}`, {
            method: "DELETE",
            headers: {
                'Authorization': `Bearer ${locals.token}`
            }
        });

        if (!response.ok) {
            if (response.status === 404) {
                return {error: "SSO Provider not found!"}
            }
            return {error: "An unexpected error occurred."}
        }

        return {success: true};
    },
    disable2FA: async ({fetch, locals}) => {
        const token = locals.token;

        const response = await fetch(env.PUBLIC_BACKEND_URL + 'v1/user/2fa/disable', {
            method: 'delete',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        })

        if (!response.ok) {
            return {error: "Unexpected Error"};
        }
    }
}