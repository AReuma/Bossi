<template>
  <div>
    <home-header :clickCategory="index"></home-header>
    <home-swiper></home-swiper>
    <live-order-list></live-order-list>
  </div>
</template>

<script>
import HomeHeader from "@/components/home/HomeHeader.vue";
import HomeSwiper from "@/components/home/HomeSwiper.vue";
import LiveOrderList from "@/components/home/sell/liveOrderList.vue";

export default {
  name: 'HomePage',
  components: {
    LiveOrderList,
    HomeSwiper,
    HomeHeader
  },
  data(){
    return {
      index: 1
    }
  }
}
</script>
