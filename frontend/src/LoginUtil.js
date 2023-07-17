import {useCookies} from "vue3-cookies";
import {Base64} from "js-base64";
function ParsingInfo(token){
    const accessTokenInfo = token.split(".");

    let base64Encoded = Base64.decode(accessTokenInfo[1]);

    let result = JSON.parse(base64Encoded.toString())

    let name = result.name;
    let registerStatus = result.registerStatus;

    useCookies().cookies.set('name', name)
    useCookies().cookies.set('registerStatus', registerStatus)
}

export {
    ParsingInfo,
}