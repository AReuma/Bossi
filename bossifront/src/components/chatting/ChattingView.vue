<template>
  <div style="width: 100%; height: 500px; border-left: 1px solid black; position: relative">
    <div style="display: flex; width: 100%; height: 60px; border-bottom: 1px solid black; padding: 10px">
      <div style="font-size: 20px; font-weight: bold; width: 30%">
        <div style="width: 100%; height: 40px; display: flex">
          <v-img src="../../assets/logo/Bossi_logo_3.png" style="border-radius: 50%; max-width: 45px; max-height: 45px; border: 1px solid black" />
          <div style="margin-left: 5px">뚜뚜스토어</div>
          <v-icon>mdi-home-outline</v-icon>
        </div>
      </div>
      <div style="width: 70%; text-align: end; padding-top: 8px">
        <v-btn>팔로우</v-btn>
        <v-btn>나가기</v-btn>
      </div>
    </div>

    <div style="background-color: #F2F2F2; width: 100%; height: 400px">
      asdfsdf
    </div>

    <div class="bottom-div">
      <v-text-field placeholder="메시지를 입력하세요." solo>
      </v-text-field>

      <v-btn>전송</v-btn>
    </div>
  </div>
</template>

<script>
import {defineComponent} from 'vue'

export default defineComponent({
  name: "ChattingView"
})
</script>

<style scoped>
.bottom-div {
  position: absolute;
  display: flex;
  bottom: 0;
  width: 100%;
  height: 100px;
  //background-color: #f0f0f0;
  border-top: 1px solid black;
  padding: 10px;
}
</style>