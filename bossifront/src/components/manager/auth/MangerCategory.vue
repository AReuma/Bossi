<template>
  <div style=" width: 40%">
    <div style="display: flex; align-items: center; justify-content: center; margin-top: 50px">
      <img id="profile_img" src="../../../assets/logo/Bossi_logo_1.png" alt="profile image"/>
    </div>
    <div style="text-align: center; padding: 25px; font-size: 25px; margin-top: 20px">
      아름 님
    </div>

    <div style="margin-top: 40px; padding: 0 20%">
      <v-list-item-group v-model="selectedItem" color="DEEP_PINK" style="border-radius: 20px">
        <v-list-item v-for="(item, index) in category" :key="index" style="text-align: center; font-size: 22px; height: 60px; margin-bottom: 15px; border-radius: 5px" @click="movePage(index)">

           <v-list-item-icon>
            <v-icon v-text="item.icon"></v-icon>
          </v-list-item-icon>

          <v-list-item-title v-text="item.name" style="font-size: 18px; width: 100px"></v-list-item-title>

        </v-list-item>
      </v-list-item-group>
    </div>

  </div>
</template>

<script>
import {defineComponent} from 'vue'

export default defineComponent({
  name: "MangerCategory",
  data(){
    return {
      category: [
        {name: "홈", routerName: 0, icon: 'mdi-home'},
        {name: "입점 관리", routerName: 1, icon: 'mdi-store'},
        {name: "사용자 권한 관리", routerName: 2, icon: 'mdi-account-check-outline'},
        {name: "신고된 상품 관리", routerName: 4, icon: 'mdi-store-alert-outline'},
        {name: "신고된 댓글 관리", routerName: 3, icon: 'mdi-comment-remove-outline'},
      ],
      selectedItem: 1
    }
  },
  methods: {
    movePage(index) {
      let categoryNum = index+1;
      this.$router.push({ path: '/manager/category/'+categoryNum})
    }
  }
})
</script>

<style scoped>
#profile_img {
  width: 150px;
  height: 150px;
  border-radius: 100%;
}
</style>