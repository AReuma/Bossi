import {
    ACCESS_DENIED, FETCH_CATEGORY_LIST, FETCH_LIVER_ORDER_PRODUCT, FETCH_PRODUCT_CONTENT, FETCH_WAITING_LIST_USERS,
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

        const parser = new DOMParser();
        const doc = parser.parseFromString(productContent.content, 'text/html');

        const imageTags = doc.querySelectorAll('img');

        imageTags.forEach(img => {
            img.style.maxWidth = '100%'; // 최대 너비 설정
            img.style.height = 'auto';   // 세로 비율 유지
        });

        state.imgData = doc.documentElement.outerHTML;
    },
    [FETCH_CATEGORY_LIST](state, categoryList){
        state.categoryList = categoryList;
    },
    [FETCH_LIVER_ORDER_PRODUCT](state, liverOrderProduct){
        state.liverOrderProduct = liverOrderProduct
    },
}