<template>
    <div id='nav'>
      <div id="header">
        <div id="title">
          <div id="bColor">B</div>
          <div id="ossiColor">ossi</div>
        </div>

        <div style="display: flex; width: 100%; margin-top: 14px;">
          <div id='nav_list'>
            <div style="float: left; padding-right: 8%; padding-top: 5px; color: #fc9899">작품</div>
            <div style="float: left; padding-top: 5px; color: #434f58">클래스</div>
          </div>

          <div style="display: flex;">
            <div id="searchBar">
              <input
                  style="margin-bottom: 15px"
                  class="search"
                  type="text"
                  placeholder=" 원하는 상품을 검색해보세요"
              />
            </div>

            <v-btn text style="padding-left: 1px;">
              <v-icon> mdi-magnify</v-icon>
            </v-btn>

            <div style="margin-left: 3px; position: relative; height: 40px; width: 270px;">
              <div style="position: absolute; top: 50%; height: 10px; margin-top: -13px">
                실시간 인기 상품
              </div>
            </div>
          </div>

          <div id='nav_right'>
            <div style="display: block" @click="login()">
              <div>
                <v-icon large style="height: 30px">mdi-account-circle-outline</v-icon>
              </div>
              <div style="font-size: 12px; display: flex; justify-content: center; padding-top: 3px">
                로그인
              </div>
            </div>

            <div @click="cart()" style="display: block; padding-left: 30px">
              <div>
                <v-badge color="DEEP_PINK" content="0" overlap>
                  <v-icon large style="height: 30px">mdi-cart-outline</v-icon>
                </v-badge>
              </div>
              <div style="font-size: 12px; display: flex; justify-content: center; padding-top: 3px">
                장바구니
              </div>
            </div>
          </div>
        </div>
      </div>

      <div style="border-bottom: 0.5px solid rgba(67,79,88,0.25); border-top: 0.5px solid rgba(67,79,88,0.25);">
        <div id="category">
          <div class="category_list" v-for="(item, i) in category" :key="i" style="position: relative; width: 120px;">
            <div v-if="i === clickCategory" class="category_item" style="border-bottom: 5px solid #fdcbcc; color: #fc9899; height: 44px; font-size: 13px; font-weight: bold" >{{item}}</div>
            <div v-else class="category_item">{{item}}</div>
          </div>
        </div>
      </div>
  </div>
</template>

<script>
export default {
  name: "HomeHeader",
  data() {
    return {
      category: [
          '카테고리',
          '홈',
          '실시간 구매',
          '실시간 후기',
          '작가님 추천',
          '실시간 추천',
          '인기 작품',
          '작가 피드',
          '인기작가'
      ],
      clickCategory: 1,
    }
  },
  methods: {
    login(){
      alert('로그인 클릭')
    },
    cart(){
      alert('장바구니 클릭')
    }
  }
};
</script>

<style scoped>
  #nav{
    background-color: white;
    color: #212124;
    top: 0;
    left: 0;
    width: 100%;
    max-width: 100vw;
    padding-top: 10px;
    /* position: fixed;
    z-index: 999; */
  }
  #header {
    background-color: white;
    max-width: 1200px;
    height: 64px;
    padding: 12px 16px;
    box-sizing: border-box;
    justify-content: space-between;
    width: 100%;
    margin: 0 auto;
    position: relative;
    display: flex;
    align-items: center;
  }
  #category{
    max-width: 1200px;
    height: 44px;
    padding: 19px 50px;
    box-sizing: border-box;
    justify-content: space-between;
    width: 100%;
    margin: 0 auto;
    position: relative;
    display: flex;
    align-items: center;
  }
  .category_list{
    font-family: 'GmarketSansMedium', sans-serif;
    font-size: 12px;
    height: 42px;
  }
  .category_item{
    height: 42px;
    text-align : center;
    padding : 13px 0;
  }
  #title {
    text-decoration: none;
    width: 160px;
  }
  #bColor{
    color: #fc9899;
    font-family: ONE-Mobile-POP, serif;
    font-size: 42px;
    float: left;
  }
  #ossiColor{
    color: #3d4148;
    font-family: GangwonEduSaeeum_OTFMediumA, serif;
    font-size: 42px;
    float: left;
  }
  #nav_list{
    width: 180px;
    font-size: 22px;
    font-family: GmarketSansMedium,serif;
  }
  #nav_list .router-link-active,
  #nav_list .router-link-exact-active {
    text-decoration: none;
    line-height: 1.32;
    font-size: 18px;
    font-style: normal;
    margin-right: 30px;
    color: green;
  }
  #nav_item{
    text-decoration: none;
    line-height: 1.32;
    font-size: 18px;
    font-weight: 700;
    font-style: normal;
    margin-right: 30px;
    color: #4d5159;
  }
  #nav_right{
    max-height: 54px;
    margin-left: 20px;
    display: flex;
    position: relative;
  }
  #searchBar {
    display: block;
    position: relative;
    margin-right: 12px;
  }
  input {
    width: 350px;
    line-height: 1.36;
    font-size: 16px;
    /*background-color: #f2f3f6;*/
    box-sizing: border-box;
    height: 40px;
    /*padding: 9px 12px;*/
    border: #fc9899 2px solid;
    border-radius: 6px;
    color: #212124;
  }
  #my_button{
    line-height: 1.3;
    font-size: 16px;
    /*letter-spacing: -2%;*/
    display: block;
    white-space: nowrap;
    background-color: white;
    color: #212124;
    height: 54px;
    font-weight: 700;
    width: fit-content;
    min-height: 28px;
    padding: 2px 10px;
    border-radius: 3px;
    //border: 1px solid #d1d3d8;
    background: none;
    appearance: none;
    justify-content: center;
    align-items: center;
    cursor: pointer;
  }
</style>