import type {Cookies} from "@sveltejs/kit";
import {error, redirect} from "@sveltejs/kit";

export async function setCookie(response: Response, cookies: Cookies) {

    if (!response.ok) {
        throw error(400);
    }
    const responseData = await response.json();
    const authToken = responseData.accessToken;
    const tempAuthKey = responseData.tempAuthKey;
    const needsTwoFa = responseData.needs2FA;

// Setze das Cookie mit einer Gültigkeitsdauer von 30 Tagen
    const expirationDate = new Date();
    expirationDate.setDate(expirationDate.getDate() + 30);

    const expirationDateShort = new Date();
    expirationDateShort.setTime(expirationDateShort.getTime() + 60 * 60 * 1000);

    if(authToken !== null) {
        cookies.set('authToken', authToken, {expires: expirationDate, httpOnly: true, path: "/"});
    }

    if(tempAuthKey !== null) {
        cookies.set('tempToken', tempAuthKey, {expires: expirationDateShort,httpOnly: true, path: "/"})
    }

    if(needsTwoFa === true)
    {
        throw redirect(302, '/FAConfirm')
    }

    throw redirect(302, '/');
}