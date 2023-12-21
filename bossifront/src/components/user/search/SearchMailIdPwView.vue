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
        <div style="font-size: 22px; color: #4d5159">가입시 사용한 이메일 주소를 입력하면 가입 정보를 보내드립니다.</div>
      </div>
    </div>

    <div style="display: flex;">
      <v-text-field
          label="이메일을 입력해주세요."
          outlined
          color="#434f58"
          style="height: 70px"
          v-model="email"
      ></v-text-field>
    </div>
    <p v-if="emailCheck" style="color: red">유효하지 않은 이메일입니다.</p>

    <v-btn color="DEEP_PINK"
           depressed
           @click="clickMailCheck()"
           id="phone_check">이메일로 받기</v-btn>

    <v-dialog v-model="nonexistent" persistent width="auto">
      <v-card style="padding: 30px 20px 10px 20px">
        <v-card-text style="font-size: 15px">
          <v-row>
            가입되지 않은 계정입니다.
          </v-row>
          <v-row>
            회원 가입을 시도하거나 이메일 주소를 다시 확인해주세요.
          </v-row>
        </v-card-text>
        <v-card-actions>
          <v-btn color="DEEP_PINK" style="color: white; width: 100%" @click="nonexistent = false">확인</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <v-dialog v-model="existMail" persistent width="auto">
      <v-card style="padding: 20px 20px 10px 20px; border: 1px solid red">
        <v-card-title class="justify-center" style="padding-bottom: 25px">
          이메일을 발송했습니다.
        </v-card-title>
        <v-card-text style="font-size: 15px">
          <v-row>
            {{email}}에서 계정 정보를 확인해주세요.
          </v-row>
        </v-card-text>
        <v-card-actions>
          <v-btn color="DEEP_PINK" style="color: white; width: 100%" @click="existMail = false">확인</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>

</template>

<script>
import {defineComponent} from 'vue'
import axios from "axios";
import {API_BASE_URL} from "@/constant/basic";

export default defineComponent({
  name: "SearchMailIdPwView",
  data() {
    return {
      email: '',
      emailCheck: false,
      checkIdDub: false,
      nonexistent: false,
      existMail: false
    }
  },
  methods: {
    clickMailCheck(){
      if(this.emailCheck !== true) {
        let email = this.email;
        axios.get(API_BASE_URL + `/api/v1/users/find/email/${email}`)
            .then((res) => {
              console.log(res)
              this.checkIdDub = res.data;
              if (this.checkIdDub) {
                this.nonexistent = true;
              } else {
                this.existMail = true;
              }
              console.log(res)
            })
            .catch(() => {
              alert('에러')
            })
      }
    }
  },
  watch: {
    email: function (val) { // dkfma!123@gmail.com 회원가입이 안됨.
      const email = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/

      this.emailCheck = !val.match(email);
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
  width: 100%;
  height: 54px;
  color: white;
}
</style>