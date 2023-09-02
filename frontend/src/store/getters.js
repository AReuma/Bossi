import {useCookies} from "vue3-cookies";

export default {
    isLogin (){
        return useCookies().cookies.get("access_token") !== null;
    }
}