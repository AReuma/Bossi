<template>
  <div id="nav">
    <div id="header">
      <div id="title" style="margin-right: 10px;">
        <div id="bossiColor">Bossi 판매자 센터</div>
      </div>

      <div style="display: flex; align-items: center">
        <div v-for="(item, index) in navList" :key="index" style="font-size: 18px; padding-top: 4px; padding-right: 15px; color: #645d5d" @click="movePage(index)">
          {{item.name}}
        </div>
      </div>

      <div style="margin-left: 10%; display: flex;">
        <v-btn @click="addProduct" depressed text style="border: 1px solid #bab7b7; text-align: center; margin-right: 10px">
          <v-icon style="margin-right: 2px">mdi-plus</v-icon> 상품 등록
        </v-btn>

        <v-btn text depressed>
          <v-icon color="#6a6969">mdi-bell-outline</v-icon>
        </v-btn>

        <v-btn depressed text @mouseover="mouseover" @mouseleave="hideList">
          <v-icon color="#6a6969">mdi-account-outline</v-icon>

          <div v-if="mouseoverCheck" style="position: absolute; z-index: 1; height: 90px; top: 25px; width: 170px; margin-top: 2px; padding: 10px; background-color: white; border: 0.5px solid rgba(67,79,88,0.25); border-radius: 4px">
            <v-btn text>판매자정보 관리</v-btn><br/>
            <v-btn text>로그아웃</v-btn>
          </div>
        </v-btn>



      </div>
    </div>
  </div>
</template>

<script>
import {defineComponent} from 'vue'

export default defineComponent({
  name: "SellerMainHeaderView",
  data() {
    return {
      navList: [
        {name: "대시보드"},
        {name: "스토어 정보관리"},
        {name: "상품관리"},
        {name: "판매관리"},
        {name: "정산관리"},
        {name: "통계"},
      ],
      mouseoverCheck: false,
    }
  },
  methods: {
    movePage(index) {
      let categoryNum = index+1;
      this.$router.push({ path: '/seller/main/'+categoryNum})
    },
    addProduct(){
      this.$router.push({name: 'SellerProductCreatePage'})
    },
    mouseover(){
      this.mouseoverCheck = true;
    },
    hideList() {
      this.mouseoverCheck = false
    }
  }
})
</script>

<style scoped>
#title {
  text-decoration: none;
  width: 250px;
}

#bossiColor{
  color: #fd7677;
  font-weight: bolder;
  font-family: GangwonEduSaeeum_OTFMediumA, serif;
  font-size: 42px;
  float: left;
}
#header {
  max-width: 1200px;
  height: 64px;
  padding: 12px 16px;
  width: 100%;
  margin: 0 auto;
  position: relative;
  display: flex;
  align-items: center;
  z-index: 2;
}
#nav {
  background-color: rgba(216, 204, 204, 0.47);
  align-items: center;
  color: #212124;
  width: 100%;
  max-width: 100vw;
}
</style>