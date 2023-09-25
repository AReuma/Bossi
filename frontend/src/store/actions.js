import {
    FETCH_CATEGORY_LIST, FETCH_DIRECT_ORDER_LIST, FETCH_LIVER_ORDER_PRODUCT,
    FETCH_PRODUCT_CONTENT,
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
    },
    fetchProductContent({commit}, productId) {
        return axios.get(API_BASE_URL+`/api/v1/product/${productId}`)
            .then((res) => {
                commit(FETCH_PRODUCT_CONTENT, res.data)
            })
            .catch((res) => {
                console.log(res)
            })
    },
    fetchCategoryList({commit}) {
        return axios.get(API_BASE_URL+'/api/v1/seller/product/category')
            .then((res) => {
                commit(FETCH_CATEGORY_LIST, res.data)
            })
    },
    fetchLiverOrderProduct({commit}) {
        return axios.get(API_BASE_URL+'/api/v1/product/liverOrderList')
            .then((res) => {
                commit(FETCH_LIVER_ORDER_PRODUCT, res.data)
            })
    },
    updateImgStyleData({ commit }, data) {
        console.log(data)
        commit('setImgStyleData', data);
    },
    fetchDirectOrderList({commit}, {productId, options, optionCount}){
        console.log(productId +"id:"+"options"+options)
        return axios.post(API_BASE_URL+'/api/v1/cart/directBuy', {productId, options, optionCount})
            .then((res) => {
                commit(FETCH_DIRECT_ORDER_LIST, res.data)
            })
    }
}