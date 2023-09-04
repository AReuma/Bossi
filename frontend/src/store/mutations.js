import {LOGOUT} from "@/store/mutation-types";
import {useCookies} from "vue3-cookies";


export default {
    [LOGOUT]() {
        useCookies().cookies.remove('email');
        useCookies().cookies.remove('nickName');
        useCookies().cookies.remove('registerStatus');
        useCookies().cookies.remove('access_token');
        useCookies().cookies.remove('refresh_token');

        location.reload();
    }
}