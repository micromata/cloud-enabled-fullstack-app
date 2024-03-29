import type {Handle} from "@sveltejs/kit"

export const handle: Handle = async ({event, resolve}) => {

    let cookies = event.cookies;
    let authToken = cookies.get("authToken");
    if (authToken) {
        let {username, email, sub} = parseJwt(authToken);

        event.locals.user = {
            username, email, id: sub
        }
        event.locals.token = authToken;
    } else {
        event.locals.user = undefined;
        event.locals.token = undefined;
    }

    const response = await resolve(event);

    return response;
}

function parseJwt (token: string) {
    return JSON.parse(Buffer.from(token.split('.')[1], 'base64').toString());
}