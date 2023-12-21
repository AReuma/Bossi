<template>
  <div style="height: 100%; max-width: 50%; padding: 10px;">
    <v-card height="auto" width="100%" style="padding: 10px; border: 1px solid rgba(234,234,234,0.2)" elevation="2">
      <div style="display: flex; align-items: center;">
        <img style="border-radius: 80%; border: 1px solid rgba(187,187,187,0.43); margin-right: 10px" src="../../../assets/logo/Bossi_logo_1.png" height="50" width="50"/>
        <div style="font-family: GmarketSansBold, sans-serif">{{productContent.storeName}}</div>
      </div>

      <div style="margin-top: 10px; font-size: 22px;">
        <span>{{productContent.title}} </span>
      </div>

      <div style="margin-top: 5px; display: flex;">
        <div style="width: 80%; display: flex; align-items: end">
          <div class="boldText" style="color: red" v-if="productContent.rating !== 0">{{productContent.rating.toLocaleString()}}%</div>
          <div class="boldText" style="">{{productContent.ratingPrice.toLocaleString()}}원</div>
          <div style="font-size: 15px; padding-bottom: 5px; color: #bbbbbb"  v-if="productContent.rating !== 0"><del>{{productContent.price.toLocaleString()}}원</del></div>
        </div>
        <div style="width: 20%; display: flex; justify-content: end; padding-right: 5px">
          <v-icon size="32">mdi-heart</v-icon>
        </div>
      </div>

      <div style="display: flex; justify-content: end">
        <span style="font-family: GmarketSansBold, sans-serif">{{productContent.salesQuantity.toLocaleString()}}명</span> 구매
      </div>

      <div>
        <table>
          <tr>
            <td class="table-name">적립금</td>
            <td>최대 34p</td>
          </tr>

          <tr>
            <td class="table-name">구매후기</td>
            <td><v-rating readonly color="yellow"></v-rating></td>
          </tr>

          <tr>
            <td class="table-name" style="vertical-align: top">배송비</td>
            <td v-if="productContent.freeDeliverTotalCharge !== -1" style="text-align: start">{{ productContent.deliveryCount.toLocaleString()}}원 <br/>
              <span style="font-size: 13px; color: rgba(106,106,106,0.89)">{{productContent.freeDeliverTotalCharge.toLocaleString()}}원 이상 무료배송</span>
            </td>
            <td v-else style="text-align: start">{{ productContent.deliveryCount.toLocaleString() }}원 <br/>
            </td>
          </tr>
          <tr v-if="productContent.freeDeliverTotalCharge === -1">
            <td class="table-name"></td>
          </tr>
          <tr>
            <td class="table-name">배송 시작</td>
            <td>평균 1일, 최대 7일 이내</td>
          </tr>
          <tr>
            <td class="table-name">수량</td>
            <td v-if="productContent.stockQuantity === -1">주문시 제작</td>
            <td v-else>{{productContent.stockQuantity}}</td>
          </tr>
        </table>
      </div>

      <div v-if="!productContent.productOption.every(isEmptyObject)">
        <div style="margin: 15px 0 20px 0; height: 60px; padding: 5px 0">
          <v-btn @click="optionDialog = true" text width="100%" height="100%" style=" border: 1px solid rgba(187,187,187,0.67);">
            옵션 선택 <v-spacer></v-spacer><v-icon>mdi-chevron-down</v-icon>
          </v-btn>
        </div>

        <div v-if="orderListView.length !== 0" style="min-height: 150px; max-height: 150px; overflow-y: auto;">
          <div v-for="(order, index) in orderListView" :key="index" style="background-color: rgba(229,229,229,0.25); border: 1px solid rgba(149,146,146,0.23); border-radius: 7px; padding: 10px">
            <p style="font-size: 14px">{{order}}</p>
            <div style="display: flex">
              <div style="width: 40%; padding-left: 10px;">
                <v-btn outlined min-width="10px" style="border: 1px solid rgba(106,106,106,0.5)"  @click="orderListMin(index)">-</v-btn>
                <input :style="{outline: 'none'}" type="text" v-model="orderCount[index]" style="width: 40%; text-align: center">
                <v-btn outlined min-width="10px" style="border: 1px solid rgba(106,106,106,0.5)" @click="orderListPlus(index)">+</v-btn>
              </div>

              <div style="text-align: end; width: 60%; font-weight: bolder;">
                {{orderOptionTotalPrice[index].toLocaleString()}} <v-btn icon @click="removeOption(index)"><v-icon>mdi-window-close</v-icon></v-btn>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div v-else style="min-height: 80px; max-height: 80px; overflow-y: auto; margin-top: 20px">
        <div style="background-color: rgba(229,229,229,0.25); border: 1px solid rgba(149,146,146,0.23); border-radius: 7px; padding: 10px">
          <div style="display: flex">
            <div style="width: 40%; padding-left: 10px;">
              <v-btn outlined min-width="10px" style="border: 1px solid rgba(106,106,106,0.5)"  @click="noOptionOrderMin">-</v-btn>
              <input :style="{outline: 'none'}" type="text" v-model="orderNoOptionCount" style="width: 40%; text-align: center">
              <v-btn outlined min-width="10px" style="border: 1px solid rgba(106,106,106,0.5)" @click="noOptionOrderPlus">+</v-btn>
            </div>
            <div v-if="this.orderNoOptionCount === 1" style="text-align: end; width: 60%; font-weight: bolder; display: flex; align-items: center; justify-content: end; height: auto">
              {{productContent.ratingPrice.toLocaleString()}} 원
            </div>
            <div v-else style="text-align: end; width: 60%; font-weight: bolder; display: flex; align-items: center; justify-content: end; height: auto">
              {{this.orderNoOptionPrice.toLocaleString()}} 원
            </div>
          </div>
        </div>
      </div>

      <v-dialog v-model="optionDialog" persistent height="auto" width="500px">
        <v-card>
          <v-card-title style="font-size: 12px; background-color: rgba(127,127,127,0.15)">
            전체 옵션 선택
          </v-card-title>

          <v-card-actions>
            <div style="height: 200px; width: 100%; display: flex; flex-direction: column">
              <div v-for="(item, index) in productContent.productOption" :key="index">
                <v-select :style="{outline: 'none'}" color="DEEP_PINK" v-model="selectedOptions[index]" :items="getOptionDetails(item)" item-text="label"
                          item-value="value" :label="(index+1)+'.  '+item.option" style="padding-bottom: 2px"></v-select>
              </div>
            </div>
          </v-card-actions>

          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
                color="red"
                text
                @click="closeOptionDialog"
            >
              취소
            </v-btn>
            <v-btn
                color="DEEP_PINK"
                text
                @click="checkOption(productContent.productOption.length)"
            >
              선택 완료
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>

      <div v-if="!productContent.productOption.every(isEmptyObject)" style="display:flex; width: 100%; margin-top: 5px; padding-right: 10px">
        <div style="width: 20%">총 작품금액</div>
        <div style="display:flex; font-family: GmarketSansBold,sans-serif; justify-content: end; width: 80%;"> {{this.orderPrice.toLocaleString()}} 원</div>
      </div>

      <div v-else style="display:flex; width: 100%; margin-top: 5px; padding-right: 10px">
        <div style="width: 20%">총 작품금액</div>
        <div style="display:flex; font-family: GmarketSansBold,sans-serif; justify-content: end; width: 80%;" v-if="this.orderNoOptionCount === 1"> {{this.productContent.ratingPrice.toLocaleString()}} 원</div>
        <div style="display:flex; font-family: GmarketSansBold,sans-serif; justify-content: end; width: 80%;" v-else> {{this.orderNoOptionPrice.toLocaleString()}} 원</div>
      </div>

      <div style="margin-top: 18px;">
        <v-btn depressed height="50px" text class="buy-button" style="border: 1px solid rgba(187,187,187,0.62)" @click="addCart">장바구니</v-btn>
        <v-btn depressed color="green" height="50px" class="buy-button" style="color: white">NPay</v-btn>
        <v-btn depressed height="50px" color="DEEP_PINK" class="buy-button" style="color: white" @click="purchase">구매하기</v-btn>
        <v-btn depressed height="50px" text width="8%" style="margin: 2px; border: 1px solid #fc9899; color: #fc9899">
          <div class="vertical-text"><v-icon size="22">mdi-gift-outline</v-icon> <div style="font-size: 11px; margin-top: 2px">선물하기</div></div>
        </v-btn>
      </div>

      <hr style="border: 1px solid rgba(67,79,88,0.11); margin: 12px 0;"/>

      <div style="height: 70px; padding: 12px 0; display: flex; font-size: 13px; align-items: center">
        <v-btn depressed height="45px" width="30%" text style="border: 1px solid rgba(187, 187, 187, 0.6); margin-right: 7px" @click="moveMessage">
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
import {useCookies} from "vue3-cookies";

export default defineComponent({
  name: "ProductDetailInfo",
  props: {
    productContent: {
      type: Object
    }
  },
  data() {
    return {
      optionDialog: false,
      userEmail: useCookies().cookies.get('email'),
      selectedOptions: [],
      orderList: [],
      orderListView: [],
      orderCount: [],
      orderOptionPrice: [],
      orderOptionTotalPrice: [],
      orderPrice: 0,
      orderNoOptionCount: 1,
      orderNoOptionPrice: 0,
    }
  },
  methods: {
    getOptionDetails(item) {
      let combinedOptions = [];

      for (let key in item.price) {
        let index = item.option+ ": " +item.optionDetail[key]+'/(+'+item.price[key]+')';
        let price = item.price[key];
        let optionDetail = item.optionDetail[key];
        combinedOptions.push({
          label: `${optionDetail} (+ ${price} 원)`,
          value: `${key}#${index}`
        });
      }
      return combinedOptions;
    },
    checkOption(index){
      if(index === this.selectedOptions.length){ ///선택된 옵션 개수
        console.log(this.selectedOptions)
        let order = "";
        let orderPrice = 0
        let option = []
        for (let i = 0; i < this.selectedOptions.length; i++) {
          let values = this.selectedOptions[i].split("#"); // 0#색상: 빨강/(+2000)
          option.push(Number(values[0]))  // 0
          order += values[1];   // 색상: 빨강/(+2000)

          if(i !== this.selectedOptions.length-1){order+='/'}

          let extractedNumbers = values[1].match(/\(\+(\d+)\)/g);
          if (extractedNumbers) {
            const numbers = extractedNumbers.map(match => match.match(/\d+/)[0]);

            orderPrice += Number(numbers[0]);
          }
        }

        this.orderListView.push(order)
        this.orderList.push(option)

        /*if(this.orderPrice === 0){
          this.orderPrice = this.productContent.ratingPrice;
        }*/
        console.log(this.productContent.ratingPrice)
        //orderPrice = Number(orderPrice)
        console.log(Number(orderPrice))
        this.orderPrice += (orderPrice + this.productContent.ratingPrice);
        this.orderOptionPrice.push(orderPrice + this.productContent.ratingPrice)
        this.orderOptionTotalPrice.push(orderPrice + this.productContent.ratingPrice)
        this.orderCount.push(1)

        console.log(this.orderPrice)
        console.log(this.orderCount)
        this.optionDialog = false;
        this.selectedOptions =[];
      }else {
        alert('옵션을 선택해주세요')
      }
    },
    closeOptionDialog(){
      this.optionDialog = false;
      this.selectedOptions = [];
    },
    orderListPlus(index){
      this.orderCount[index]++;
      this.orderOptionTotalPrice[index] = this.orderOptionPrice[index] * this.orderCount[index];
      this.orderPrice += this.orderOptionPrice[index];
      this.$forceUpdate();
    },
    orderListMin(index){
      if(this.orderCount[index] > 1) {
        this.orderCount[index]--;
        this.orderOptionTotalPrice[index] = this.orderOptionPrice[index] * this.orderCount[index];
        this.orderPrice -= this.orderOptionPrice[index];
        this.$forceUpdate();
      }else{
        alert('주문수가 1보다 작을 수 없습니다.')
      }
    },
    removeOption(index){
      console.log(this.orderPrice+"!")
      console.log(this.orderOptionTotalPrice[index]+"!")
      this.orderPrice -= this.orderOptionTotalPrice[index];
      this.orderCount.splice(index, 1);
      this.orderListView.splice(index, 1);
      this.orderList.splice(index, 1);
      this.orderOptionTotalPrice.splice(index, 1);
      this.orderOptionPrice.splice(index, 1);
    },
    isEmptyObject(obj) {
      return (
          Object.keys(obj.price).length === 0 &&
          Object.keys(obj.optionDetail).length === 0 &&
          obj.option === ""
      );
    },
    noOptionOrderMin(){
      if(this.orderNoOptionCount > 1){
        this.orderNoOptionCount--;
        this.orderNoOptionPrice = this.productContent.ratingPrice * this.orderNoOptionCount;
      }else {
        alert('주문수가 1보다 작을 수 없습니다.')
      }
    },
    noOptionOrderPlus(){
      this.orderNoOptionCount++;
      this.orderNoOptionPrice = this.productContent.ratingPrice * this.orderNoOptionCount;
    },
    purchase(){
      // 옵션 정보랑 상품Id 전달
      console.log("userEmail" +this.userEmail)
      console.log("options" +this.orderList.length)

      if(this.orderList.length === 0 && this.orderListView.length !== 0){
        alert('옵션을 선택해주세요')
      }else {
        if(this.userEmail !== null){
          let productId = this.productContent.productId;
          const options = this.orderList.join(',');
          const optionCount = this.orderCount.join(',');
          console.log(options)

          const expires = new Date()
          expires.setMinutes(expires.getMinutes() + 60)

          useCookies().cookies.set('productId', productId, expires)
          useCookies().cookies.set('options', options, expires)
          useCookies().cookies.set('optionCount', optionCount, expires)

          this.$router.push({name: "PurchaseDirectPage"})
        }else {
          alert('회원만 가입할 수 있습니다.\n로그인 부탁드려요.')
          this.$router.push({name: "LoginPage"})
        }

      }
      // 가져가야하는 정보
      // 작가 이름
      // 기본 이미지
      // 옵션 개수
      // 옵션 선택 (인덱스로 넘기기)
      // 가격
      // 배송비

    },
    addCart(){
      // String email, Long productId, String options, String optionCount
      console.log("orderList: "+this.orderList.length)
      console.log("orderListView: "+this.orderListView.length)
      if(this.orderList.length === 0){
        alert('옵션을 선택해주세요')
        console.log("optin")
      }else {
        if(this.userEmail !== null){
          let email = this.userEmail;
          let productId = this.productContent.productId;
          const options = this.orderList.join(',');
          const optionCount = this.orderCount.join(',');
          console.log(options)

          /*const expires = new Date()
          expires.setMinutes(expires.getMinutes() + 60)

          useCookies().cookies.set('productId', productId, expires)
          useCookies().cookies.set('options', options, expires)
          useCookies().cookies.set('optionCount', optionCount, expires)*/

          this.$emit('addCart', {email, productId, options, optionCount})
        }else {
          alert('회원만 가능합니다.\n로그인 부탁드려요.')
          this.$router.push({name: "LoginPage"})
        }

      }
    },
    moveMessage(){
      this.$router.push({name: "ChattingPage"})
    }
  },
  computed: {

  }
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
  text-align: center;
  margin-top: 8px;
}

</style>