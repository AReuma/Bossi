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
          style="max-width: 80%"
          v-model="phoneNum"
      ></v-text-field>

      <v-btn color="DEEP_PINK"
             depressed
             :disabled = phoneCheck
             @click="clickPhoneCheck()"
             id="phone_check">인증요청</v-btn>
    </div>
  </div>
</template>

<script>
import {defineComponent} from 'vue'

export default defineComponent({
  name: "SearchIdPwView"
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