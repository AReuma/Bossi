<template>
    <div id='nav'>
      <div id="before_login">
        <div v-if="isUserLogin">
          <div class="after_login_button" style="margin-right: 10px" @click="enteringStore()">
            <v-icon small style="margin-right: 2px">mdi-storefront-outline</v-icon>
            입점하기
          </div>
          <div class="after_login_button" style="margin-right: 8px">
            <v-icon small style="margin-right: 2px">mdi-message-outline</v-icon>
            메시지
          </div>
          <div class="after_login_button" @click="register()">
            <v-icon small style="margin-right: 2px">mdi-bell-outline</v-icon> 알림
          </div>
          <div class="after_login_button" @mouseover="mouseover" @mouseleave="hideList">
            <div>{{ nickName }} 님</div>
            <div v-if="mouseoverCheck" style="position: absolute; z-index: 1; height: 170px; right: 320px; width: 150px; margin-top: 2px; padding: 10px; background-color: white; border: 0.5px solid rgba(67,79,88,0.25); border-radius: 4px">
              <div v-for="(item, i) in mouseoverList" :key="i" style="padding: 5px;" @mouseover="changeTextColor(i)" @mouseleave="resetTextColor(i)">
                <router-link class="custom-link" :to="{name: item.routerName}" :style="{color: item.textColor}"> {{item.name}} </router-link>
              </div>
              <hr style="margin: 8px 0; border: 0.5px solid rgba(67,79,88,0.2);"/>
              <div style="padding: 2px" @click="logoutBtn" @mouseover="changeTextColorLogout" @mouseleave="resetTextColorLogout" :style="{color: changeTextColorLogoutColor}">
                로그아웃
              </div>
            </div>
          </div>
        </div>

        <div v-else>
          <div class="before_login_button" style="margin-right: 160px">
            고객센터
          </div>
          <div class="before_login_button" @click="register()">
            회원가입
          </div>
          <div class="before_login_button" @click="login()">
            로그인
          </div>
        </div>
      </div>

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
                  style="margin-bottom: 15px; outline-color: #fc9899"
                  class="search"
                  type="text"
                  placeholder=" 원하는 상품을 검색해보세요"
              />
            </div>

            <div style="padding-left: 1px; margin-top: 6px; margin-right: 12px" >
              <v-icon> mdi-magnify</v-icon>
            </div>

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
              <div style="font-size: 11px; display: flex; justify-content: center; padding-top: 3px">
                내 정보
              </div>
            </div>

            <div @click="cart()" style="display: block; padding-left: 35px">
              <div>
                <v-badge color="DEEP_PINK" content="0" overlap>
                  <v-icon large style="height: 30px">mdi-cart-outline</v-icon>
                </v-badge>
              </div>
              <div style="font-size: 11px; display: flex; justify-content: center; padding-top: 3px">
                장바구니
              </div>
            </div>
          </div>
        </div>
      </div>

      <div style="border-bottom: 0.5px solid rgba(67,79,88,0.25); border-top: 0.5px solid rgba(67,79,88,0.25);">
        <div id="category">
          <div style="font-size: 14px; width: 120px; position: absolute; z-index: 99; height: 42px; text-align : center; padding : 13px 0;" @mouseover="categoryMouseOver" @mouseleave="hideCategory">
            <div :style="{color: categoryColor}" style="width: 120px;">카테고리</div>
              <div class="category-mouseover" v-if="categoryMouseover">

                <div class="category_list_items">
                  <div v-for="(item, index) in category" :key="index" @mouseover="categoryMouseoverList(index)" @mouseleave="hideCategoryList(index)" >
                    <div class="category_list_item" :style="{backgroundColor: item.backColor}" style="text-align: left;" v-if="index <= 8">{{item.name}}
                      <v-icon style="float: right" v-show="item.icon">mdi-chevron-right</v-icon>
                    </div>
                  </div>
                </div>

                <div class="category_list_items">
                  <div v-for="(item, index) in category" :key="index" @mouseover="categoryMouseoverList(index)" @mouseleave="hideCategoryList(index)" >
                    <div class="category_list_item" :style="{backgroundColor: item.backColor}" style="text-align: left" v-if="index > 8 && index < 18">{{item.name}}
                      <v-icon style="float: right" v-show="item.icon">mdi-chevron-right</v-icon>
                    </div>
                  </div>
                </div>

                <div class="category_list_items" style="border-right: white">
                  <div v-for="(item, index) in category" :key="index" @mouseover="categoryMouseoverList(index)" @mouseleave="hideCategoryList(index)" >
                    <div class="category_list_item" :style="{backgroundColor: item.backColor}" style="text-align: left" v-if="index > 17">{{item.name}}<v-icon style="float: right" v-show="item.icon">mdi-chevron-right</v-icon>
                    </div>
                  </div>
                </div>
              </div>
          </div>

          <div class="category_list" v-for="(item, i) in headerCategory" :key="i" style="position: relative; width: 120px;" @mouseover="categoryChangeTextColor(i)" @mouseleave="resetCategoryTextColor(i)">
<!--            <div class="category_item"  :style="{color: item.textColor}">
              {{item.name}}
            </div>-->

            <div v-if="i === clickCategory" class="category_item" style="border-bottom: 4px solid #fdcbcc; color: #fc9899; height: 44px; font-size: 13px; font-weight: bold" >{{item.name}}</div>
            <div v-else class="category_item" :style="{color: item.textColor}">
              <router-link class="custom-link" :to="{name: item.routerName}" :style="{color: item.textColor}"> {{item.name}} </router-link>
            </div>
          </div>
        </div>
      </div>

      <hr style="border: 0.5px solid rgba(67,79,88,0.11);"/>

  </div>
</template>

<script>
import index, {mapActions} from "vuex";
import {useCookies} from "vue3-cookies";
import "swiper/css/swiper.css";

export default {
  name: "HomeHeader",
  components: {},
  props: ['clickCategory'],
  data() {
    return {
      headerCategory: [
        {name: '', textColor: ''},
        {name: '홈', textColor: 'black', routerName: "HomePage"},
        {name: '실시간 구매', textColor: 'black', routerName: "LiverOrderProductPage"},
        {name: '실시간 후기', textColor: 'black'},
        {name: '작가님 추천', textColor: 'black'},
        {name: '실시간 추천', textColor: 'black'},
        {name: '인기 작품', textColor: 'black'},
        {name: '작가 피드', textColor: 'black'},
        {name: '인기작가', textColor: 'black'},
      ],
      mouseoverList: [
        { name: '주문배송', routerName: 'MyOrderDeliveryPage', textColor: 'black'},
        { name: '관심리스트', routerName: 'FavoriteProductPage', textColor: 'black'},
        { name: '쿠폰함', routerName: 'MyCouponListPage', textColor: 'black'},
        { name: '회원 정보관리', routerName: 'PersonalInfoPage', textColor: 'black'},
      ],
      //clickCategory: 1,
      nickName: useCookies().cookies.get("nickName"),
      mouseoverCheck: false,
      hoverColor: '#fc9899',
      hoverBackColor: 'rgba(255,207,210,0.56)',
      changeTextColorLogoutColor: 'black',
      categoryColor: 'black',
      categoryMouseover: false,
      category: [
        {name: '디저트/베이커리', routerName: '', backColor: 'white', icon: false},
        {name: '음료', routerName: '', backColor: 'white', icon: false},
        {name: '전통주', routerName: '', backColor: 'white', icon: false},
        {name: '수제 반찬', routerName: '', backColor: 'white', icon: false},
        {name: '수제 먹거리', routerName: '', backColor: 'white', icon: false},
        {name: '의류/홈웨어', routerName: '', backColor: 'white', icon: false},
        {name: '패션 잡화', routerName: '', backColor: 'white', icon: false},
        {name: '시계', routerName: '', backColor: 'white', icon: false},
        {name: '육아/아동', routerName: '', backColor: 'white', icon: false},
        {name: '반지', routerName: '', backColor: 'white', icon: false},
        {name: '귀걸이', routerName: '', backColor: 'white', icon: false},
        {name: '목걸이', routerName: '', backColor: 'white', icon: false},
        {name: '팔찌', routerName: '', backColor: 'white', icon: false},
        {name: '꽃/식물', routerName: '', backColor: 'white', icon: false},
        {name: '캔들/디퓨저', routerName: '', backColor: 'white', icon: false},
        {name: '홈인테리어', routerName: '', backColor: 'white', icon: false},
        {name: '주방/생활', routerName: '', backColor: 'white', icon: false},
        {name: '가구', routerName: '', backColor: 'white', icon: false},
        {name: '인형/장난감', routerName: '', backColor: 'white', icon: false},
        {name: '공예', routerName: '', backColor: 'white', icon: false},
        {name: '폰케이스', routerName: '', backColor: 'white', icon: false},
        {name: '문구/팬시', routerName: '', backColor: 'white', icon: false},
        {name: '도서', routerName: '', backColor: 'white', icon: false},
        {name: '향수', routerName: '', backColor: 'white', icon: false},
        {name: '뷰티', routerName: '', backColor: 'white', icon: false},
        {name: '헤어/바디', routerName: '', backColor: 'white', icon: false},
        {name: '용돈이벤트/기타', routerName: '', backColor: 'white', icon: false},
      ],
      categoryListBackColor: 'white',
      categoryListMouseover: false,
    }
  },
  methods: {
    login(){
      if(this.isUserLogin){
        this.$router.push({name: 'MyPage'})
      } else{
        this.$router.push({name: 'LoginPage'})
      }
    },
    cart(){
      this.$router.push({name: "MyCartPage"})
    },
    register(){
      this.$router.push({name: 'JoinPage'})
    },
    ...mapActions(["logout"]),
    logoutBtn(){
      this.logout();
    },
    mouseover(){
      this.mouseoverCheck = true;
    },
    hideList(){
      this.mouseoverCheck = false
    },
    changeTextColor(index) {
      this.mouseoverList[index].textColor = this.hoverColor;
    },
    resetTextColor(index) {
      this.mouseoverList[index].textColor = 'black';
    },
    changeTextColorLogout(){
      this.changeTextColorLogoutColor = this.hoverColor;
    },
    resetTextColorLogout(){
      this.changeTextColorLogoutColor = 'black'
    },
    categoryChangeTextColor(index){
      this.headerCategory[index].textColor = this.hoverColor;
    },
    resetCategoryTextColor(index){
      this.headerCategory[index].textColor = 'black'
    },
    categoryMouseOver(){
      this.categoryMouseover = true;
      this.categoryColor = this.hoverColor;
    },
    hideCategory(){
      this.categoryMouseover = false;
      this.categoryColor = 'black';
    },
    categoryMouseoverList(index){
      this.category[index].backColor = this.hoverBackColor;
      this.category[index].icon = true;
    },
    hideCategoryList(index){
      this.category[index].backColor = 'white';
      this.category[index].icon = false;
    },
    enteringStore(){
      this.$router.push({name: "EnteringStorePage"})
    }
  },
  computed: {
    index() {
      return index
    },
    isUserLogin() {
      return this.$store.getters.isLogin;
    }
  }

};


</script>

<style scoped>
#before_login {
  background-color: rgba(153,155,159,0.1);
  //background-color: red;
  padding-right: 140px;
  height: 35px;
  position: relative;
  z-index: 3;
}
.before_login_button{
  float: right;
  width: 75px;
  box-sizing: border-box;
  text-align: center;
  margin-top: 8px;
  font-size: 11px;
}
.after_login_button{
   float: right;
   width: 70px;
   box-sizing: border-box;
   text-align: center;
   margin-top: 8px;
   font-size: 12px;
 }
#nav{
  background-color: white;
  color: #212124;
  top: 0;
  left: 0;
  width: 100%;
  max-width: 100vw;
  max-height: 200px;
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
  z-index: 2;
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
  font-size: 14px;
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
.custom-link {
  text-decoration: none; /* 밑줄 제거 */
  color: inherit; /* 기본 링크 색상 유지 */
}
.category-mouseover{
  width: 650px;
  height: 340px;
  position: absolute;
  z-index: 1;
  top: 42px;
  left: 10%;
  padding: 18px 0;
  display: flex;
  float: left;
  background-color: white;
  border: 0.5px solid rgba(67,79,88,0.25);
  border-radius: 4px
}
.category_list_items{
  z-index: 99;
  font-size: 15px;
  height: 100%;
  margin: 0 0 10px 10px;
  width: 220px;
  border-right: 1px solid rgba(67,79,88,0.25);
}
.category_list_item{
  padding: 2px;
  margin-left: 2px;
  margin-bottom: 8px;
  margin-right: 15px;
  border-radius: 4px;
}
</style>