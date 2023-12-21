<template>
  <div class="basic" v-if="!cartIsEmpty">
    <div style="width: 100%; display: flex;">
      <div style="width: 50%; font-size: 28px; font-family: GmarketSansBold, sans-serif">장바구니</div>
      <div style="display: flex; width: 50%; justify-content: end; align-items: center; font-size: 20px">
        <div style="font-family: GmarketSansBold, sans-serif">1. 장바구니 &nbsp;&nbsp;>&nbsp;&nbsp; </div>
        <div style="color: #848484">2. 주문 결제 &nbsp;&nbsp;>&nbsp;&nbsp; </div>
        <div style="color: #848484">3. 주문완료</div>
      </div>
    </div>

    <div style="border: 1px solid #626262; width: 100%; height: 100%; margin-top: 40px; border-radius: 4px">
      <div style="padding-left: 2%; display: flex; align-items: center; background-color: rgba(187,187,187,0.15); height: 60px">
        <v-checkbox readonly v-model="checkProduct" color="DEEP_PINK"></v-checkbox><div style="margin-left: 5px; font-size: 18px">{{directOrderList.storeName}}</div>
      </div>

      <div style="display: flex; padding: 0 2%; height: 100%; flex-direction: column">
        <div style="width: 100%; height: 25%; align-items: center; display: flex; margin-top: 15px">
          <v-checkbox readonly v-model="checkProduct" color="DEEP_PINK"></v-checkbox>
          <div>
            <v-img :src="`https://s3.ap-northeast-2.amazonaws.com/my.example.s3.bucket.bossi/${directOrderList.productImg}`" width="100" height="100" style="border-radius: 4px"/>
          </div>
          <div style="width: 100%; padding-left: 2%">
            <div style="font-weight: bolder; font-size: 16px">{{directOrderList.productName}}</div>
            <div style="color: #848484; font-size: 14px" v-if="directOrderList.stockQuantity === -1">수량 제한 없음</div>
            <div style="color: #848484; font-size: 14px" v-else>{{directOrderList.stockQuantity}}</div>
          </div>
        </div>

        <div style="margin-top: 30px; padding: 0 10px 0 155px; height: auto; margin-bottom: 20px">
          <div v-for="(option, index) in optionStr" :key="index" style="border-bottom: #848484 1px solid; height: 50px; padding: 0 10px; display: flex; align-items: center; font-size: 15px">
            <div style="max-width: 50%; min-width: 50%">
              {{option}}
            </div>
            <div style=" max-width: 25%; min-width: 25%">
              <v-btn outlined min-width="10px" style="border: 1px solid rgba(106,106,106,0.5)" @click="orderListMin(index)">-</v-btn>
              <input readonly :style="{outline: 'none'}" v-model="optionCount[index]" type="text" style="width: 40%; text-align: center">
              <v-btn outlined min-width="10px" style="border: 1px solid rgba(106,106,106,0.5)" @click="orderListPlus(index)">+</v-btn>
            </div>
            <div style="display: flex; align-items: center; max-width: 25%; min-width: 25%">
              {{ optionPrice[index] * optionCount[index]}}
              <div style="margin-left: 15px;">
                <v-btn icon @click="settingOption(index)"><v-icon>mdi-cog</v-icon></v-btn>
                <v-btn icon @click="removeOption(index)"><v-icon>mdi-window-close</v-icon></v-btn>
              </div>
            </div>
          </div>
        </div>

        <div>
          <div style="display: flex; height: 170px">
            <textarea v-model="orderMsg" placeholder="주문 요청사항을 입력해주세요" @input="checkTextLength"></textarea>

            <v-btn depressed v-if="showSave" style="width: 150px; height: 150px; outline: rgba(132,132,132,0.27) 1px solid" @click="saveOrderMsg">
              저장
            </v-btn>
          </div>

          <p v-if="showWarning">
            <v-icon>mdi-alert-circle</v-icon>  입력 후 저장버튼을 눌러주세요
          </p>
        </div>

      </div>

      <div style="margin-bottom: 5px">
        <table style="width: 100%; border-collapse: collapse; ">
          <tr style="height: 60px; border-bottom: 1px solid black; border-top: 1px solid black;">
            <td class="table-padding">작품 가격</td>
            <td class="table-padding" style="font-weight: bold; text-align: end">{{ numberWithCommas(optionTotalPrice) }}원</td>
          </tr>

          <tr>
            <td v-if="directOrderList.freeDeliverTotalCharge !== -1" class="table-padding" rowspan="2" style="height: 80px;">배송비</td>
            <td v-else class="table-padding" style="height: 60px;">배송비</td>
            <td class="table-padding" style="font-weight: bold; text-align: end; ">{{ numberWithCommas(directOrderList.deliveryCharge) }}원</td>
          </tr>

          <tr v-if="directOrderList.freeDeliverTotalCharge !== -1">
            <td class="table-padding" style="font-size: 12px; text-align: end"> {{ numberWithCommas(directOrderList.freeDeliverTotalCharge)}}원 이상 무료배송</td>
          </tr>
        </table>
      </div>
    </div>

    <div style="min-height: 250px; max-height: 250px; width: 100%;"></div>

    <div class="fixed-bottom">
      <div style="background-color: rgba(255,255,255,0.88); ">
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
              <td class="table-border">{{ numberWithCommas(optionTotalPrice) }} 원</td>
              <td class="table-border">+</td>
              <td class="table-border">{{ numberWithCommas(directOrderList.deliveryCharge) }} 원</td>
              <td class="table-border">=</td>
              <td class="table-border">{{ numberWithCommas((optionTotalPrice + directOrderList.deliveryCharge)) }} 원</td>
            </tr>
          </table>
        </div>
        <div style="padding: 15px;">
          <v-btn @click="order" style="width: 100%; height: 60px; font-size: 18px; color: white" color="DEEP_PINK" depressed>주문하기</v-btn>
        </div>

      </div>
    </div>

    <v-dialog v-model="optionDialog" persistent height="auto" width="500px">
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
    </v-dialog>

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
import index, {mapActions} from "vuex";
import {useCookies} from "vue3-cookies";
import {numberWithCommas} from "../../../constant/util";

export default defineComponent({
  name: "PurchaseDirectView",
  computed: {
    index() {
      return index
    }
  },
  props: ['directOrderList', 'cartIsEmpty'],
  data() {
    return {
      productId: useCookies().cookies.get('productId'),
      options: useCookies().cookies.get('options'),
      optionsCount: useCookies().cookies.get('optionCount'),
      optionCount: null,
      optionDialog: false,
      checkIndex: 0,
      selectedOptions: [],
      optionStr: [],
      option: [],
      optionPrice: null,
      checkProduct: true,
      optionTotalPrice: 0,
      orderMsg: '',
      showSave: false,
      showWarning: false
    }
  },
  methods: {
    numberWithCommas,
    checkTextLength() {
      this.showSave = this.orderMsg.length > 0;
      this.showWarning = true;
    },
    settingOption(index){
      console.log(index)
      this.checkIndex = index;
      this.optionDialog = true;
    },
    removeOption(index){
      if(this.optionStr.length -1 < 1){
        this.optionCount = [];
        this.optionPrice = [];
        this.optionStr = [];
        this.option = [];

        useCookies().cookies.remove('options');
        useCookies().cookies.remove('productId');

      }else {
        this.optionCount.splice(index, 1);
        this.optionPrice.splice(index, 1);
        this.optionStr.splice(index, 1);
        this.option.splice(index, 1);

        const options = this.option.join(',');
        const optionsCount = this.optionCount.join(',');
        console.log(options) // 0, 0, 1, 0

        useCookies().cookies.set('options', options);
        useCookies().cookies.set('optionCount', optionsCount);
      }
      this.$router.go(0);

    },
    getOptionDetails(item) {
      let combinedOptions = [];

      for (let key in item.price) {
        //let index = item.option+ ": " +item.optionDetail[key];
        let price = item.price[key];
        let optionDetail = item.optionDetail[key];
        combinedOptions.push({
          label: `${optionDetail} (+ ${price} 원)`,
          value: `${key}`
        });
      }
      return combinedOptions;
    },
    closeOptionDialog(){
      this.optionDialog = false;
    },
    modifyOptionDialog(){
      console.log(this.selectedOptions) // [0, 1]
      console.log(this.directOrderList.optionStr)
      /*let str = '';
      for (let selectedOption of this.selectedOptions) {
        str += selectedOption.match(/\D+/g);
      }*/
      //const extractedNumbers = this.selectedOptions[0].match(/\D+/g);
      //Vue.set(this.optionStr[index], str);

      console.log(this.option)
      console.log("=========")
      console.log("index: "+ this.checkIndex)

      for (let i = 0; i < this.selectedOptions.length; i++) {
        //console.log(this.selectedOptions[i])
        //console.log(this.option[this.checkIndex][i])
        this.option[this.checkIndex][i] = this.selectedOptions[i];
      }
      //index = 0
      //this.option[0][index] = this.selectedOptions;
      console.log(this.option)  // [[0,0], [1, 0]]
      const options = this.option.join(',');
      console.log(options) // 0, 0, 1, 0

      useCookies().cookies.set('options', options)
      this.optionDialog = false;
      this.$router.go(0);
    },
    orderListMin(index){
      if(this.optionCount[index] > 1) {
        this.optionCount[index]--;

        const optionsCount = this.optionCount.join(',');

        useCookies().cookies.set('optionCount', optionsCount);

        //this.optionPrice[index] = this.directOrderList.optionPrice[index] * this.optionCount[index];
        this.optionTotalPrice -= this.directOrderList.optionPrice[index];
        this.$forceUpdate();
      }else{
        alert('주문수가 1보다 작을 수 없습니다.')
      }
    },
    orderListPlus(index){
      console.log(this.optionPrice[index])
      console.log(useCookies().cookies.get('optionCount'));
      this.optionCount[index]++;
      const optionsCount = this.optionCount.join(',');

      useCookies().cookies.set('optionCount', optionsCount);

      //this.optionPrice[index] = this.directOrderList.optionPrice[index] * this.optionCount[index];
      this.optionTotalPrice += this.directOrderList.optionPrice[index];
      this.$forceUpdate();
    },
    gHome(){
      this.$router.push({name: 'HomePage'})
    },
    saveOrderMsg(){
      // redis 작업

      if (this.orderMsg.length === 0) {
        this.showWarning = true;
      } else {
        useCookies().cookies.set('orderMsg', this.orderMsg)
        this.showWarning = false;
        this.saveOrderBtn = false;
      }
    },
    ...mapActions(['fetchTest']),
    order(){
      // 주문
      // 옵션, 옵션별 개수, 주문 요청 사항
      /*const {orderMsg} = this;

      this.$emit('order', {orderMsg});*/

      this.$router.push({name: 'PurchaseDeliveryPage'})
    }
  },
  watch: {
    directOrderList: {
      handler(newVal) {
        if (newVal && newVal.optionCount) {
          this.optionCount = [...newVal.optionCount];
          this.optionPrice = [...newVal.optionPrice];
          this.optionStr = [...newVal.optionStr];
          this.option = [...newVal.option];

          for (let i = 0; i < newVal.optionPrice.length; i++) {
            this.optionTotalPrice += (this.optionPrice[i] * this.optionCount[i])
          }
        }
      },
      deep: true,
    },
    orderMsg(){

    }
  },
  mounted() {
    console.log(this.optionCount)
    //this.optionCount = [...this.directOrderList.optionCount];
    //this.optionPrice = [...this.directOrderList.optionPrice];
  },
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
  min-height: 250px;
  max-height: 250px;
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
</style>