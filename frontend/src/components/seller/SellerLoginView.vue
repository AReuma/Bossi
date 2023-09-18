<template>
  <div id="basic">

    <div style="width: 100%; display: flex; justify-content:center;">
      <img src="../../assets/logo/Bossi_logo_3.png"  style="height: 100px;" alt="logo"/>
    </div>


    <div style="display: flex; justify-content: center; margin-top: 30px;">
      <v-row style="margin: 5px 0 5px 0; width: 100%;" justify="center">
        <v-col style="padding-top: 40px">
          <hr/>
        </v-col>
        <v-col align="center" style="color: rgba(33,33,36,0.6); font-size: 18px; ">
          <p><mark style="background-color: PINK">작가님 전용 <br/>로그인 페이지</mark></p>
        </v-col>
        <v-col style="padding-top: 40px">
          <hr/>
        </v-col>
      </v-row>
    </div>

    <div>
      <div style="height: 54px">
        <v-text-field
            v-model="email"
            label="아이디"
            outlined
            color="#434f58"
        ></v-text-field>
      </div>
      <div style="height: 50px; margin-top: 15px;">
        <v-text-field
            v-on:keyup.enter="login"
            v-model="password"
            :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
            :type="show ? 'text' : 'password'"
            @click:append="show = !show"
            label="비밀번호"
            outlined
            color="#434f58"
        ></v-text-field>
      </div>
    </div>
    <div v-if="showError" style="font-size: small; color: red; margin: 8px 0">
      아이디(로그인 전용 아이디) 또는 비밀번호를 잘못 입력했습니다.<br/>
      입력하신 내용을 다시 확인해주세요.
    </div>

    <div style="margin-top: 25px">
      <v-btn @click="login()" depressed class="my_btn"  style="height: 54px; font-size: 17px; color: white" color="DEEP_PINK">
        로그인
      </v-btn>
    </div>
  </div>
</template>

<script>
import {defineComponent} from 'vue'

export default defineComponent({
  name: "SellerLoginView",
  props:['showError'],
  data(){
    return {
      show: false,
      email: '',
      password: ''
    }
  },
  methods: {
    login(){
      const {email, password} = this;

      this.$emit('login', {email, password})
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