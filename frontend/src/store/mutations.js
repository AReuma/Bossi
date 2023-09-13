import {
    ACCESS_DENIED, FETCH_CATEGORY_LIST, FETCH_PRODUCT_CONTENT, FETCH_WAITING_LIST_USERS,
    LOGOUT,
} from "@/store/mutation-types";
import {useCookies} from "vue3-cookies";


export default {
    [LOGOUT]() {
        useCookies().cookies.remove('email');
        useCookies().cookies.remove('nickName');
        useCookies().cookies.remove('registerStatus');
        useCookies().cookies.remove('access_token');
        useCookies().cookies.remove('refresh_token');

        location.reload();
    },
    [FETCH_WAITING_LIST_USERS](state, waitingListUsers){
        state.waitingListUsers = waitingListUsers;
    },
    [ACCESS_DENIED](){
        this.$router.push()
    },
    [FETCH_PRODUCT_CONTENT](state, productContent){
        state.productContent = productContent;
    },
    [FETCH_CATEGORY_LIST](state, categoryList){
        state.categoryList = categoryList;
    }
}