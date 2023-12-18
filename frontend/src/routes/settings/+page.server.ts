import type {ServerLoad} from "@sveltejs/kit";
import {env} from "$env/dynamic/public";

export const load:ServerLoad = async ({fetch, locals}) => {

    const token = locals.token;

    console.log("Test");

    const response = await fetch(env.PUBLIC_BACKEND_URL + "v1/user/settings", {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`
        }
    })

    const responseValid = await response.json();

    console.log(responseValid);

    const activeSettings = responseValid.activeSettings;
    if (activeSettings.includes("TWO_FACTOR_AUTH")) {
        return {value: true};
    }

}