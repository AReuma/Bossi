<template>
  <div id="basic">
    <div style="display: flex; float: left; width: 100%;">
      <manger-category></manger-category>
      <!--      <manager-waiting-list-view></manager-waiting-list-view>-->
      <manager-waiting-list-view v-if="index"></manager-waiting-list-view>
      <router-view v-else></router-view>
    </div>
  </div>
</template>

<script>
import {defineComponent} from 'vue'
import MangerCategory from "@/components/manager/auth/MangerCategory.vue";
import ManagerWaitingListView from "@/components/manager/auth/ManagerWaitingListView.vue";

export default defineComponent({
  name: "ManagerMainPage",
  components: {ManagerWaitingListView, MangerCategory, },
  data(){
    return {
      index: true
    }
  },
  mounted() {
    if(this.$route.path.search([/0-9/]) !== -1) this.index = false;
    console.log(this.$route.path.search([/0-9/]))
  }
})
</script>

<style scoped>
#basic{
  margin: 0 5% 0 5%;
  height: 100%;
}
</style>