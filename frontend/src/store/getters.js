import {useCookies} from "vue3-cookies";
import states from "@/store/states";

export default {
    isLogin (){
        return useCookies().cookies.get("access_token") !== null;
    },
    getImgData() {
        return states.imgData;
    },
    getOptionCount() {
        return states.directOptionCount;
    }
}