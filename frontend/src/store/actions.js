import {
    LOGOUT,
} from './mutation-types'

export default {
    logout({commit}){
        commit(LOGOUT)
    }
}