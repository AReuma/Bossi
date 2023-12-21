import {useCookies} from "vue3-cookies";
import {Base64} from "js-base64";
function ParsingInfo(token){
    const accessTokenInfo = token.split(".");

    let base64Encoded = Base64.decode(accessTokenInfo[1]);

    let result = JSON.parse(base64Encoded.toString())

    console.log(result)

    let email = result.email;
    let role = result.role;
    let nickName = result.nickName;
    let registerStatus = result.registerStatus;

    useCookies().cookies.set('role', role)
    useCookies().cookies.set('email', email)
    useCookies().cookies.set('nickName', nickName)
    useCookies().cookies.set('registerStatus', registerStatus)
}

export {
    ParsingInfo,
}