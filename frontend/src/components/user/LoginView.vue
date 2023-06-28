<template>
  <div id="basic">

    <div style="width: 100%; display: flex; justify-content:center;">
      <img src="../../assets/logo/Bossi_logo_3.png"  style="height: 100px;" alt="logo"/>
    </div>

    <div style="display: flex; justify-content: center; height: 18px">
      <p><mark>로그인</mark>이 필요한 서비스입니다.</p>
    </div>

    <div style="display: flex; justify-content: center; margin-top: 30px">
        <v-row style="margin: 5px 0 5px 0; width: 100%;" justify="center">
          <v-col style="padding-top: 20px">
            <hr/>
          </v-col>
          <v-col align="center" style="color: rgba(33,33,36,0.6)">
            간편 로그인/ 회원가입
          </v-col>
          <v-col style="padding-top: 20px">
            <hr/>
          </v-col>
        </v-row>
    </div>

    <div>
      <v-btn class="my_btn" style="color: #3A1D1D; height: 54px" color="#F7E600" depressed>
        <img src="../../assets/logo/kakao_logo.png" height="20" width="20" style="margin-right: 7px" alt="kakaoLogin"/>카카오 3초만에 시작하기
      </v-btn>
      <v-btn text class="my_btn" style="border: 1px solid rgba(33,33,36,0.25); margin-top: 15px; height: 54px">
        이메일로 가입하기
      </v-btn>

      <div style="display: flex; justify-content: center; width: 100%; margin-top: 15px; margin-bottom: 25px">
        <v-btn fab style="margin-right: 30px" icon>
          <img src="../../assets/logo/Naver_logo.png" height="50" width="50" />
        </v-btn>

        <v-btn fab icon>
          <img src="../../assets/logo/Google_logo.png" height="57" width="57"/>
        </v-btn>
      </div>
    </div>

    <div style="display: flex; justify-content: center; margin-top: 30px">
      <v-row style="margin: 5px 0 5px 0; width: 100%;" justify="center">
        <v-col style="padding-top: 20px">
          <hr/>
        </v-col>
        <v-col align="center" style="color: rgba(33,33,36,0.6)">
          이메일 로그인
        </v-col>
        <v-col style="padding-top: 20px">
          <hr/>
        </v-col>
      </v-row>
    </div>

    <div>
      <div style="height: 54px">
        <v-text-field
            label="아이디"
            outlined
            color="#434f58"
        ></v-text-field>
      </div>
      <div style="height: 50px; margin-top: 15px;">
        <v-text-field
            :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
            :type="show ? 'text' : 'password'"
            @click:append="show = !show"
            label="비밀번호"
            outlined
            color="#434f58"
        ></v-text-field>
      </div>
    </div>

    <div style="display: flex; justify-content: right; margin-top: 10px">
      <v-btn text color="rgba(33,33,36,0.6)">아이디/비밀번호 찾기</v-btn>
    </div>

    <div style="margin-top: 5px">
      <v-btn depressed class="my_btn"  style="height: 54px; font-size: 17px; color: white" color="DEEP_PINK">
        로그인
      </v-btn>
    </div>

  </div>
</template>

<script>
import {defineComponent} from 'vue'

export default defineComponent({
  name: "LoginView",
  data(){
    return{
      show: false
    }
  }
})
</script>

<style scoped>
#basic{
  margin: 10px 30% 0 30%;
  /*border: 1px solid pink;*/
  height: 100%;
}
mark{
  font-weight: bold;
  background: linear-gradient(to top, #fc9899 2px, transparent 50%);
  font-size: 15px;
}
.my_btn{
  width: 100%;
  font-weight: bolder;
  border-radius: 5px;
  height: 54px;
}
input {
  width: 100%;
  line-height: 1.36;
  font-size: 16px;
  box-sizing: border-box;
  height: 40px;
  border: #fc9899 2px solid;
  border-radius: 6px;
  color: #212124;
}
</style>