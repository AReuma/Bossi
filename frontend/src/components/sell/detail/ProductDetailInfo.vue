<template>
  <div style="height: 100%; width: 50%; padding: 10px; border: 1px solid rebeccapurple">
    <v-card height="670px" width="100%" style="padding: 10px; border: 1px solid #bbbbbb" elevation="0">
      <div style="display: flex; align-items: center; border: 1px solid pink">
        <img style="border-radius: 80%; border: 1px solid rgba(187,187,187,0.43); margin-right: 10px" src="../../../assets/logo/Bossi_logo_1.png" height="50" width="50"/>
        <div style="font-family: GmarketSansBold, sans-serif">동그라미</div>
      </div>

      <div style="margin-top: 10px; font-size: 22px; border: 1px solid blue">
        <span>⭐5일전 선주문순 순차배송!🍪1인4팩만~ 파지약과❤️ </span>
      </div>

      <div style="margin-top: 5px; display: flex; border: 1px solid black">
        <div style="width: 80%; border: 1px solid red; display: flex; align-items: end">
          <div class="boldText" style="color: red">11%</div>
          <div class="boldText" style="">5,800원</div>
          <div style="font-size: 15px; padding-bottom: 5px; color: #bbbbbb"><del>6,500원</del></div>
        </div>
        <div style="width: 20%; display: flex; justify-content: end; padding-right: 5px">
          <v-icon size="32">mdi-heart</v-icon>
        </div>
      </div>

      <div style="display: flex; justify-content: end">
        <span style="font-family: GmarketSansBold, sans-serif">4,454명</span> 구매
      </div>

      <div style="border: 1px solid olivedrab">
        <table style="border: 1px solid red">
          <tr>
            <td class="table-name">적립금</td>
            <td>최대 34p</td>
          </tr>

          <tr>
            <td class="table-name">구매후기</td>
            <td><v-rating></v-rating></td>
          </tr>

          <tr>
            <td class="table-name">배송비</td>
            <td rowspan="2">3,000원 <br/> <span style="font-size: 13px; color: rgba(106,106,106,0.89)">45,000원 이상 무료배송</span></td>
          </tr>
          <tr>
            <td class="table-name"></td>
          </tr>
          <tr>
            <td class="table-name">배송 시작</td>
            <td>평균 1일, 최대 7일 이내</td>
          </tr>
          <tr>
            <td class="table-name">수량</td>
            <td>주문시 제작</td>
          </tr>

        </table>
      </div>

      <div style="border: 1px solid darkgoldenrod; margin-top: 5px; height: 60px">

      </div>

      <div style="display:flex; width: 100%; border: 1px solid rosybrown; margin-top: 5px">
        <div style="width: 20%">총 작품금액</div>
        <div style="display:flex; font-family: GmarketSansBold,sans-serif; justify-content: end; width: 80%; border: 1px solid red"> 0원</div>
      </div>

      <div style="margin-top: 8px; border: 1px solid rebeccapurple;">
        <v-btn depressed height="50px" text class="buy-button" style="border: 1px solid rgba(187,187,187,0.62)" >장바구니</v-btn>
        <v-btn depressed color="green" height="50px" class="buy-button" style="color: white">NPay</v-btn>
        <v-btn depressed height="50px" color="DEEP_PINK" class="buy-button" style="color: white">구매하기</v-btn>
        <v-btn depressed height="50px" text width="8%" style="margin: 2px; border: 1px solid #fc9899; color: #fc9899">
          <div class="vertical-text"><v-icon size="22">mdi-gift-outline</v-icon> <div style="font-size: 11px; margin-top: 2px">선물하기</div></div>
        </v-btn>
      </div>

      <hr style="border: 1px solid rgba(67,79,88,0.11); margin: 12px 0;"/>

      <div style="border: 1px solid #0054f3; height: 70px; padding: 12px 0; display: flex; font-size: 13px; align-items: center">
        <v-btn depressed height="45px" width="30%" text style="border: 1px solid rgba(187, 187, 187, 0.6); margin-right: 7px">
          <v-icon color="DEEP_PINK" style="margin-right: 4px;">mdi-message-question-outline</v-icon>
          <span style="color: #fc9899; font-family: GmarketSansBold, sans-serif">작품문의</span>
        </v-btn>

        <div>
          작품 및 배송 관련 문의는 작품문의 버튼을 이용해주세요.
        </div>
      </div>
    </v-card>
  </div>
</template>

<script>
import {defineComponent} from 'vue'

export default defineComponent({
  name: "ProductDetailInfo"
})
</script>

<style scoped>
.boldText{
  font-size: 28px;
  font-family: GmarketSansBold, sans-serif;
  margin-right: 10px;
}
table {
  width: 100%;
  border: 1px solid #444444;
  border-collapse: collapse;
}

th, td {
  /*border: 1px solid #444444;*/
  padding: 5px;
}

.buy-button {
  height: 50px;
  width: 28%;
  margin: 2px;
}

.table-name {
  width: 100px;
}
.vertical-text {
  text-align: center; /* 텍스트를 가운데 정렬합니다 */
  margin-top: 8px; /* 아이콘과 텍스트 사이의 간격을 조정합니다 */
}
</style>