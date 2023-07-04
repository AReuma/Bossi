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
        <v-btn fab depressed outlined disabled style="color: white; font-size: 20px;">1</v-btn>
        <hr style="width: 120px; margin-top: 26px"/>
        <v-btn fab depressed color="DEEP_PINK" style="color: black; font-size: 20px">2</v-btn>
      </div>

      <div style="display: flex; justify-content: center; margin-top: 15px">
        <div style="font-size: 22px; color: #4d5159">가입 정보 입력하기</div>
      </div>
    </div>


  </div>
</template>

<script>
import {defineComponent} from 'vue'

export default defineComponent({
  name: "EmailJoinView"
})
</script>

<style scoped>
#basic{
  margin: 10px 20% 0 20%;
  border: 1px solid pink;
  height: 100%;
}
</style>