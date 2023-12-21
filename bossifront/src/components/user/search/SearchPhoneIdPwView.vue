<template>
  <div id="basic">
    <div style="width: 100%; display: flex; justify-content:center; margin-top: 20px">
      <img src="../../../assets/logo/Bossi_logo_3.png"  style="height: 100px;" alt="logo"/>
    </div> <!--로고-->

    <div style="display: flex; justify-content: center; margin-top: 30px">
      <v-row style="width: 100%;" justify="center">
        <v-col style="padding-top: 20px">
          <hr/>
        </v-col>
        <v-col align="center" style="color: rgba(33,33,36,0.6)">
          아이디/비밀번호 찾기
        </v-col>
        <v-col style="padding-top: 20px">
          <hr/>
        </v-col>
      </v-row>
    </div> <!-- 아이디/비밀번호 찾기 -->

    <div style="padding: 30px 0; margin: 25px">

      <div style="display: flex; justify-content: center;">
        <div style="font-size: 22px; color: #4d5159">Bossi 계정 연동 전화번호를 입력해주세요.</div>
      </div>
    </div>

    <div style="display: flex;">
      <v-text-field
          label="-를 제외한 번호만 입력해주세요."
          outlined
          color="#434f58"
          style="max-width: 80%; height: 70px"
          v-model="phoneNum"
      ></v-text-field>

      <v-btn color="DEEP_PINK"
             depressed
             :disabled = sendPhoneBtn
             @click="clickPhoneCheck()"
             id="phone_check">인증요청</v-btn>
    </div>
    <p v-if="!phoneCheck" style="color: red">필수항목입니다. 전화번호를 작성해주세요.</p>

    <div v-if="this.checkCode !== ''" style="height: 65px; margin-top: 10px;">
      <!--      <div style="height: 65px; margin-top: 10px;">-->
      <div style="display: flex;">
        <v-text-field
            label="인증코드를 입력해주세요"
            outlined
            color="#434f58"
            style="width: 100%"
            v-model="phoneDubCode"
            :disabled="successCheck"
        ></v-text-field>

        <div style="padding-top: 15px; padding-left: 10px; color: rgba(37,37,37,0.72)">
          {{prettyTime()}}
        </div>

        <v-btn color="DEEP_PINK"
               @click="phoneDubCheck()"
               :disabled="successCheck"
               id="phone_check">확인</v-btn>
      </div>
    </div>
    <p v-if="expireCode" style="color: red">인증요청 시간이 지났습니다. 재요청 해주세요.</p>
  </div>
</template>

<script>
import {defineComponent} from 'vue'
import axios from "axios";
import {API_BASE_URL} from "@/constant/basic";
import {EXPIRE_TIME} from "@/constant/login";

export default defineComponent({
  name: "SearchPhoneIdPwView",
  data() {
    return {
      phoneNum: '',
      phoneCheck: true,
      timeCounter : EXPIRE_TIME,
      expireCode: false,
      sendPhoneBtn: true,
      successCheck: false,
      phoneDubCode: '',
      checkCode: '',
      socialType: '',
      email: ''
    }
  },
  methods: {
    clickPhoneCheck(){
      this.sendPhoneBtn = true;
      this.sendPhoneNum = true;
      if(this.expireCode === true) {
        this.expireCode = false;

      }
      const {phoneNum} = this
      //this.$emit('checkNum', {phoneNum})
      axios.get(API_BASE_URL+`/api/v1/users/find/phone/${phoneNum}`)
          .then((res) => {
            //alert(res)
            console.log(res.data.num)
            console.log(res.data.socialType)
            console.log(res.data)
            this.checkCode = res.data.num;
            this.socialType = res.data.socialType;
            this.email = res.data.email;

            this.start()
          })
          .catch((res) => {
            console.log(res)
          })
    },start(){
      // 1초에 한번씩 start 호출
      this.polling = setInterval( () =>{
        this.timeCounter-- //1찍 감소
        if (this.timeCounter <= 0) {
          this.timeStop();
          this.timeCounterReset();
          this.expireCode = true;
          // 인증 코드 다시 보내야함.
        }
      },1000) // 1000ms, 1초
    },
    prettyTime() {
      let time = this.timeCounter / 60
      let minutes = parseInt(time)
      let secondes = Math.round((time - minutes) * 60)
      return this.pad(minutes, 2) + ":" + this.pad(secondes, 2)
    },
    // 2자리수로 만들어줌 09,08...
    pad(n, width) {
      n = n + '';
      return n.length >= width ? n : new Array(width - n.length + 1).join('0') + n
    },
    timeStop() {
      clearInterval(this.polling)
    },
    timeCounterReset(){
      this.sendPhoneBtn = false;
      this.sendPhoneNum = false;
      this.checkCode = '';
      this.phoneNum = '';
      this.phoneDubCode = '';
      this.timeCounter = EXPIRE_TIME;
    },
    phoneDubCheck(){
      this.timeStop();
      //
      this.$router.push({name: 'SearchIdPwFoundPage', params: {socialType: this.socialType, email: this.email, phoneNum: this.phoneNum}})
      if(this.socialType === "NEW_MEM"){
        this.timeCounter = 0;
        if(this.phoneDubCode === this.checkCode){
          this.phoneCheckData = "인증 완료 되었습니다."
          this.phoneCheckDialog = true;

          this.successCheck = true;
          this.phoneCheck = true;
          this.existsPhoneDialog = false;
        }else {
          this.phoneCheckData = "인증번호가 일치하지 않습니다.\n확인 후 다시 입력해주세요."
          this.phoneDubCode = "";
          this.phoneCheckDialog = true
        }
      }else {
        this.timeCounterReset();
        if(this.socialType === "GENERAL") {
          this.phoneCheckData = '회원가입이 된 전화번호 입니다.';
        }else {
          this.phoneCheckData = "이미 " + this.socialType + "으로 가입된 회원의 전화번호 입니다.\n"
        }
        this.existsPhoneDialog = true;
      }
    },
  },
  watch: {
    phoneNum: function (val){
      const phone = /\d{3}\d{4}\d{4}/g;
      //console.log(val)
      if(val.length === 0) this.phoneCheck = true;
      else if(!val.match(phone)) this.phoneCheck = false;
      else if(val.match(phone)) this.phoneCheck = true;

      this.sendPhoneBtn = !(val.match(phone))
    },
  }
})
</script>

<style scoped>
#basic{
  margin: 10px 27% 0 27%;
  height: 100%;
}
#phone_check{
  font-size: 16px;
  width: 18%;
  height: 54px;
  color: white;
  align-content: center;
  margin-left: 20px;
}
</style>