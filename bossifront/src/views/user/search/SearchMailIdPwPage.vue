<template>
  <div>
    <search-mail-id-pw-view></search-mail-id-pw-view>
  </div>
</template>

<script>
import {defineComponent} from 'vue'
import SearchMailIdPwView from "@/components/user/search/SearchMailIdPwView.vue";

export default defineComponent({
  name: "SearchMailIdPwPage",
  components: {SearchMailIdPwView}
})
</script>

<style scoped>

</style>