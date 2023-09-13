<template>
  <div style=" height: 100%; width: 50%; padding: 10px">
    <div style="width: 100%;">
      <swiper
          style="width: 100%; height: 100%"
          class="swiper"
          :options="swiperOption"
      >
        <swiper-slide v-for="(item, i) in swiperList" :key="i">
          <img :src="require(`../../../assets/sellProduct/${item}`)" width="100%" height="520"/></swiper-slide>

        <div
            class="swiper-pagination"
            slot="pagination"
        >
        </div>
      </swiper>
    </div>

    <div>
<!--      재료불문❗️ 종류불문❗️ 착한가격❗️ <br/>

      떡과 쌀빵 심지어 쿠키랑 과자류등<br/>

      전통부터 퓨전까지😘 뚝떡뚝떡~ 잘 만드는<br/>

      K-푸드 맛집✨ 맛있는~ 떠카나주 💖<br/>

      약과를 만들다 깨지고 남은게 파지다??<br/>

      파지약과에 대한 가장 큰 오해인데요ㅜㅜ<br/>

      점보보다 훨씬 손이 많이가는 파지..<br/>

      그래서 저희도 엄두를 못내다,<br/>

      회원님들의 요청에 시작했습니다<br/>

      🍪 파지약과 🍪<br/>
      파지라는 이름과는 참~ 안어울리게<br/>

      너무 정성껏 만들다 보니..<br/>

      저희 팀원중엔,<br/>

      "궁금하니 파지좀 챙겨와"라는 남동생 말에?<br/>

      아까워서 넌 못주겠다.. 파지 한팩에 공이 너무 들어가서..ㅜㅜ<br/>

      ㅎㅎ 리얼남매<br/>

      재료불문❗️ 종류불문❗️ 착한가격❗️ <br/>

      떡과 쌀빵 심지어 쿠키랑 과자류등<br/>

      전통부터 퓨전까지😘 뚝떡뚝떡~ 잘 만드는<br/>

      K-푸드 맛집✨ 맛있는~ 떠카나주 💖<br/>

      약과를 만들다 깨지고 남은게 파지다??<br/>

      파지약과에 대한 가장 큰 오해인데요ㅜㅜ<br/>

      점보보다 훨씬 손이 많이가는 파지..<br/>

      그래서 저희도 엄두를 못내다,<br/>

      회원님들의 요청에 시작했습니다<br/>

      🍪 파지약과 🍪<br/>
      파지라는 이름과는 참~ 안어울리게<br/>

      너무 정성껏 만들다 보니..<br/>

      저희 팀원중엔,<br/>

      "궁금하니 파지좀 챙겨와"라는 남동생 말에?<br/>

      아까워서 넌 못주겠다.. 파지 한팩에 공이 너무 들어가서..ㅜㅜ<br/>

      ㅎㅎ 리얼남매<br/>-->

<!--      <editor-content :editor="editor" :content="productContent.content" />-->
      <div v-html="productContent.content"></div>
    </div>
  </div>
</template>

<script>
import {defineComponent} from 'vue'
import {Swiper, SwiperSlide} from "vue-awesome-swiper";
import 'swiper/css/swiper.css'

export default defineComponent({
  name: "ProductDetailContent",
  components: {
    Swiper,
    SwiperSlide,
  },
  props: {
    productContent: {
      type: Array
    }
  },
  data(){
    return{
      swiperList: ['product_test.jpg', 'product_test_1.jpg', 'product_test_2.jpg', 'product_test_3.jpg'],
      swiperOption: {
        slidesPerView: 1,
        spaceBetween: 30,
        /*autoplay:{
          delay: 1500,
          disableOnInteraction: true
        },*/
        speed: 2,
        autoHeight: true,
        loop: true,
        pagination: {
          el: '.swiper-pagination',
          clickable: true
        }
      }
    }
  },
  created() {

  }
})
</script>

<style scoped>

.swiper-pagination {
  --swiper-theme-color: #fc9899
}

</style>