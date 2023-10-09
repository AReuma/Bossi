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
{{purchaseInfo}}
    <div style="display: flex; height: 100%; width: 100%; margin-top: 40px">
      <div style="flex: 1.2;">
        <div
            style="width: 100%; text-align: start; font-size: 16px; padding: 15px;"
            class="expanding-box"
            :style="{ height: boxHeight + 'px' }"
            @click="expandBox"
        >

          <div style="display: flex;">
            <div style="width: 30%; font-weight: bold">주문고객</div>

            <div style="width: 70%; text-align: end">
              {{ purchaseInfo.name }} ({{purchaseInfo.phoneNum}})
              <v-icon v-if="!isActive">mdi-chevron-down</v-icon>
              <v-icon v-else>mdi-chevron-up</v-icon>
            </div>
          </div>

          <div v-if="isActive" style="margin-top: 15px">
            <div style="font-size: 14px">
              <p>이름 : {{ purchaseInfo.name }}</p>
              <p>전화번호 : {{purchaseInfo.phoneNum}} <v-btn style="margin-left: 20px" depressed text outlined small @click="changePhoneNum">변경하기</v-btn></p>
            </div>
          </div>
        </div>

        <div class="div-deco" style="margin-top: 20px; height: auto">
          <div style="display: flex;">
            <div style="width: 50%; font-weight: bold">배송 정보</div>
            <div v-if="existDelivery !== true && purchaseInfo.deliveryCheck" style="display: flex; justify-content: end; width: 50%; color: #fc9899">배송지 변경 <v-icon color="DEEP_PINK">mdi-chevron-right</v-icon></div>
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
            <div v-if="!purchaseInfo.deliveryCheck" style="height: 100px; text-align: center; display: flex; justify-content: center; align-items: center">
              배송지가 없습니다.<br/>
              ' 신규 입력 '을 눌러 배송지를 추가해주세요.
            </div>

            <div v-else>
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

          </div>

          <div v-else style="margin-top: 15px; font-size: 15px">
            <table style="width: 100%; height: 100px; font-size: 14px; border-collapse: collapse;">
              <tr style="height: 55px; width: 100%; vertical-align: middle;">
                <td style="width: 25%">수령인 <span style="color: red">*</span></td>
                <td style="width: 80%">
                  <v-text-field v-model="receiver" dense outlined style="margin-left: 5%; height: 35px; font-size: 12px">
                  </v-text-field>
                </td>
              </tr>
              <tr style="height: 100%; vertical-align: middle;">
                <td>주소 <span style="color: red">*</span></td>
                <td>
                  <v-text-field v-model="addrZipcode" readonly prepend-inner-icon="mdi-magnify" dense outlined style="margin-left: 5%; width: 95%; height: 35px; font-size: 12px" @click="addAddr">
                  </v-text-field>

                  <div ref="embed" style="height: auto; margin-left: 5%; margin-top: 10px">

                  </div>
                </td>
              </tr>
              <tr style="height: 45px; vertical-align: middle;">
                <td>상세주소</td>
                <td>
                  <v-text-field v-model="detailAddr" dense outlined style="margin-left: 5%; width: 95%; height: 35px; font-size: 12px">
                  </v-text-field>
                </td>
              </tr>

              <tr style="height: 55px; vertical-align: middle;">
                <td>휴대폰 <span style="color: red">*</span></td>
                <td>
                  <v-text-field v-model="phoneNum" @input="formatPhoneNumber" dense outlined style="margin-left: 5%; width: 95%; height: 35px; font-size: 12px">
                  </v-text-field>
                </td>
              </tr>

              <tr style="height: auto; width: 100%; vertical-align: middle;">
                <td></td>
                <td style="padding-left: 5%; width: 80%; color: red">
                  {{phoneNumberError}}
                </td>
              </tr>

              <tr style="height: 45px; vertical-align: middle;">
                <td>배송지명 <span style="color: red">*</span></td>
                <td>
                  <v-text-field dense outlined style="margin-left: 5%; width: 95%; height: 35px; font-size: 12px" v-model="deliveryName" :readonly="selectedChip !== null">
                  </v-text-field>
                </td>
              </tr>

               <tr style="height: 45px; width: 100%; vertical-align: middle;">
                 <td></td>
                 <td style="padding-left: 5%; width: 80%">
                  <v-chip-group active-class="text--pink lighten-5 DEEP_PINK--text" v-model="selectedChip" @change="handleChipChange">
                    <v-chip value="집" small>집</v-chip>
                    <v-chip value="회사" small>회사</v-chip>
                    <v-chip value="가족" small>가족</v-chip>
                    <v-chip value="친구" small>친구</v-chip>
                    <v-chip value="학교" small>학교</v-chip>
                  </v-chip-group>

                </td>
              </tr>
            </table>

            <v-checkbox v-if="!purchaseInfo.deliveryCheck" readonly v-model="basicAddr" color="DEEP_PINK" label="기본 배송지로 설정" hide-details></v-checkbox>
            <v-checkbox v-else color="DEEP_PINK" v-model="basicAddr" label="기본 배송지로 설정" hide-details></v-checkbox>
          </div>
        </div>

        <div
            style="width: 100%; text-align: start; font-size: 16px; margin-top: 20px"
            class="expanding-box"
            :style="{ height: 'auto'}"
        >
          <div style="display: flex; padding: 15px;">
            <div style="width: 30%; font-weight: bold">주문 작품 정보</div>

            <div style="width: 70%; text-align: end; display: flex; float: right">
              <div style="width: 90%" class="text-container">
                이거 다른 작가의 상품일 경우 외 1건 으로 나오도록 구현
              </div>
              <div style="width: 10%;" @click="expandOrderBox">
                <v-icon v-if="!isActiveOrder">mdi-chevron-down</v-icon>
                <v-icon v-else>mdi-chevron-up</v-icon>
              </div>
            </div>
          </div>

          <div v-if="isActiveOrder">
            <div style="padding-left: 2%; display: flex; align-items: center; background-color: rgba(187,187,187,0.15); height: 60px">
              <div style="padding-left: 15px">{{purchaseInfo.storeName}} 작가님</div>
            </div>

            <div style="padding: 0 15px 15px 15px;">
              <div style="margin-top: 15px; display: flex">
                <div>
                  <v-img :src="`https://s3.ap-northeast-2.amazonaws.com/my.example.s3.bucket.bossi/${purchaseInfo.productImg}`" width="100" height="100" style="border-radius: 4px"/>
                </div>
                <div style="display: flex; align-items: center; height: 100px; padding-left: 8px; font-weight: bold">
                  {{purchaseInfo.productTitle}}
                </div>
              </div>

              <div style="margin-top: 10px; font-size: 12px">
                <div v-for="(options, index) in purchaseInfo.optionInfo" :key="index" style="margin-top: 5px;">
                  <div style="display: flex;">
                    <div style="width: 70%">
                      <div v-for="(option, index) in options" :key="index">
                        <span style="font-size: 15px">•</span> {{option}}
                      </div>
                    </div>
                    <div style="width: 30%; text-align: end">{{purchaseInfo.optionPrice[index].toLocaleString()}}원</div>
                  </div>
                  <div> <span style="font-size: 15px">•</span> 수량 : {{purchaseInfo.orderCount[index]}} 개</div>
                </div>
              </div>

              <div v-if="orderMsg !== null">
                <textarea v-model="orderMsg" placeholder="주문 요청사항을 입력해주세요"></textarea>
              </div>
            </div>
          </div>

          <div v-if="isActiveOrder" style="height: 55px;">
            <hr/>
            <div style="display: flex; font-size: 14px; padding: 15px">
              <div style="width: 70%">배송비</div>
              <div style="width: 30%; text-align: end">{{purchaseInfo.deliveryPrice.toLocaleString()}}원</div>
            </div>
          </div>

        </div>

        <div v-if="purchaseInfo.point > 0" class="div-deco" style="margin-top: 20px; height: auto; ">
          <div style="width: 50%; font-weight: bold">Bossi 할인 혜택</div>
          <div style="margin-top: 15px; font-size: 14px">
            <div>Bossi 적립금</div>
            <div style="display: flex">
              <input :readonly="usePointCheck" v-model="usePoint" min="0" type="text" style="border: 1px solid #626262; width: 80%; border-radius: 4px; height: 28px; background-color: rgba(236, 235, 235, 0.15); color: #fc9899; padding-left: 4px">

              <div v-if="!usePointCheck">
                <v-btn v-if="usePoint === 0 || usePoint === ''" @click="useAllPointBtn" height="28" style="font-size: 11px; color: white; margin-left: 5px; width: 18%" color="DEEP_PINK" depressed>
                  전부 사용
                </v-btn>

                <v-btn v-else @click="usePointBtn" height="28" style="font-size: 11px; color: white; margin-left: 5px; width: 18%" color="DEEP_PINK" depressed>
                  사용
                </v-btn>
              </div>

              <div v-else>
                <v-btn @click="delPoint" height="28" style="font-size: 11px; color: black; margin-left: 5px; width: 18%" depressed>
                  취소
                </v-btn>
              </div>

            </div>
            <div style="margin-top: 5px">
              보유중인 적립금 <span style="color: #fc9899"> {{purchaseInfo.point}}p</span>
            </div>
          </div>
        </div>

        <div class="div-deco" style="margin-top: 20px; height: auto">
          <div style="width: 50%; font-weight: bold;">결제 수단</div>
        </div>
      </div>

      <div class="div-deco" style="flex: 0.8; height: 450px; margin-left: 25px; padding-top: 20px">
        <div style="width: 100%; text-align: center; font-weight: bold; font-size: 18px; padding-bottom: 20px; border-bottom: 1px solid black">결제 정보</div>
        <div style="margin-top: 20px">
          <table style="width: 100%; border-collapse: collapse;">
            <tr>
              <td>작품 금액</td>
              <td class="text-right">{{purchaseInfo.totalProductPrice.toLocaleString()}}원</td>
            </tr>

            <tr>
              <td>배송비</td>
              <td class="text-right">{{purchaseInfo.deliveryPrice.toLocaleString()}}원</td>
            </tr>

            <tr>
              <td>적립금</td>
              <td class="text-right" v-if="!usePointCheck">0원</td>
              <td class="text-right" v-else>{{usePoint.toLocaleString()}}원</td>
            </tr>

            <tr style="height: 50px; vertical-align: top">
              <td>작가님 할인 혜택</td>
              <td class="text-right">0원</td>
            </tr>

            <tr style="border-top: 1px solid rgba(128,127,127,0.64); vertical-align: middle">
              <td style="font-size: 18px; font-weight: bold;">최종 결제 금액</td>
              <td class="text-right" style="height: 60px; font-size: 18px; font-weight: bold;">{{totalPrice.toLocaleString()}}원</td>
            </tr>
          </table>
        </div>

        <div>
          결제 시 개인정보 제공에 동의합니다.
        </div>

        <div style="margin-top: 40px">
          <v-btn @click="payment" color="DEEP_PINK" depressed height="80" width="100%" style="font-size: 18px; color: white; font-family: GmarketSansBold,sans-serif">
            <div>
              <div>{{totalPrice.toLocaleString()}}원 결제하기</div>
            <div style="font-family: GmarketSansMedium,sans-serif; margin-top: 8px; font-size: 14px">예상적금: {{purchaseInfo.expectPoint}}p</div>
            </div>
          </v-btn>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {defineComponent} from 'vue'
import axios from "axios";
import {API_BASE_URL} from "@/constant/basic";
import {useCookies} from "vue3-cookies";
export default defineComponent({
  name: "PurchaseDeliveryView",
  props: ['purchaseInfo'],
  data() {
    return {
      boxHeight: 55,
      boxOrderHeight: 'auto',
      isActive: false,
      isActiveOrder: false,
      existDelivery: true,
      basicAddr: true,
      addApiShow: false,
      receiver: '',
      address: '',
      addrZipcode: '',
      detailAddr: '',
      zipcode: '',
      phoneNum: '',
      phoneNumPatternCheck: false,
      deliveryName: '',
      selectedChip: null,
      phoneNumberError: '',
      orderMsg: useCookies().cookies.get('orderMsg'),
      productId: useCookies().cookies.get('productId'),
      options: useCookies().cookies.get('options'),
      optionCount: useCookies().cookies.get('optionCount'),
      email: useCookies().cookies.get('email'),
      usePoint: 0,
      usePointCheck: false,
      totalPrice: 0,
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
      /*if(this.isActiveOrder){
        this.boxOrderHeight += 250;
      }else{
        this.boxOrderHeight -= 250;
      }*/
    },
    existBtn(){
      this.existDelivery = true
    },
    newBtn(){
      this.existDelivery = false
    },
    addAddr(){  // 주소 api 사용하기
      new window.daum.Postcode({
        width: '300px',
        height: '500px',
        oncomplete: (data) => {
          console.log(data)
          this.zipcode = data.zonecode;
          this.address = data.address;

          this.addrZipcode = '('+this.zipcode+') '+this.address;
        }
      }).embed(this.$refs.embed)
    },
    changePhoneNum(){
      alert('change')
    },
    handleChipChange(value) {
      console.log(value)
      if(value === undefined) {
        this.deliveryName = ''
        this.selectedChip = null
      }
      else this.deliveryName = value;
    },
    formatPhoneNumber() {
      const pattern = /^010\d{8}$/;
      if (!this.phoneNum.match(pattern)) {
        this.phoneNumberError = '올바른 전화번호 형식이 아닙니다.';
      } else {
        this.phoneNumberError = '';
        this.phoneNumPatternCheck = true;
        this.phoneNum = this.phoneNum.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');
      }
    },
    createOrderNum(){
      const currentDate = new Date();
      const timestamp = currentDate.getTime(); // 현재 시간을 밀리초로 가져옵니다.
      const randomDigits = Math.floor(Math.random() * 9000) + 1000; // 4자리 랜덤 숫자 생성
      return `ORD${timestamp}${randomDigits}`;
    },
    readyPayment(){
      // 결제하기전 배송지가 제대로 작성된지 확인.
      // 1. 기존 배송지인지, 신규 입력인지 체크
      // 2. 기존 배송지 일경우 delivdryCheck === true 일 경우 결제
      // 3. 신규 입력일 일 경우 수령인, 주소, 휴대폰, 배송지명 체크
      // 4. 비워 있을 경우 alert 창 띄우기

      let paymentCheck = false;

      if(this.existDelivery === true){
        if (this.purchaseInfo.deliveryCheck === true) {
          paymentCheck = true;
        }
      }else {
        if(this.receiver !== '' && this.address !== '' && this.phoneNum !== '' && this.deliveryName !== '' && this.phoneNumPatternCheck === true){
          paymentCheck = true;
        }
      }

      return paymentCheck;
    },
    payment(){
      let check = this.readyPayment();

      if(check === true) {
        let IMP = window.IMP;
        IMP.init("imp73123883");

        let price = this.purchaseInfo.totalPrice;

        let orderUser = this.purchaseInfo.name;
        let orderPhoneNum = this.purchaseInfo.phoneNum;
        IMP.request_pay(
            {
              pg: "kakaopay",
              pay_method: "card",
              merchant_uid: this.createOrderNum(),// 주문번호
              name: this.purchaseInfo.productTitle,
              amount: this.purchaseInfo.totalPrice,
              //buyer_email: "gildong@gmail.com",
              buyer_name: this.purchaseInfo.name,
              //buyer_tel: "010-4242-4242",
              //buyer_addr: "서울특별시 강남구 신사동",
              //buyer_postcode: "01181"
            },
            function (rsp) {
              //rsp.imp_uid 값으로 결제 단건조회 API를 호출하여 결제결과를 판단합니다.
              let imp_uid = rsp.imp_uid;
              console.log("imp_uid" + imp_uid)

              if (rsp.success) { // 결제가 성공했을때
                //서버 검증 요청 부분
                axios.post(API_BASE_URL + '/api/v1/payment/verify/' + rsp.imp_uid)
                    .then((res) => {
                      console.log(res)

                      if (res.data.response.amount === price) { // 결제 금액이랑 상품 금액이 같을 경우
                        console.log('결제')
                        // 주문 고객 이름, 전화번호
                        // 기존 배송지일 경우/ 신규 배송지일 경우 배송지 db에 저장
                        // 적립금, 상품, 옵션, 수량

                        if(this.existDelivery === false){
                          // 저장된 배송지가 아닐 경우
                          const {productId, options, optionCount, email, receiver, address, detailAddr, zipcode, phoneNum, deliveryName, orderMsg, usePoint} = this;

                          axios.post(API_BASE_URL+"/api/v1/payment/order/complete", {productId, options, optionCount, email, receiver, address, detailAddr, zipcode, phoneNum, deliveryName, orderMsg, usePoint, orderUser, orderPhoneNum})
                        }

                      } else { // 결제 금액이 달라 실패한 경우
                        alert('결제 실패')
                      }
                    })
                    .catch((res) => {
                      console.log(res)
                    })
              } else {
                alert("결제에 실패하였습니다. 에러 내용: " + rsp.error_msg);
              }
            }
        )
      }else {
        alert('결제 실패')
      }
    },
    usePointBtn(){
      this.usePoint = Math.floor(this.usePoint / 10) * 10;
      let maximumPrice = Math.floor(Number(this.purchaseInfo.point) / 10) * 10

      if (this.usePoint > maximumPrice) {
        this.usePoint = maximumPrice;
        alert('보유하는 포인트보다 더 사용할 수 없습니다')
      }

      this.totalPrice -= this.usePoint;
      this.usePointCheck = true;
    },
    useAllPointBtn(){
      let maximumPrice = Math.floor(Number(this.purchaseInfo.point) / 10) * 10
      this.usePoint = maximumPrice;

      this.totalPrice -= this.usePoint;
      this.usePointCheck = true;
    },
    delPoint(){
      this.usePointCheck = false;
      this.totalPrice += this.usePoint;
      this.usePoint = 0;
    }
  },
  mounted() {

  },
  watch: {
    purchaseInfo: {
      handler(newVal){
        if (newVal) {
          this.totalPrice = newVal.totalPrice;
        }
      },
      deep: true
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
  cursor: pointer;
  transition: height 0.2s;
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

.text-container {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

textarea {
  margin-right: 10px;
  height: 150px;
  width: 100%;
  border: 1px solid rgba(106,106,106,0.5);
  resize: none;
}

textarea:focus {
  outline: none;
}
</style>