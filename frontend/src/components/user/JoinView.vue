<template>
  <div id="basic">
    <div style="width: 100%; display: flex; justify-content:center; margin-top: 15px">
      <img src="../../assets/logo/Bossi_logo_3.png"  style="height: 100px;" alt="logo"/>
    </div>

    <div style="display: flex; justify-content: center; margin-top: 30px">
      <v-row style="width: 100%;" justify="center">
        <v-col style="padding-top: 20px">
          <hr/>
        </v-col>
        <v-col align="center" style="color: rgba(33,33,36,0.6)">
          정말 간단한 회원가입하기
        </v-col>
        <v-col style="padding-top: 20px">
          <hr/>
        </v-col>
      </v-row>
    </div>

    <div style="padding: 30px 0">
      <div style="display: flex; justify-content: center;">
        <v-btn fab depressed color="DEEP_PINK" style="color: white; font-size: 20px;">1</v-btn>
        <hr style="width: 120px; margin-top: 26px"/>
        <v-btn fab depressed outlined disabled style="color: black; font-size: 20px">2</v-btn>

      </div>

      <div style="display: flex; justify-content: center; margin-top: 15px">
        <div style="font-size: 20px; color: #4d5159">회원가입 방법 선택하기</div>
      </div>
    </div>

    <div>
      <v-btn class="my_btn" style="color: #3A1D1D; height: 54px" color="#F7E600" depressed @click="kakaoJoin()">
        <img src="../../assets/logo/kakao_logo.png" height="20" width="20" style="margin-right: 7px" alt="kakaoLogin"/>카카오톡으로 가입하기
      </v-btn>

      <v-btn v-if="bthShow" class="my_btn" style="height: 54px; margin-top: 15px" outlined depressed @click="showBtn()">
        다른 방법으로 가입하기
      </v-btn>

      <div v-else>
        <v-btn @click="naverJoin()" class="my_btn" style="height: 54px; margin-top: 15px; background-color: #33BC03" text >
          <img src="../../assets/logo/Naver_logo.png" height="36" width="36" />&nbsp;&nbsp;네이버로 가입하기
        </v-btn>

        <v-btn @click="googleJoin()" class="my_btn" style="height: 54px; margin-top: 15px; background-color: #EFEFEF" text>
          <img src="../../assets/logo/Google_logo.png" style="outline-style: none;" height="40" width="40"/>&nbsp;&nbsp;구글로 가입하기
        </v-btn>

        <v-btn @click="githubJoin()" class="my_btn" style="height: 54px; margin-top: 15px; background-color: #000000" color="white" text>
          <img src="../../assets/logo/Github_logo.png" style="outline-style: none;" height="32" width="32"/>&nbsp;&nbsp;깃허브로 가입하기
        </v-btn>

        <v-btn class="my_btn" style="height: 54px; margin-top: 15px; color: #fc9899" outlined depressed @click="goJoinP()">
          이메일로 가입하기
        </v-btn>
      </div>
    </div>

    <div style="display: flex; justify-content: center; margin-top: 30px; color: rgba(33,33,36,0.6); ">
      <p>이미 가입하셨다면&nbsp;</p>
      <p style="text-decoration : underline; text-underline-position : under;" @click="goLongP()">바로 로그인 하기</p>
    </div>
  </div>
</template>

<script>
import {defineComponent} from 'vue'
import {GITHUB_AUTH_URL, GOOGLE_AUTH_URL, KAKAO_AUTH_URL, NAVER_AUTH_URL} from "@/constant/login";

export default defineComponent({
  name: "JoinView",
  data(){
    return{
      bthShow: true
    }
  },
  methods: {
    showBtn(){
      this.bthShow = false;
    },
    goJoinP(){
      this.$router.push({name: 'EmailJoinPage'})
    },
    goLongP(){
      this.$router.push({name: 'LoginPage'})
    },
    kakaoJoin(){
      window.open(KAKAO_AUTH_URL,
          "_blank", "width=480, height=720");
    },
    naverJoin(){
     window.open(NAVER_AUTH_URL,
      "_blank", "width=480, height=720");
    },
    googleJoin(){
      window.open(GOOGLE_AUTH_URL,
          "_blank", "width=480, height=720");
    },
    githubJoin(){
      window.open(GITHUB_AUTH_URL,
      "_blank", "width=480, height=720");
    }
  }
})
</script>

<style scoped>
#basic{
  margin: 10px 25% 0 25%;
  /*border: 1px solid pink;*/
  height: 100%;
}
.my_btn{
  width: 100%;
  font-weight: bolder;
  border-radius: 5px;
  height: 54px;
}
</style>