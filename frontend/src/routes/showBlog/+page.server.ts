import type {PageServerLoad} from "./$types";
import {env} from "$env/dynamic/public";

export const load: PageServerLoad = async ({fetch, url}) => {

    const id = url.searchParams.get("id")
    const response = await fetch(env.PUBLIC_BACKEND_URL + `v1/feed/${id}`, {
        method: "GET"
    });
    const responseData = await response.json()
    console.log(responseData);

    return await responseData;


}