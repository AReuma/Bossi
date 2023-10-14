      <template>
  <div>
    <cart-multi-view :myCartInfo="myCartInfo"></cart-multi-view>
  </div>
</template>

<script>
import {defineComponent} from 'vue'
import CartMultiView from "@/components/order/CartMultiView.vue";
import {mapActions, mapState} from "vuex";
import {useCookies} from "vue3-cookies";

export default defineComponent({
  name: "MyCartPage",
  components: {CartMultiView},
  data() {
    return {
      email: useCookies().cookies.get("email")
    }
  },
  methods: {
    ...mapActions(['fetchMyCartInfo']),
  },
  mounted() {
    this.fetchMyCartInfo(this.email)
  },
  computed: {
    ...mapState(['myCartInfo']),
  }
  })
</script>

<style scoped>

</style>