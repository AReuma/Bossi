<template>
  <div class="basic" v-if="productInfo.length !== 0">
  <div style="width: 100%; display: flex;">
    <div style="width: 50%; font-size: 28px; font-family: GmarketSansBold, sans-serif">장바구니</div>
    <div style="display: flex; width: 50%; justify-content: end; align-items: center; font-size: 20px">
      <div style="font-family: GmarketSansBold, sans-serif">1. 장바구니 &nbsp;&nbsp;>&nbsp;&nbsp; </div>
      <div style="color: #848484">2. 주문 결제 &nbsp;&nbsp;>&nbsp;&nbsp; </div>
      <div style="color: #848484">3. 주문완료</div>
    </div>
  </div>

  <div v-for="(cartInfo, index) in productInfo" :key="index" style="border: 1px solid #626262; width: 100%; height: 100%; margin-top: 40px; border-radius: 4px">
    <div style="padding-left: 2%; display: flex; align-items: center; background-color: rgba(187,187,187,0.15); height: 60px">
      <v-checkbox v-model="selectedProduct[index]" color="DEEP_PINK"></v-checkbox><div style="margin-left: 5px; font-size: 18px" @click="test(index)">{{cartInfo.storeName}}</div>
    </div>

    <div style="display: flex; padding: 0 2%; height: 100%; flex-direction: column">
      <div style="width: 100%; height: 25%; align-items: center; display: flex; margin-top: 15px">
<!--        <v-checkbox  v-model="selectedProduct" readonly color="DEEP_PINK"></v-checkbox>-->
        <div>
          <v-img :src="`https://s3.ap-northeast-2.amazonaws.com/my.example.s3.bucket.bossi/${cartInfo.productImg}`" width="100" height="100" style="border-radius: 4px"/>
        </div>
        <div style="width: 100%; padding-left: 2%">
          <div style="font-weight: bolder; font-size: 16px">{{cartInfo.productName}}</div>
          <div style="color: #848484; font-size: 14px" v-if="cartInfo.stockQuantity === '-1'">수량 제한 없음</div>
          <div style="color: #848484; font-size: 14px" v-else>{{ cartInfo.stockQuantity }}개</div>
        </div>
      </div>

      <div style="margin-top: 30px; padding: 0 10px 0 155px">
        <div v-for="(option, index2) in cartInfo.optionStr" :key="index2" style="border-bottom: #848484 1px solid; height: 50px; padding: 0 10px; display: flex; align-items: center; font-size: 15px">
          <div style="max-width: 50%; min-width: 50%; font-size: 13px">
            {{option}}
          </div>
          <div style="padding-left: 2%; max-width: 23%; min-width: 23%">
            <v-btn class="custom-btn" min-width="2" outlined style="border: 1px solid rgba(106,106,106,0.5);" @click="orderListMin(index, index2)">-</v-btn>
            <input readonly :style="{outline: 'none'}" v-model="cartInfo.optionCount[index2]" type="text" style="width: 40%; text-align: center">
            <v-btn class="custom-btn" outlined min-width="2" style="border: 1px solid rgba(106,106,106,0.5)" @click="orderListPlus(index, index2)">+</v-btn>
          </div>
          <div style="display: flex; align-items: center; justify-content: end; max-width: 25%; min-width: 25%;">
            <div style="margin-left: 15px; font-size: 13px;">
              {{ cartInfo.optionTotalPrice[index2].toLocaleString() }}원
              <v-btn icon @click="settingOption(index2)"><v-icon>mdi-cog</v-icon></v-btn>
              <v-btn icon @click="removeOption(index2)"><v-icon>mdi-window-close</v-icon></v-btn>
            </div>
          </div>
        </div>
      </div>

      <div>
        <div style="display: flex;  align-items: center; height: 200px">
          <v-textarea v-model="orderMsg[index]" label="주문 요청사항을 입력해주세요" outlined style="margin-top: 45px; margin-right: 10px" auto-grow @input="checkTextLength(index)">

          </v-textarea>
          <v-btn depressed v-if="showSave[index]" style="width: 150px; height: 120px; margin-top: 8px; outline: rgba(132,132,132,0.27) 1px solid" @click="saveOrderMsg(index)">
            저장
          </v-btn>
        </div>
        <p v-if="showWarning[index]">
          <v-icon>mdi-alert-circle</v-icon>  입력 후 저장버튼을 눌러주세요
        </p>
      </div>

    </div>

    <div style="margin-bottom: 5px">
      <table style="width: 100%; border-collapse: collapse; ">
        <tr style="height: 60px; border-bottom: 1px solid black; border-top: 1px solid black;">
          <td class="table-padding">작품 가격</td>
          <td class="table-padding" style="font-weight: bold; text-align: end">{{cartInfo.totalPrice.toLocaleString()}}원</td>
        </tr>

        <tr>
          <td v-if="cartInfo.freeDeliverTotalCharge !== -1" class="table-padding" rowspan="2" style="height: 80px;">배송비</td>
          <td v-else class="table-padding" style="height: 40px; vertical-align: center">배송비</td>
          <td class="table-padding" style="font-weight: bold; text-align: end; vertical-align: bottom" v-if="!deliveryFreeCheck[index]">{{cartInfo.deliveryCharge.toLocaleString()}}원</td>
          <td class="table-padding" style="font-weight: bold; text-align: end; vertical-align: bottom" v-else>무료배송</td>
        </tr>

        <tr>
          <td class="table-padding" style="font-size: 12px; text-align: end"> {{ cartInfo.freeDeliverTotalCharge.toLocaleString() }}이상 무료배송</td>
        </tr>
      </table>
    </div>
  </div>

  <div style="min-height: 250px; max-height: 250px; width: 100%;"></div>

  <div class="fixed-bottom">
    <div style="background-color: rgba(255,255,255,0.88);">
      <div style="display: flex; align-items: center; height: 40px">
        <v-checkbox v-model="selectAllProduct" @click="clickAll()" color="DEEP_PINK"></v-checkbox>전체 선택 ({{selectedCount}}/{{productInfo.length}})
      </div>
      <div style="min-height: 150px; border: 1px solid black; border-radius: 7px; display: flex; align-items: center; justify-content: center">
        <table style="width: 100%; text-align: center">
          <tr>
            <td>작품금액</td>
            <td></td>
            <td>배송비</td>
            <td></td>
            <td>결제 예정 금액</td>
          </tr>
          <tr>
            <td class="table-border">{{totalPrice.toLocaleString()}} 원</td>
            <td class="table-border">+</td>
            <td class="table-border">{{ totalDelivery.toLocaleString() }} 원</td>
            <td class="table-border">=</td>
            <td class="table-border">{{ (totalPrice + totalDelivery).toLocaleString() }} 원</td>
          </tr>
        </table>
      </div>
      <div style="padding: 15px 0;">
        <v-btn @click="order" style="width: 100%; height: 50px; font-size: 18px; color: white" color="DEEP_PINK" depressed>주문하기</v-btn>
      </div>

    </div>
  </div>

<!--  <v-dialog v-model="optionDialog" persistent height="auto" width="500px">
    <v-card>
      <v-card-title style="justify-content: center; border-bottom: 1px solid black">
        <v-spacer></v-spacer>
        옵션 수정
        <v-spacer></v-spacer>
        <v-btn icon @click="optionDialog = false"><v-icon small>mdi-window-close</v-icon></v-btn>
      </v-card-title>


      <v-card-text>
        <div style="padding: 10px">
          <div style="display: flex; margin-top: 20px">
            <v-img :src="`https://s3.ap-northeast-2.amazonaws.com/my.example.s3.bucket.bossi/${directOrderList.productImg}`" style="border-radius: 4px; max-width: 90px; height: 90px"/>
            <div style="display: flex; flex-direction: column; margin-left: 12px; ">
              <div style="font-weight: bolder; font-size: 24px; margin-top: 4px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis; color: black">
                {{directOrderList.productName}}
              </div>

              <div style="display: flex; margin-top: 4px">
                <div class="boldText" style="color: red">{{ directOrderList.rating }}%</div>
                <div class="boldText" style="">{{ directOrderList.ratingPrice }} 원</div>
                <div style="font-size: 15px; padding-bottom: 5px; color: #bbbbbb"><del>{{ directOrderList.price }}원</del></div>
              </div>
            </div>
          </div>

          <div style="margin-top: 15px">
            <div style="color: #fc9899">현재 선택한 옵션</div>
            <div style="height: auto; background-color: rgba(187,187,187,0.15); border-radius: 4px; padding: 15px">
              {{directOrderList.optionStr[checkIndex]}}
            </div>
          </div>

          <div style="margin-top: 15px">
            <div style="">옵션 선택</div>
            <div style="height: 200px; width: 100%; display: flex; flex-direction: column; margin-top: 10px">
              <div v-for="(item, index) in directOrderList.productOption" :key="index">
                <v-select :style="{outline: 'none'}" color="DEEP_PINK" v-model="selectedOptions[index]" :items="getOptionDetails(item)" item-text="label"
                          item-value="value" :label="(index+1)+'.  '+item.option" style="padding-bottom: 2px"></v-select>
              </div>
            </div>
          </div>
        </div>

      </v-card-text>

      <v-card-actions style="justify-content: center">
        <v-btn depressed style="border: 1px solid #8e8e8e; background-color: white" @click="closeOptionDialog">
          취소
        </v-btn>
        <v-btn color="DEEP_PINK" depressed style="color: white" @click="modifyOptionDialog">
          확인
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>-->
  </div>

  <div class="basic" style="justify-content: center; align-items: center; height: 500px;" v-else>
    <div style="width: 100%; text-align: center; font-size: 22px">
      장바구니가 비었습니다.<br>
      작가님들의 다양한 작품을 담아보세요.
    </div>

    <div style="padding: 15px; margin-top: 40px; width: 240px">
      <v-btn @click="gHome" style="width: 100%; height: 50px; font-size: 18px; color: white" color="DEEP_PINK" depressed>작품 구경하기</v-btn>
    </div>
  </div>
</template>

<script>
import {defineComponent} from 'vue'
import {useCookies} from "vue3-cookies";

export default defineComponent({
  name: "CartMultiView",
  props: ['myCartInfo'],
  data() {
    return {
      totalPrice: 0,
      totalDelivery: 0,
      productInfo: [],
      orderMsg: [],
      showSave: [],
      showWarning: [],
      selectedProduct: [],
      selectAllProduct: false,
      orderData: [],
      deliveryFreeCheck: []
    }
  },
  watch: {
    myCartInfo: {
      handler(newVal) {
        if(newVal) {
          for (const newValElement of newVal) {
            this.showSave.push(false);
            this.showWarning.push(false);
            this.selectedProduct.push(true);

            this.orderMsg.push("");
            const clonedElement = { ...newValElement };

            clonedElement.optionTotalPrice = newValElement.optionTotalPrice.map((price, index) => {
              return price * newValElement.optionCount[index];
            });
            clonedElement.optionCount = [...newValElement.optionCount]; // 내부 배열 복제

            this.productInfo.push(clonedElement);
            this.totalPrice += clonedElement.totalPrice;


            if(clonedElement.totalPrice >= clonedElement.freeDeliverTotalCharge) {
              this.deliveryFreeCheck.push(true)
            }else{
              this.totalDelivery += clonedElement.deliveryCharge;
              this.deliveryFreeCheck.push(false)
            }

          }
        }
      },
      deep: true,
    },
    selectedCount(newCount) {
      if (newCount === this.productInfo.length) {
        this.selectAllProduct = true;
      } else {
        this.selectAllProduct = false;
      }
    },
    totalPrice: {
      handler() {
        this.updateTotalPriceAndDelivery(this);
      },
      immediate: true, // 컴포넌트가 마운트될 때도 호출
    },
  },
  methods: {
    orderListMin(index, optionIndex){
      console.log(index)
      if(this.productInfo[index].optionCount[optionIndex] > 1){
        console.log("dk")
        this.productInfo[index].optionCount[optionIndex]--;

        console.log("1: "+this.productInfo[index].optionTotalPrice[optionIndex] +" 2: "+ this.myCartInfo[index].optionTotalPrice[optionIndex]+" 3: "+ this.productInfo[index].optionCount[optionIndex])
        this.productInfo[index].optionTotalPrice[optionIndex] -= this.myCartInfo[index].optionTotalPrice[optionIndex];

        // 전체 가격 변경
        this.productInfo[index].totalPrice -= this.myCartInfo[index].optionTotalPrice[optionIndex];
        this.totalPrice -= this.myCartInfo[index].optionTotalPrice[optionIndex];
        this.$forceUpdate();
      }else {
        alert('주문 수량이 1보다 작을 수 없습니다.')
      }
    },
    orderListPlus(index, optionIndex){
      this.productInfo[index].optionCount[optionIndex]++;
      console.log("1: "+this.productInfo[index].optionTotalPrice[optionIndex] +" 2: "+ this.myCartInfo[index].optionTotalPrice)
      //this.productInfo[index].optionTotalPrice[optionIndex] = this.myCartInfo[index].optionTotalPrice[optionIndex] * this.productInfo[index].optionCount[optionIndex];
      //this.productInfo[index].optionTotalPrice[optionIndex] = this.myCartInfo[index].optionTotalPrice[optionIndex] * this.productInfo[index].optionCount[optionIndex];
      this.productInfo[index].optionTotalPrice[optionIndex] += this.myCartInfo[index].optionTotalPrice[optionIndex];
      console.log("1: "+this.productInfo[index].optionTotalPrice[optionIndex] +" 2: "+ this.myCartInfo[index].optionTotalPrice)

      // 전체 가격 변경
      this.productInfo[index].totalPrice += this.myCartInfo[index].optionTotalPrice[optionIndex];
      this.totalPrice += this.myCartInfo[index].optionTotalPrice[optionIndex];
      this.updateTotalPriceAndDelivery.call(this)
      this.$forceUpdate();
    },
    saveOrderMsg(index){
      // redis 작업
      console.log(index)
      if (this.orderMsg[index].length === 0) {
        this.showWarning = true;
      } else {
        useCookies().cookies.set('orderMsg'+index, this.orderMsg)
        this.showWarning = false;
      }
    },
    settingOption(index){
      console.log(index)
    },
    removeOption(index){
      console.log(index)
    },
    clickAll(){
      this.selectedProduct = this.productInfo.map(() => this.selectAllProduct);
    },
    order() {
      // ...어떤 정보가 넘어가야하지?
      //productId, option, optionCount
      // 이미 redis에 값이 있으니깐
      // email, productId만 전달..? 삭제일 경우는...? // redis와 값이 다를경우는...?

      console.log(this.selectedProduct)
      for (let i = 0; i < this.selectedProduct.length; i++) {
        if(this.selectedProduct[i] === true) {
          console.log(this.myCartInfo[i])
          const data = {
            productId: this.myCartInfo[i].productId,
            option: this.myCartInfo[i].option.flat().join(','),
            optionCount: this.myCartInfo[i].optionCount.join(',')
          }

          this.orderData.push(data);
        }
      }

      console.log(JSON.stringify(this.orderData))
      useCookies().cookies.set('orderData', JSON.stringify(this.orderData))
      this.$router.push({name: 'PurchaseMultiDeliveryPage'})
    },
    gHome(){

    },
    checkTextLength(index) {
      this.showSave[index] = this.orderMsg.length > 0;
      this.showWarning[index] = true;
    },
    test(index){
      console.log("index: "+ index)
      console.log(this.selectedProduct)
    },
    updateTotalPriceAndDelivery(){
      this.totalPrice = this.productInfo.reduce((acc, product) => {
        return acc + product.totalPrice;
      }, 0);

      let newTotalDeliveryCharge = 0;
      for (let i = 0; i < this.productInfo.length; i++) {
        const product = this.productInfo[i];
        const deliveryFreeCheck = product.totalPrice >= product.freeDeliverTotalCharge;

        this.deliveryFreeCheck[i] = deliveryFreeCheck;

        if(product.totalPrice < product.freeDeliverTotalCharge){
          console.log(product.deliveryCharge)
          newTotalDeliveryCharge += product.deliveryCharge;
        }

        console.log("newTotalDeliveryCharge: "+newTotalDeliveryCharge)
      }
      this.totalDelivery = newTotalDeliveryCharge;
    }
  },
  computed: {
    selectedCount(){
      return this.selectedProduct.filter(item => item).length;
    }
  },
  mounted() {

  }
})
</script>

<style scoped>
.basic{
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
.fixed-bottom {
  padding: 0 5%;
  position: fixed;
  bottom: 0;
  max-width: 1000px;
  min-height: 270px;
  max-height: 270px;
  width: 100%;

}
.table-border {
  height: 70px;
  font-size: 24px;
  font-family: GmarketSansBold,sans-serif;
}

.table-padding {
  padding: 0 20px;
}
.boldText{
  font-size: 20px;
  font-family: GmarketSansBold, sans-serif;
  margin-right: 4px;
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

.custom-btn {
  width: 2px !important;
}
</style>