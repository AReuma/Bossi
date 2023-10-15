<template>
  <div id="basic">
    <p style="font-size: 32px"><span style="color: #fc9899; ">주문이 정상적으로 완료</span>되었습니다. </p>

    <div>
      <table border="1">
        <tr>
          <td>주문번호</td>
          <td>{{orderCompleteInfo.orderNum}}</td>
        </tr>

        <tr>
          <td>배송지 정보</td>
          <td>
            <table>
              <tr>
                <td>네이버</td>
              </tr>
              <tr>
                <td>네이버</td>
              </tr>
              <tr>
                <td>네이버</td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </div>

    <div style="border: 1px solid red">
      포인트 예정 최대 56p
    </div>
    <div>
      구매 적립 nn원
    </div>
    orderCompleteInfo: {{orderCompleteInfo}}
  </div>
</template>

<script>
import {defineComponent} from 'vue'

export default defineComponent({
  name: "PurchaseCompleteView",
  props: ['orderCompleteInfo']
})
</script>

<style scoped>
#basic{
  max-width: 1000px;
  height: 100%;
  padding: 6% 5%;
  width: 100%;
  margin: 0 auto;
  align-items: center;
  display: flex;
  justify-content: center;
  flex-direction: column;
  position: relative;
}
</style>