<template>
  <div>
    <search-id-pw-view></search-id-pw-view>
  </div>
</template>

<script>
import {defineComponent} from 'vue'
import SearchIdPwView from "@/components/user/search/SearchIdPwView.vue";

export default defineComponent({
  name: "SearchIdPwPage",
  components: {SearchIdPwView}
})
</script>

<style scoped>

</style>