import {useCookies} from "vue3-cookies";
import states from "@/store/states";

export default {
    isLogin (){
        return useCookies().cookies.get("access_token") !== null;
    },
    getImgData(state) {
        //console.log(states.imgData)
        return state.imgData;
    },
    getOptionCount() {
        return states.directOptionCount;
    }
}