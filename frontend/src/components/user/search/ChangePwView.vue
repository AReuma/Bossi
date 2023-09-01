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
          비밀번호 변경
        </v-col>
        <v-col style="padding-top: 20px">
          <hr/>
        </v-col>
      </v-row>
    </div> <!-- 비밀번호 변경 -->

    <div style="margin-top: 40px">
      <div style="display: flex; justify-content: center; margin-top: 15px">
        <div style="font-size: 22px; color: #4d5159">변경할 비밀번호를 입력해주세요.</div>
      </div>
    </div>

    <div style="display: flex; justify-content: center; margin-top: 35px;">
      <div style="width: 100%" >
        <div style="height: 100px">
          <p>이메일</p>
          <div style="display: flex">
            <v-text-field
                style="height: 60px"
                :label="email"
                disabled
                outlined
                color="#434f58"
            ></v-text-field>
          </div>
        </div>

        <div style="height: 200px; margin-top: 25px;">
          <p>비밀번호 <span style="color: red; font-weight: lighter">*</span></p>
          <v-text-field
              :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
              :type="show ? 'text' : 'password'"
              @click:append="show = !show"
              label="비밀번호 (영문+숫자+특수문자 8자 이상)"
              outlined
              style="height: 70px;"
              color="#434f58"
              v-model="password"
          ></v-text-field>

          <p v-if="passwordCheck" style="color: red">영문, 특수문자, 숫자를 포함해서 8자 이상 작성해주세요.</p>

          <v-text-field
              :append-icon="showRe ? 'mdi-eye' : 'mdi-eye-off'"
              :type="showRe ? 'text' : 'password'"
              @click:append="showRe = !showRe"
              label="비밀번호 확인"
              outlined
              style="height: 70px;"
              color="#434f58"
              v-model="password2"
          ></v-text-field>
          <p v-if="passwordCheck2" style="color: red">비밀번호가 서로 다릅니다.</p>
        </div>

        <div style="margin-top: 15px">
          <v-btn @click="changeBtn()" depressed class="my_btn"  style="height: 54px; font-size: 17px; color: white; width: 100%" color="DEEP_PINK">
            비밀번호 변경
          </v-btn>
        </div>

      </div>
    </div>
  </div>
</template>

<script>
import {defineComponent} from 'vue'

export default defineComponent({
  name: "ChangePwView",
  props: ['email'],
  data(){
    return {
      password: '',
      password2: '',
      passwordCheck: false,
      passwordCheck2: false,
      show: false,
      showRe: false,
    }
  },
  methods: {
    changeBtn(){
      if (this.passwordCheck === false && this.passwordCheck2 === false){
        const {email, password} = this;

        this.$emit('changePw', {email, password})
      }
    }
  },
  watch: {
    password: function (val){
      const password =/(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\W)(?=\S+$).{8,16}/

      this.passwordCheck = !val.match(password);
    },
    password2: function (val){
      this.passwordCheck2 = val !== this.password;
    },
  }
})
</script>

<style scoped>
#basic{
  margin: 10px 27% 0 27%;
  height: 100%;
}
</style>