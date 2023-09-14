<template>
  <div style="margin: 0 12%; display: flex; align-content: center; justify-content: center; background-color: rgba(216, 204, 204, 0.47);">
    <div id="title" style="">
      <div id="bColor">B</div>
      <div id="ossiColor">ossi 작품 등록 :)</div>
    </div>
  </div>
</template>

<script>
import {defineComponent} from 'vue'

export default defineComponent({
  name: "SellerHeaderView"
})
</script>

<style scoped>
#basic{
  height: 70px;
}
#bColor{
  color: #fc9899;
  font-family: ONE-Mobile-POP, serif;
  font-size: 62px;
  float: left;
}
#ossiColor{
  color: #3d4148;
  font-family: GangwonEduSaeeum_OTFMediumA, serif;
  font-size: 62px;
  float: left;
}
</style>