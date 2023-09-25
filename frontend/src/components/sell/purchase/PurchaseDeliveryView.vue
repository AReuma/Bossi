<template>
  <div id="basic">
    <div style="width: 100%; display: flex;">
      <div style="width: 50%; font-size: 24px; font-family: GmarketSansBold, sans-serif">주문 결제하기</div>
      <div style="display: flex; width: 50%; justify-content: end; align-items: center; font-size: 17px">
        <div style="color: #848484">1. 장바구니 &nbsp;&nbsp;>&nbsp;&nbsp; </div>
        <div style="font-family: GmarketSansBold, sans-serif">2. 주문 결제 &nbsp;&nbsp;>&nbsp;&nbsp; </div>
        <div style="color: #848484">3. 주문완료</div>
      </div>
    </div>

    <div style="display: flex; height: 100%; width: 100%; margin-top: 40px">
      <div style="flex: 1;">
        <div
            style="width: 100%; text-align: start; font-size: 16px"
            class="expanding-box"
            :style="{ height: boxHeight + 'px' }"
            @click="expandBox"
        >
          <div style="width: 80%; font-weight: bold">주문고객</div>

          <div style="width: 20%; text-align: end">
            <v-icon v-if="!isActive">mdi-chevron-down</v-icon>
            <v-icon v-else>mdi-chevron-up</v-icon>
          </div>
        </div>

        <div class="div-deco" style="margin-top: 20px; height: auto">
          <div style="display: flex;">
            <div style="width: 50%; font-weight: bold">배송 정보</div>
            <div style="display: flex; justify-content: end; width: 50%; color: #fc9899">배송지 변경 <v-icon color="DEEP_PINK">mdi-chevron-right</v-icon></div>
          </div>

          <div style="margin-top: 15px; width: 100%" v-if="existDelivery">
            <v-btn @click="existBtn" style="width: 50%; border-top: 2px solid #626262; border-top-left-radius: 0; border-top-right-radius: 0" height="50" depressed color="rgba(236, 235, 235, 0.15)">기존 배송지</v-btn>
            <v-btn @click="newBtn" style="width: 50%; border-bottom: 0.5px solid rgba(98,98,98,0.27); border-bottom-left-radius: 0; border-bottom-right-radius: 0" height="50" depressed>신규 입력</v-btn>
          </div>

          <div style="margin-top: 15px; width: 100%" v-else>
            <v-btn @click="existBtn" style="width: 50%; border-bottom: 0.5px solid rgba(98,98,98,0.27); border-bottom-left-radius: 0; border-bottom-right-radius: 0" height="50" depressed>기존 배송지</v-btn>
            <v-btn @click="newBtn" style="width: 50%; border-top: 2px solid #626262; border-top-left-radius: 0; border-top-right-radius: 0" height="50" depressed color="rgba(236, 235, 235, 0.15)">신규 입력</v-btn>
          </div>

          <div v-if="existDelivery" style="margin-top: 10px; font-size: 15px; padding: 5px">
            <div><v-chip small color="rgba(251,191,191,0.2)" style="color: #fc9899; margin-right: 5px">기본 배송지</v-chip>신니니</div>
            <div style="margin-top: 8px">
              010-1234-1234
            </div>
            <div style="margin-top: 8px">
              (12345) 서울시 강남구 한강 어디어디 104동
            </div>
            <div style="margin-top: 8px; font-size: 12px">
              <v-icon small>mdi-pencil-outline</v-icon>
              정보 수정
            </div>
          </div>

          <div v-else style="margin-top: 15px; font-size: 15px">
            배송 정보
          </div>
        </div>

        <div
            style="width: 100%; text-align: start; font-size: 16px; margin-top: 20px"
            class="expanding-box"
            :style="{ height: boxOrderHeight + 'px' }"
            @click="expandOrderBox"
        >
          <div style="width: 80%; font-weight: bold">주문 작품 정보</div>

          <div style="width: 20%; text-align: end">
            <v-icon v-if="!isActiveOrder">mdi-chevron-down</v-icon>
            <v-icon v-else>mdi-chevron-up</v-icon>
          </div>
        </div>

        <div class="div-deco" style="margin-top: 20px; height: auto; ">
          <div style="width: 50%; font-weight: bold">Bossi 할인 혜택</div>
          <div style="margin-top: 15px; font-size: 14px">
            <div>Bossi 적립금</div>
            <div>
              <input type="text" style="border: 1px solid #626262; width: 80%; border-radius: 4px; height: 28px; background-color: rgba(236, 235, 235, 0.15); color: #fc9899; padding-left: 4px" value="0">
              <v-btn height="28" style="font-size: 11px; color: white; margin-left: 5px; width: 18%" color="DEEP_PINK" depressed>전부 사용</v-btn>
            </div>
            <div style="margin-top: 5px">
              보유중인 적립금<span style="color: #fc9899">29p</span>
            </div>
          </div>
        </div>

        <div class="div-deco" style="margin-top: 20px; height: auto">
          <div style="width: 50%; font-weight: bold;">결제 수단</div>
        </div>
      </div>

      <div class="div-deco" style="flex: 1; height: 450px; margin-left: 25px; padding-top: 20px">
        <div style="width: 100%; text-align: center; font-weight: bold; font-size: 18px; padding-bottom: 20px; border-bottom: 1px solid black">결제 정보</div>
        <div style="margin-top: 20px">
          <table style="width: 100%; border-collapse: collapse;">
            <tr>
              <td>작품 금액</td>
              <td class="text-right">45,000원</td>
            </tr>

            <tr>
              <td>배송비</td>
              <td class="text-right">3,000원</td>
            </tr>

            <tr style="height: 50px; vertical-align: top">
              <td>작가님 할인 혜택</td>
              <td class="text-right">0원</td>
            </tr>

            <tr style="border-top: 1px solid rgba(128,127,127,0.64); vertical-align: middle">
              <td style="font-size: 18px; font-weight: bold;">최종 결제 금액</td>
              <td class="text-right" style="height: 60px; font-size: 18px; font-weight: bold;">48,000원</td>
            </tr>
          </table>
        </div>

        <div>
          결제 시 개인정보 제공에 동의합니다.
        </div>

        <div style="margin-top: 40px">
          <v-btn color="DEEP_PINK" depressed height="80" width="100%" style="font-size: 18px; color: white; font-family: GmarketSansBold,sans-serif">
            <div>
              <div>48,000원 결제하기</div>
            <div style="font-family: GmarketSansMedium,sans-serif; margin-top: 8px; font-size: 14px">예상적금:45p</div>
            </div>
          </v-btn>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {defineComponent} from 'vue'

export default defineComponent({
  name: "PurchaseDeliveryView",
  data() {
    return {
      boxHeight: 55,
      boxOrderHeight: 55,
      isActive: false,
      isActiveOrder: false,
      existDelivery: true
    };
  },
  methods: {
    expandBox() {
      this.isActive = !this.isActive;
      if(this.isActive){
        this.boxHeight += 100;
      }else{
        this.boxHeight -= 100;
      }
    },
    expandOrderBox(){
      this.isActiveOrder = !this.isActiveOrder;
      if(this.isActiveOrder){
        this.boxOrderHeight += 250;
      }else{
        this.boxOrderHeight -= 250;
      }
    },
    existBtn(){
      this.existDelivery = true
    },
    newBtn(){
      this.existDelivery = false
    }
  }
})
</script>

<style scoped>
#basic{
  max-width: 1000px;
  height: 100%;
  padding: 3% 5%;
  width: 100%;
  margin: 0 auto;
  align-items: center;
  display: flex;
  justify-content: center;
  flex-direction: column;
  position: relative;
}

.expanding-box {
  background-color: rgba(236, 235, 235, 0.15);
  border-radius: 4px;
  border: 1px solid #626262;
  padding: 15px;
  cursor: pointer;
  transition: height 0.2s;
  display: flex;
}

.div-deco {
  background-color: rgba(236, 235, 235, 0.15);
  border-radius: 4px;
  border: 1px solid #626262;
  padding: 15px;
}

input:focus{
  outline: none; /* 포커스 테두리 제거 */
  border: 1px solid #ccc; /* 포커스되지 않은 상태의 테두리 스타일 */
}

tr{
  height: 30px;
  vertical-align: top;
}
</style>