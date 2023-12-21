<template>
  <div>
    <home-header :clickCategory="index"></home-header>
    <v-btn @click="connect()">test</v-btn>
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
  methods: {

  },
  components: {
    LiveOrderList,
    HomeSwiper,
    HomeHeader
  },
  data(){
    return {
      index: 1,
    }
  },

}
</script>
