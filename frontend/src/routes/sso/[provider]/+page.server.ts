import type {PageServerLoad, PageServerLoadEvent} from "./$types";
import {redirect} from "@sveltejs/kit";
import {env} from "$env/dynamic/public";
import {setCookie} from "$lib/setCookie";

export const load: PageServerLoad = async (event) => {
    if (event.url.searchParams.has("settings")) {
        await addProvider(event);
    } else {
        await ssoLogin(event);
    }

}

async function addProvider ({locals, params, url}: PageServerLoadEvent) {
    if (!locals.token) {
        throw redirect(302, "/login");
    }

    const provider = params.provider;

    const idToken = url.searchParams.get("code");

    const response = await fetch(env.PUBLIC_BACKEND_URL + `v1/user/sso/new/${provider}`, {
        method: "POST",
        body: JSON.stringify({idToken}),
        headers: {
        'Content-Type': 'application/json',
            'Accept': 'application/json',
            'Authorization': `Bearer ${locals.token}`
        }
    });

    if (!response.ok) {
        let error = "An unknown error occurred.";
        if (response.status === 409) {
            error = "This SSO Account is already linked to a profile";
        } else if (response.status === 401) {
            error = "Invalid session! Try again.";
        }
        throw redirect(302, `/settings?sso_error=${error}`);
    }

    throw redirect(302, "/settings?sso_success")
}

async function ssoLogin({params, url, fetch, cookies}: PageServerLoadEvent) {
    const provider = params.provider;

    const idToken = url.searchParams.get("code");

    const response = await fetch(env.PUBLIC_BACKEND_URL + `v1/auth/sso/${provider}`, {
        method: "POST",
        body: JSON.stringify({idToken}),
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        }
    });

    if (!response.ok) {
        let error = (await response.json()).error;
        throw redirect(302, `/login?error=${JSON.stringify(error)}`);
    }

    await setCookie(response, cookies)
}