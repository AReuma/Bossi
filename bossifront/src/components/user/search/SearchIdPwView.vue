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

    <div>
      <div style="margin-top: 15px">
        <v-btn @click="phone()" text class="my_btn" style="border: 1px solid rgba(33,33,36,0.25); margin-top: 15px; height: 54px">
          전화번호로 찾기
        </v-btn>

        <v-btn @click="mail()" text class="my_btn" style="border: 1px solid rgba(33,33,36,0.25); margin-top: 15px; height: 54px">
          이메일로 찾기
        </v-btn>
      </div>
    </div>

  </div>
</template>

<script>
import {defineComponent} from 'vue'

export default defineComponent({
  name: "SearchIdPwView",
  data(){
    return{

    }
  },
  methods: {
    phone(){
      this.$router.push({name: 'SearchPhoneIdPwPage'})
    },
    mail(){
      this.$router.push({name: 'SearchMailIdPwPage'})
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
  width: 100%;
  font-weight: bolder;
  border-radius: 5px;
  height: 54px;
}
</style>