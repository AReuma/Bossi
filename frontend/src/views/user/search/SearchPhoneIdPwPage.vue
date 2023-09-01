<template>
  <div>
    <search-phone-id-pw-view></search-phone-id-pw-view>
  </div>
</template>

<script>
import {defineComponent} from 'vue'
import SearchPhoneIdPwView from "@/components/user/search/SearchPhoneIdPwView.vue";

export default defineComponent({
  name: "SearchPhoneIdPwPage",
  components: {SearchPhoneIdPwView}
})
</script>

<style scoped>

</style>