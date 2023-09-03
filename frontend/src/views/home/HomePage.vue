<template>
  <div>
    <home-header></home-header>
    <home-header-category></home-header-category>
    <home-swiper></home-swiper>
  </div>
</template>

<script>
import HomeHeader from "@/components/home/HomeHeader.vue";
import HomeHeaderCategory from "@/components/home/HomeHeaderCategory.vue";
import HomeSwiper from "@/components/home/HomeSwiper.vue";

export default {
  name: 'HomePage',
  components: {
    HomeSwiper,
    HomeHeaderCategory,

    HomeHeader
  },
}
</script>
