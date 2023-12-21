<template>
  <div id="basic">
    <div style="width: 100%; display: flex;">
      <div style="width: 50%; font-size: 24px;">결제하기</div>
      <div style="display: flex; width: 50%; justify-content: end; align-items: center; font-size: 17px">
        <div style="color: #848484">1. 장바구니 &nbsp;&nbsp;>&nbsp;&nbsp; </div>
        <div style="color: #848484">2. 주문 결제 &nbsp;&nbsp;>&nbsp;&nbsp; </div>
        <div style="font-family: GmarketSansBold, sans-serif">3. 주문완료</div>
      </div>
    </div>
  {{orderCompleteInfo}}
    <div style="width: 100%; background-color: rgba(242,242,242,0.3); padding: 0 20px 40px 20px; margin-top: 10px;">
    <p style="font-size: 32px; margin-top: 30px; width: 100%"><span style="color: #fc9899; ">주문이 정상적으로 완료</span>되었습니다. </p>

    <div style="padding-top: 40px; border-top: 0.5px solid #bbbbbb; width: 100%; display: flex">
      <div style="flex: 1.2;">
        <table style="width: 100%">
          <tr>
            <td>주문번호</td>
            <td style="color: #fc9899">{{orderCompleteInfo.orderNum}}</td>
          </tr>

          <tr>
            <td style="vertical-align: top">배송지 정보</td>
            <td>
              <table>
                <tr>
                  <td>{{ orderCompleteInfo.recipient }}</td>
                </tr>
                <tr>
                  <td>{{ orderCompleteInfo.phoneNum }}</td>
                </tr>
                <tr>
                  <td>({{orderCompleteInfo.zipcode}}) {{orderCompleteInfo.address}} {{orderCompleteInfo.detailAddr}}</td>
                </tr>
              </table>
            </td>
          </tr>
        </table>

        <div style="width: 100%; margin-top: 40px">
          <table style="width: 100%;">
            <tr>
              <td>결제 금액</td>
              <td style="text-align: end;">{{orderCompleteInfo.price.toLocaleString()}}원</td>
            </tr>
            <tr>
              <td>사용 포인트</td>
              <td style="text-align: end;">{{orderCompleteInfo.usePoint.toLocaleString()}}P</td>
            </tr>
            <tr>
              <td>포인트 혜택</td>
              <td style="text-align: end;">최대 506p</td>
            </tr>
          </table>
        </div>

      </div>

      <div style="flex: 0.8; margin: 0 2%; background-color: rgba(247,247,247,0.73)">
        <div style="align-items: center; display: flex; justify-content: center; margin-top: 20px">
          <img src="../../../assets/logo/Bossi_logo_3.png" width="140"/>
        </div>

        <div style="margin-top: 20px; padding: 0 20px;">
          <div style=" border-top: 0.5px solid #bbbbbb;">
            <div style="width: 100%; margin-top: 15px;" v-for="(product, index) in orderCompleteInfo.orderProductInfoList" :key="index">
              <div style="display: flex">
               <v-img :src="`https://s3.ap-northeast-2.amazonaws.com/my.example.s3.bucket.bossi/${product.productImg}`" max-width="100" style="border-radius: 4px; "/>

                <div style="padding-left: 10px">
                  <div class="overDiv">{{product.productName}}</div>
                  <div class="overDiv" style="font-size: 12px; color: rgba(62,62,62,0.41)" v-for="(option, index) in product.optionList" :key="index"> - {{option}}</div>
                  <div class="overDiv" style="font-size: 12px; color: rgba(62,62,62,0.41); margin-top: 4px"><v-icon small>mdi-home-outline</v-icon>{{product.storeName}}</div>
                  <div class="overDiv" style="font-size: 12px; margin-top: 8px">{{product.totalProductPrice.toLocaleString()}}원</div>
                </div>
              </div>
            </div>

            <div style="margin-top: 25px; border-top: 0.5px solid #bbbbbb;">
              <table style="width: 100%">
                <tr>
                  <td>주문 금액</td>
                  <td style="text-align: end; color: #fc9899; font-size: 18px">{{orderCompleteInfo.price.toLocaleString()}}원</td>
                </tr>

                <tr>
                  <td style="color: #ACAAA7; font-size: 15px; padding-left: 4px;">└ 상품 금액</td>
                  <td style="text-align: end; color: #ACAAA7; font-size: 15px">{{productTotalPrice.toLocaleString()}}원</td>
                </tr>

                <tr>
                  <td style="color: #ACAAA7; font-size: 15px; padding-left: 4px;">└ 포인트 사용</td>
                  <td style="text-align: end; color: #ACAAA7">- {{orderCompleteInfo.usePoint}}P</td>
                </tr>

                <tr>
                  <td style="color: #ACAAA7; font-size: 15px; padding-left: 4px;">└ 배송비</td>
                  <td style="text-align: end; color: #ACAAA7">+{{orderCompleteInfo.deliveryPrice.toLocaleString()}}원</td>
                </tr>
              </table>
            </div>

          </div>

        </div>
      </div>
    </div>
    </div>
  </div>
</template>

<script>
import {defineComponent} from 'vue'

export default defineComponent({
  name: "PurchaseCompleteView",
  props: ['orderCompleteInfo'],
  data() {
    return {
      productTotalPrice: 0,
    }
  },
  watch:{
    orderCompleteInfo: {
      handler(newVal) {
        //console.log(newVal.orderProductInfoList);

        for (const orderProduct of newVal.orderProductInfoList) {
          for (const totalPrice of orderProduct.totalPrice) {
            this.productTotalPrice += totalPrice;
          }
        }

        console.log(this.productTotalPrice)
      }
    }
  }
})
</script>

<style scoped>
#basic{
  max-width: 1200px;
  height: 100%;
  padding: 1% 5%;
  width: 100%;
  margin: 0 auto;
  align-items: center;
  display: flex;
  justify-content: center;
  flex-direction: column;
  position: relative;
}

.overDiv {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  max-width: 200px;
}
</style>