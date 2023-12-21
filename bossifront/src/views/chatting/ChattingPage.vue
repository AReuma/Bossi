<template>
  <div>
    <home-header></home-header>
    <div class="content-wrapper">
      <chatting-list-view style="flex: 0.5"></chatting-list-view>
      <chatting-view style="flex: 1.5; "></chatting-view>
    </div>
  </div>
</template>

<script>
import {defineComponent} from 'vue'
import ChattingListView from "@/components/chatting/ChattingListView.vue";
import ChattingView from "@/components/chatting/ChattingView.vue";
import HomeHeader from "@/components/home/HomeHeader.vue";

export default defineComponent({
  name: "ChattingPage",
  components: {HomeHeader, ChattingView, ChattingListView}
})
</script>

<style scoped>
.content-wrapper {
  display: flex;
  max-width: 1200px;
  margin: 20px auto;
  border: 1px solid black;
}
</style>