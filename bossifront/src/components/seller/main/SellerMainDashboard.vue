<template>
  <div id="basic">
    <div style="width: 100%; display: flex; padding-top: 60px">
      <div style="flex: 6.5; padding: 4px; display: flex; flex-direction: column">
        <div style="display: flex; border-bottom: 1px solid rgba(66,68,84,0.08); padding: 0 20px 50px 40px">
          <div style="flex: 1; margin-right: 100px">
            <table>
              <tr style="height: 50px">
                <td style="font-weight: bolder">상품 현황</td>
                <td class="text-right"><v-btn x-small icon depressed color="DEEP_PINK"><v-icon>mdi-reload</v-icon></v-btn></td>
              </tr>

              <tr>
                <td>판매중인 상품</td>
                <td class="text-right">1개</td>
              </tr>

              <tr>
                <td>품절상품</td>
                <td class="text-right">1개</td>
              </tr>

              <tr>
                <td>판매대기 상품</td>
                <td class="text-right">1개</td>
              </tr>

              <tr>
                <td>수정요청/ 판매 금지 상품</td>
                <td class="text-right">1개</td>
              </tr>

              <tr>
                <td>상품 문의 미답변</td>
                <td class="text-right">1개</td>
              </tr>
            </table>
          </div>
          <div style="flex: 1">
            <table>
              <tr style="height: 50px">
                <td style="font-weight: bolder">정산 현황</td>
                <td class="text-right"><v-btn x-small icon depressed color="DEEP_PINK"><v-icon>mdi-reload</v-icon></v-btn></td>
              </tr>

              <tr>
                <td>정산 예정 금액</td>
                <td class="text-right">1개</td>
              </tr>

              <tr>
                <td class="text-right;"  style="height: 140px; text-align: end; vertical-align:bottom; font-size: 25px" colspan="2" rowspan="4">원</td>
              </tr>
            </table>
          </div>
        </div>

        <div style="display: flex; padding: 25px 20px 20px 40px">
          <div style="flex: 1; margin-right: 100px">
            <table>
              <tr style="height: 50px">
                <td style="font-weight: bolder">판매 현황</td>
                <td class="text-right"><v-btn x-small icon depressed color="DEEP_PINK"><v-icon>mdi-reload</v-icon></v-btn></td>
              </tr>

              <tr>
                <td>입금 대기중</td>
                <td class="text-right">1개</td>
              </tr>

              <tr>
                <td>배송 요청</td>
                <td class="text-right">1개</td>
              </tr>

              <tr>
                <td>배손 준비중</td>
                <td class="text-right">1개</td>
              </tr>

              <tr>
                <td>배송중</td>
                <td class="text-right">1개</td>
              </tr>

              <tr>
                <td>배송 지연</td>
                <td class="text-right">1개</td>
              </tr>

              <tr>
                <td>배송 지연 취소 예정</td>
                <td class="text-right">1개</td>
              </tr>
            </table>
          </div>
          <div style="flex: 1;">
            <table>
              <tr style="height: 50px">
                <td style="font-weight: bolder">취소 반품 교환 현황</td>
                <td class="text-right"><v-btn x-small icon depressed color="DEEP_PINK"><v-icon>mdi-reload</v-icon></v-btn></td>
              </tr>

              <tr>
                <td>구매 취소 요청</td>
                <td class="text-right">0건</td>
              </tr>

              <tr>
                <td>판매자 취소 요청</td>
                <td class="text-right">1개</td>
              </tr>

              <tr>
                <td>반품 요청/ 반송중</td>
                <td class="text-right">1개</td>
              </tr>

              <tr>
                <td>교환 요청/ 반송중</td>
                <td class="text-right">1개</td>
              </tr>

            </table>
          </div>
        </div>
      </div>

      <div style="flex: 3.5; margin-left: 15px; border-left: 1px solid rgba(66,68,84,0.08); padding: 0 20px 50px 40px">
        <seller-main-dashboard-seller-info></seller-main-dashboard-seller-info>
      </div>
    </div>
  </div>
</template>

<script>
import {defineComponent} from 'vue'
import SellerMainDashboardSellerInfo from "@/components/seller/main/SellerMainDashboardSellerInfo.vue";

export default defineComponent({
  name: "SellerMainDashboard",
  components: {SellerMainDashboardSellerInfo}
})
</script>

<style scoped>
#basic {
  display: flex;
  max-width: 1200px;
  width: 100%;
  height: 100%;
  margin: 0 auto;
}

table {
  width: 100%;
}

td {
  height: 35px;
}
</style>