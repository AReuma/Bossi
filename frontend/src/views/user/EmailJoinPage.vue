<template>
  <div>
    <email-join-view
      @register="register"
      @checkNum="checkNum"
      @checkDubId="checkDubId"
      :checkIdDub="checkIdDub"
      :checkCode="checkCode"
      :socialType="socialType"
    >
    </email-join-view>
  </div>
</template>

<script>
import {defineComponent} from 'vue'
import EmailJoinView from "@/components/user/EmailJoinView.vue";
import axios from "axios";
import {useCookies} from "vue3-cookies";
import {API_BASE_URL} from "@/constant/basic";


export default defineComponent({
  name: "EmailJoinPage",
  components: {
    EmailJoinView

  },
  data(){
    return {
      cookieName: useCookies().cookies.get('name'),
      checkCode: '',
      socialType: '',
      checkIdDub: true,
    }
  },
  methods: {
    register(payload){
      const {email, password, nickName, phoneNum, recommender, checkSMS} = payload;
      axios.post('http://localhost:7777/api/v1/users/join', {email, password, nickName, phoneNum, recommender, checkSMS})
          .then((res) => {
            //alert(res)
            console.log(res)
            this.$router.push({name: 'LoginPage'})
          })
          .catch((res) => {
            console.log(res)
          })
      console.log(payload);

    },
    checkNum(payload){
      const {phoneNum} = payload;
      alert(phoneNum)
      axios.get(API_BASE_URL+`/api/v1/users/checkPhone/${phoneNum}`)
          .then((res) => {
            //alert(res)
            console.log(res.data.num)
            console.log(res.data.socialType)
            console.log(res.data)
            this.checkCode = res.data.num;
            this.socialType = res.data.socialType;
          })
          .catch((res) => {
            console.log(res)
          })
    },
    checkDubId(payload){
      const {email} = payload;
      //alert(email)
      axios.get(API_BASE_URL + `/api/v1/users/checkId/${email}`)
          .then((res) => {
            this.checkIdDub = res.data;
            alert(this.checkIdDub)
            if(this.checkIdDub){
              alert('사용가능한 아이디 입니다.')
            }else {
              alert('사용중인 아이디 입니다. ')
            }
            console.log(res)
          })
          .catch((res) => {
            console.log(res)
          })
    }
  },
})
</script>

<style scoped>

</style>