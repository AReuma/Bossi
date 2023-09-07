import {
    FETCH_WAITING_LIST_USERS,
    LOGOUT,
} from './mutation-types'
import axios from "axios";
import {API_BASE_URL} from "@/constant/basic";
import {useCookies} from "vue3-cookies";

/*const config = {
    headers: {
        'Authorization': 'Bearer '+ useCookies().cookies.get('access_token'),
        'Accept' : 'application/json',
        'Content-Type': 'application/json'
    }
};*/

export default {
    logout({commit}){
        commit(LOGOUT)
    },
    fetchWaitingListUsers({commit}) {
        return axios.get(API_BASE_URL+"/api/v1/manager/waitingList", { headers: {
            'Authorization': 'Bearer '+ useCookies().cookies.get('access_token'),
            'Accept' : 'application/json',
            'Content-Type': 'application/json'
        }})
            .then((res) => {
                commit(FETCH_WAITING_LIST_USERS, res.data)
            })
            .catch((res) => {
                throw res;
        })
    }
}