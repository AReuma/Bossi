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
          계정 확인
        </v-col>
        <v-col style="padding-top: 20px">
          <hr/>
        </v-col>
      </v-row>
    </div> <!-- 계정 확인 -->

    <div style="display: flex; justify-content: center; margin-top: 35px">
      <div>
        <v-img src="../../../assets/logo/Bossi_logo_3.png" height="120" width="120" style="border-radius: 70%; background-color: pink"/>
      </div>
    </div>

    <div>
      <div style="display: flex; justify-content: center; margin-top: 30px">
        <div>
          {{phone}} 로 회원가입한 계정을 찾았습니다.
        </div>
      </div>
      <div style="display: flex; justify-content: center; margin-top: 10px">
        <div>
          {{ email }}
        </div>
      </div>
      <div style="display: flex; justify-content: center; margin-top: 10px">
        <div v-if="socialType === 'GENERAL'">
          계정 타입: 일반 계정
        </div>
        <div v-else>
          계정 타입: {{ socialType }}
        </div>
      </div>
    </div>

    <div style="display: flex; justify-content: center; margin-top: 35px; height: 50px;">
      <v-btn v-if="socialType === 'GENERAL'"
            color="DEEP_PINK"
            depressed
            class="my_btn" style="font-size: 15px; width: 100%; height: 100%" @click="changePw()">비밀번호 찾기</v-btn>
      <v-btn v-else
          color="DEEP_PINK"
          depressed
          class="my_btn" style="font-size: 15px; width: 100%; height: 100%" @click="gLogin()">sns로 로그인하기</v-btn>
    </div>

    <hr style="margin-top: 20px; border: 1px dashed #bbb">

  </div>
</template>

<script>
import {defineComponent} from 'vue'

export default defineComponent({
  name: "SearchIdPwFoundView",
  props: ['email', 'socialType', 'phone'],
  data(){
    return{

    }
  },
  methods: {
    gLogin(){
      this.$router.push({name: "LoginPage"})
    },
    changePw(){
      this.$router.push({name: "ChangePwPage", params: {email: this.email}})
    }
  }
})
</script>

<style scoped>
#basic{
  margin: 10px 27% 0 27%;
  height: 100%;
}
.my_btn{
  font-size: 16px;
  color: white;
  align-content: center;
}
</style>