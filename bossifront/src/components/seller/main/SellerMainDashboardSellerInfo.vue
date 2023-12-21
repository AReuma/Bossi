<template>
  <div>
    <div style="display: flex; align-items: center; padding-top: 20px">
      <img id="profile_img" src="../../../assets/logo/Bossi_logo_1.png" alt="profile image"/>
      <div style="margin-left: 20px">
        <div style="font-size: 30px">스토어 이름</div>
        <div style="font-size: 18px">스토어 찜 <span style="margin-left: 4px; color: #fc9899">0</span></div>
      </div>
    </div>

  </div>
</template>

<script>
import {defineComponent} from 'vue'

export default defineComponent({
  name: "SellerMainDashboardSellerInfo"
})
</script>

<style scoped>
#profile_img {
  width: 120px;
  height: 120px;
  border-radius: 100%;
}
</style>