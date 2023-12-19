import type {PageServerLoad} from "./$types";
import {redirect} from "@sveltejs/kit";
import {env} from "$env/dynamic/public";
import {setCookie} from "$lib/setCookie";

export const load: PageServerLoad = async ({locals, params, url, fetch, cookies}) => {
    const provider = params.provider;

    if (provider !== "github") {
        throw redirect(302, "/login");
    }

    const response = await fetch(env.PUBLIC_BACKEND_URL + `v1/auth/sso/${provider}`, {
        method: "POST",
        body: JSON.stringify({idToken: url.searchParams.get("code")}),
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
        }
    });

    if (!response.ok) {
        throw redirect(302, `/login?error=${(await response.json()).error}`);
    }

    await setCookie(response, cookies)
}