<template>
  <div style="width: 100%; height: 500px;">
    <div style="display: flex; width: 100%; height: 60px; border-bottom: 1px solid black; padding: 10px">
      <div style="font-size: 26px; font-weight: bold; width: 30%">메세지</div>
      <div style="width: 70%; text-align: end; padding-top: 8px">
        <v-icon>mdi-message-plus-outline</v-icon>
        <v-icon style="padding-left: 12px">mdi-cog-outline</v-icon>
      </div>
    </div>
    <div>
      <div v-for="(chatting, index) in chattingRoom" :key="index" style="height: 80px; padding: 10px; margin-top: 10px">
        <div style="display: flex">
          <div style="width: 56px; height: 60px;">
            <v-img src="../../assets/logo/Bossi_logo_3.png" style="border-radius: 50%; width: 50px; height: 50px; border: 1px solid black" />
          </div>

          <div style="margin-left: 10px; height: 60px; width: 100%">
            <div style="display: flex; height: 30px;">
              <div style="flex: 1.2; font-size: 15px">
                {{chatting}}
              </div>
              <div style="flex: 0.8; font-size: 12px; text-align: end; color: #ADADAD">
                05월 01일
              </div>
            </div>

            <div style="font-size: 12px; height: 50px">
              마지막 이야기 저장했다~~~
            </div>
        </div>

        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {defineComponent} from 'vue'

export default defineComponent({
  name: "ChattingListView",
  data(){
    return {
      chattingRoom: ["뚜뚜스토어", "니니스토어", "파파오" ]
    }
  }
})
</script>

<style scoped>

</style>