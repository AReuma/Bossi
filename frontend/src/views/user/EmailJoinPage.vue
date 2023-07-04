<template>
  <div>
    <email-join-view></email-join-view>
  </div>
</template>

<script>
import {defineComponent} from 'vue'
import EmailJoinView from "@/components/user/EmailJoinView.vue";

export default defineComponent({
  name: "EmailJoinPage",
  components: {
    EmailJoinView

  }
})
</script>

<style scoped>

</style>