import type {PageLoad} from "./$types";
import {env} from "$env/dynamic/public";

export const load: PageLoad = async ({fetch}) => {
    const json = await Promise.all([
        fetch(env.PUBLIC_BACKEND_URL + "v1/health", {
            headers: {
                'Accept': 'application/json'
            }
        }),
        fetch(env.PUBLIC_BACKEND_URL + "v1/health/database", {
            headers: {
                'Accept': 'application/json'
            }
        }),
    ]).then(async (response) => {
        return [await response[0].json(), await response[1].json()]
    });

    return {
        backend: json[0].message,
        database: json[1].message
    }
}