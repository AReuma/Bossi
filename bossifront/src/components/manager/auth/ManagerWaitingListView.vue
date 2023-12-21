<template>
  <div style="width: 100%;">
    <div style="margin-top: 25px; width: 100%; padding: 10px">
      <div style="margin: 10px">
        <div style="font-size: 28px; ">입점 관리</div>
        <p style="font-size: 15px">메일로 제출 된 자료를 확인 후 입점 관리를 진행해주세요. </p>
      </div>


      <div style="margin-top: 60px">
        <div v-if="!waitingListUsers || (Array.isArray(waitingListUsers) && waitingListUsers.length === 0)">
          입점 요청 없음
          {{waitingListUsers}}
        </div>
        <table v-else style="background-color: white;">
          <thead>
            <tr>
              <td>계정</td>
              <td>메일 주소</td>
              <td>여부</td>
            </tr>
          </thead>

          <tbody>
            <tr v-for="(item, index) in waitingListUsers" :key="index">
              <td>{{ item.email }}</td>
              <td>{{ item.sendEmail }}</td>
              <td>
                <div style="width: 300px; display: flex; float: left; height: 60px">
                  <v-select
                      style="margin-top: 10px; margin-left: 10px; margin-right: 10px; font-size: 15px"
                      :items="dropdown_font"
                      label="통과/ 보류"
                      v-model="selectStatus[index]"
                      :loading="false"
                  ></v-select>
                  <v-progress-circular
                      indeterminate
                      color="amber"
                      size="25"
                      v-show="item.check"
                      style="margin-top: 20px"
                  ></v-progress-circular>
                  <v-btn style="margin: 20px 0 0 20px" @click="saveBtn(index)">
                    저장
                  </v-btn>
                </div>

              </td>
            </tr>
          </tbody>
        </table>
      </div>

      <v-dialog v-model="passDialog" persistent width="auto">
        <v-card style="padding: 40px 20px 10px 20px">
          <v-card-title>
            [ " {{ email }} " ]
          </v-card-title>
          <v-card-title style="justify-content: center">
            <i style="color: blue; font-size: 45px">합격</i>
          </v-card-title>

          <v-card-text style="font-size: 18px; margin-top: 12px; text-align: center">
            입점 심사를 통과한 계정이 맞나요?<br>
            확인을 누르면 합격 통보가 날아가요.
          </v-card-text>

          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn depressed style="color: black; width: 37%; height: 40px" @click="backPassDialog()">취소</v-btn>
            <v-btn color="DEEP_PINK" depressed style="color: white; width: 37%; height: 40px" @click="passDialogClose()">확인</v-btn>
            <v-spacer></v-spacer>
          </v-card-actions>
        </v-card>
      </v-dialog>

      <v-dialog v-model="rejectDialog" persistent width="auto">
        <v-card style="padding: 40px 20px 10px 20px">
          <v-card-title>
            [ " {{ email }} " ]
          </v-card-title>
          <v-card-title style="justify-content: center">
            <i style="color: red; font-size: 45px">보류</i>
          </v-card-title>

          <v-card-text style="font-size: 18px; margin-top: 12px; text-align: center">
            입점 심사에 탈락한 계정이 맞나요?<br>
            확인을 누르면 보류 통보가 날아가요.
          </v-card-text>

          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn depressed style="color: black; width: 37%; height: 40px" @click="backRejectDialog()">취소</v-btn>

            <v-btn color="DEEP_PINK" depressed style="color: white; width: 37%; height: 40px" @click="rejectDialogClose()">확인</v-btn>
            <v-spacer></v-spacer>
          </v-card-actions>
        </v-card>
      </v-dialog>

    </div>
  </div>
</template>

<script>
import {defineComponent} from 'vue'
import { mapState} from "vuex";
import axios from "axios";
import {API_BASE_URL} from "@/constant/basic";
import {useCookies} from "vue3-cookies";

export default defineComponent({
  name: "ManagerWaitingListView",
  data(){
    return {
      switch1: false,
      data: [
        {email: 'dkfma@naver.com', sendMail: 'dkfma@naver.com', check: false},
        {email: 'dkfma@naver.com', sendMail: 'dkfma@naver.com', check: false},
        {email: 'dkfma@naver.com', sendMail: 'dkfma@naver.com', check: false},
        {email: 'dkfma@naver.com', sendMail: 'dkfma@naver.com', check: false},
        {email: 'dkfma@naver.com', sendMail: 'dkfma@naver.com', check: false},
        {email: 'dkfma@naver.com', sendMail: 'dkfma@naver.com', check: false},
        {email: '', sendMail: '', check: false},
      ],
      dropdown_font: ['통과', '보류'],
      passDialog: false,
      rejectDialog: false,
      email: '',
      index: 0,
      selectStatus: []
    }
  },
  methods: {
    async fetchData(){
      try{
        await this.$store.dispatch('fetchWaitingListUsers')
      }catch (error) {
        await this.$router.push({name: 'ManagerAccessDeniedPage'})
        console.error(error);
      }

    },
    saveBtn(index) {
      console.log(this.selectStatus[index] == null)
      if(this.selectStatus[index] != null){
        this.index = index;
        this.email = this.waitingListUsers[index].email;

        if(this.selectStatus[index] === "통과"){
          this.passDialog = true
          this.data[index].check = true;

          console.log("통과")
        }else if(this.selectStatus[index] === "보류") {
          this.rejectDialog = true;
          this.data[index].check = true;

          console.log("보류")
        }
      } else {
        console.log("선택안함.")
      }
    },
    passDialogClose(){
      this.passDialog = false;
      this.data[this.index].check = false;

      let id = this.waitingListUsers[this.index].id;
      let enteringStatus = true;

      this.sendEnteringStatus(id, enteringStatus);
    },
    sendEnteringStatus(id, enteringStatus){
      axios.post(API_BASE_URL+'/api/v1/manager/store/status', {id, enteringStatus}, { headers: {
          'Authorization': 'Bearer '+ useCookies().cookies.get('access_token'),
          'Accept' : 'application/json',
          'Content-Type': 'application/json'
        }})
          .then((res) => {
            console.log(res)
            this.$router.go(0)
          })
          .catch((res) => {
            console.log(res)
          })
    },
    rejectDialogClose(){
      this.rejectDialog = false;
      this.data[this.index].check = false;


      let id = this.waitingListUsers[this.index].id;
      let enteringStatus = false;

      this.sendEnteringStatus(id, enteringStatus);
    },
    backRejectDialog(){
      this.data[this.index].check = false;
      this.selectStatus[this.index] = null;

      this.rejectDialog = false;
    },
    backPassDialog(){
      this.data[this.index].check = false;
      this.selectStatus[this.index] = null;

      this.passDialog = false;
    }
  },
  computed: {
    ...mapState(["waitingListUsers"])
  },
  mounted() {
    this.fetchData();
  }
})
</script>

<style scoped>
table {
  width: 100%;
  border: 1px solid #444444;
  border-collapse: collapse;
}
th {
  border: 1px solid #444444;
  width: 30px;
}

td{
  border: 1px solid #444444;
  width: 30px;
}
</style>