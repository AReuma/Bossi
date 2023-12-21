<template>
  <div>
    <join-view></join-view>
  </div>
</template>

<script>
import {defineComponent} from 'vue'
import JoinView from "@/components/user/JoinView.vue";

export default defineComponent({
  name: "JoinPage",
  components: {JoinView},
  methods: {
    test2(){
    alert('test2');
  }
  }
})
</script>

<style scoped>

</style>