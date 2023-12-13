import type {Actions} from "./$types";
import {env} from "$env/dynamic/public";

export const actions:Actions = {
    default: async ({request, fetch}) => {

        const data = await request.formData();
        const username = data.get('username');
        const email = data.get('email')
        const password = data.get('password');
        const passwordConfirm = data.get('passwordConfirm');
    }
}