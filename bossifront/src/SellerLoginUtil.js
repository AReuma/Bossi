import {useCookies} from "vue3-cookies";
import {Base64} from "js-base64";
function ParsingSellerInfo(token){
    const accessTokenInfo = token.split(".");

    let base64Encoded = Base64.decode(accessTokenInfo[1]);

    let result = JSON.parse(base64Encoded.toString())

    console.log(result)

    let email = result.email;
    let role = result.role;
    let sellerId = result.seller_id;

    useCookies().cookies.set('role', role)
    useCookies().cookies.set('email', email)
    useCookies().cookies.set('sellerId', sellerId)
}

export {
    ParsingSellerInfo,
}