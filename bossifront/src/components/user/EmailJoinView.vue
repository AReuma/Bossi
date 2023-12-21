<template>
  <div id="basic">

    <div style="width: 100%; display: flex; justify-content:center; margin-top: 20px">
      <img src="../../assets/logo/Bossi_logo_3.png"  style="height: 100px;" alt="logo"/>
    </div> <!--로고-->

    <div style="display: flex; justify-content: center; margin-top: 30px">
      <v-row style="width: 100%;" justify="center">
        <v-col style="padding-top: 20px">
          <hr/>
        </v-col>
        <v-col align="center" style="color: rgba(33,33,36,0.6)">
          정말 간단한 회원가입하기
        </v-col>
        <v-col style="padding-top: 20px">
          <hr/>
        </v-col>
      </v-row>
    </div> <!-- 간단한 회원가입-->

    <div style="padding: 30px 0; margin: 25px">
      <div style="display: flex; justify-content: center;">
        <v-btn fab depressed outlined disabled style="color: white; font-size: 20px;">1</v-btn>
        <hr style="width: 120px; margin-top: 26px"/>
        <v-btn fab depressed color="DEEP_PINK" style="color: black; font-size: 20px">2</v-btn>
      </div>

      <div style="display: flex; justify-content: center; margin-top: 15px">
        <div style="font-size: 22px; color: #4d5159">가입 정보 입력하기</div>
      </div>
    </div><!-- 1, 2 동그라미 -->

    <div> <!--회원가입 폼 -->
      <div style="height: 100px">
        <p>이메일 <span style="color: red; font-weight: lighter">*</span></p>
        <div style="display: flex">
        <v-text-field
              style="height: 60px"
              label="이메일을 입력해주세요."
              outlined
              color="#434f58"
              v-model="email"
          ></v-text-field>
          <v-btn color="DEEP_PINK"
                 @click="idDubCheck()"
                 :disabled="idCheckSuccess && idCheck"
                 id="phone_check">중복 확인</v-btn>
        </div>
        <p v-if="emailCheck" style="color: red;">이메일을 제대로 작성해주세요</p>
      </div>

      <div style="height: 200px; margin-top: 25px;">
        <p>비밀번호 <span style="color: red; font-weight: lighter">*</span></p>
        <v-text-field
            :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
            :type="show ? 'text' : 'password'"
            @click:append="show = !show"
            label="비밀번호 (영문+숫자+특수문자 8자 이상)"
            outlined
            style="height: 70px;"
            color="#434f58"
            v-model="password"
        ></v-text-field>
        <p v-if="passwordCheck" style="color: red">영문, 특수문자, 숫자를 포함해서 8자 이상 작성해주세요.</p>
        <v-text-field
            :append-icon="showRe ? 'mdi-eye' : 'mdi-eye-off'"
            :type="showRe ? 'text' : 'password'"
            @click:append="showRe = !showRe"
            label="비밀번호 확인"
            outlined
            style="height: 70px;"
            color="#434f58"
            v-model="password2"
        ></v-text-field>
        <p v-if="passwordCheck2" style="color: red">비밀번호가 서로 다릅니다.</p>
      </div>

      <div style="height: 100px; margin-top: 15px;">
        <p>닉네임 <span style="color: red; font-weight: lighter">*</span></p>
        <v-text-field
            label="닉네임을 입력해주세요."
            outlined
            color="#434f58"
            style="height:60px;"
            v-model="nickName"
        ></v-text-field>
        <p v-if="nickNameCheck" style="color: red">필수항목입니다. 닉네임을 작성해주세요.</p>
      </div>

      <div style="height: 100px; margin-top: 25px;">
        <p>전화번호 <span style="color: red; font-weight: lighter">*</span></p>
        <div style="display: flex;">
          <v-text-field
              label="-를 제외한 번호만 입력해주세요."
              outlined
              color="#434f58"
              style="max-width: 80%"
              v-model="phoneNum"
              :disabled="sendPhoneNum"
          ></v-text-field>

          <v-btn color="DEEP_PINK"
                 depressed
                 :disabled = sendPhoneBtn
                 @click="clickPhoneCheck()"
                 id="phone_check">인증요청</v-btn>
        </div>
      </div>
      <p v-if="!phoneCheck" style="color: red">필수항목입니다. 전화번호를 작성해주세요.</p>

      <div v-if="this.checkCode !== ''" style="height: 65px; margin-top: 10px;">
<!--      <div style="height: 65px; margin-top: 10px;">-->
        <div style="display: flex;">
          <v-text-field
              label="인증코드를 입력해주세요"
              outlined
              color="#434f58"
              style="width: 100%"
              v-model="phoneDubCode"
              :disabled="successCheck"
          ></v-text-field>

            <div style="padding-top: 15px; padding-left: 10px; color: rgba(37,37,37,0.72)">
              {{prettyTime()}}
            </div>

          <v-btn color="DEEP_PINK"
                 @click="phoneDubCheck()"
                 :disabled="successCheck"
                 id="phone_check">확인</v-btn>
        </div>
      </div>
      <p v-if="expireCode" style="color: red">인증요청 시간이 지났습니다. 재요청 해주세요.</p>

      <v-dialog v-model="phoneCheckDialog" persistent width="auto">
        <v-card style="padding: 30px 20px 10px 20px">
          <v-card-text style="font-size: 15px">
            {{ phoneCheckData }}
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="DEEP_PINK" style="color: white" @click="phoneCheckDialog = false">확인</v-btn>
            <v-spacer></v-spacer>
          </v-card-actions>
        </v-card>
      </v-dialog>

      <v-dialog v-model="existsPhoneDialog" persistent width="auto">
        <v-card style="padding: 30px 20px 10px 20px">
          <v-card-text>
            {{ phoneCheckData }}
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn @click="gLogin()" color="BACK_GROUND_COLOR" style="font-size: small">로그인</v-btn>
            <v-btn color="DEEP_PINK" style="color: white" @click="existsPhoneDialog = false">확인</v-btn>
            <v-spacer></v-spacer>
          </v-card-actions>
        </v-card>
      </v-dialog>

      <div style="height: 100px; margin-top: 25px;">
        <p>추천인코드</p>
        <v-text-field
            label="선택사항"
            outlined
            color="#434f58"
            v-model="recommender"
        ></v-text-field>
      </div>

      <div style="margin-top: 35px; color: black">
        <v-checkbox v-model="selectAll" label="모두 동의합니다" color="DEEP_PINK" hide-details @click="clickAll()">
        </v-checkbox>
        <hr style="margin-top: 15px; background: rgba(33,33,36,0.15); height: 1px; border: 0"/>
        <v-checkbox v-model="select" value="c1" label="만 14세 이상입니다." color="DEEP_PINK" hide-details>
        </v-checkbox>
        <v-checkbox v-model="select" value="c2" label="이용약관 동의" color="DEEP_PINK" hide-details>
        </v-checkbox>
        <v-checkbox v-model="select" value="c3" label="개인정보 수집 및 이용 동의" color="DEEP_PINK" hide-details>
        </v-checkbox>
        <v-checkbox v-model="select" value="c4" label="할인쿠폰/이벤트/감동적인 뉴스레터 선택동의 (선택)" color="DEEP_PINK" hide-details>
        </v-checkbox>

        <hr style="margin-top: 20px; margin-bottom: 45px; background: rgba(33,33,36,0.15); height: 1px; border: 0"/>
      </div>

      <div>
        <v-btn @click="register()" class="my_btn" style="height: 54px; margin-bottom: 70px; color: #fc9899; font-size: 15px" outlined depressed>
          이메일로 가입하기
        </v-btn>
      </div>
    </div>
  </div>
</template>

<script>
import {defineComponent} from 'vue'
import axios from "axios";
import {API_BASE_URL} from "@/constant/basic";
import {EXPIRE_TIME} from "@/constant/login";

export default defineComponent({
  name: "EmailJoinView",
  props: ['checkIdDub'],
  data(){
    return{
      show: false,
      showRe: false,
      selectAll: false,
      phoneCheck: true,
      emailCheck: false,
      idCheckSuccess: true,
      idCheck: true,
      passwordCheck: false,
      passwordCheck2: false,
      nickNameCheck: false,
      selectCheck: false,
      sendPhoneNum: false,
      sendPhoneBtn: true,
      email: '',
      password: '',
      password2: '',
      nickName: '',
      phoneNum: '',
      phoneDubCode: '',
      recommender: '',
      select: [],
      phoneCheckDialog: false,
      existsPhoneDialog: false,
      phoneCheckData: '',
      successCheck: false,
      // 타이머
      timeCounter : EXPIRE_TIME,
      expireCode: false,
      checkCode: '',
      socialType: ''

    }
  },
  methods: {
    clickAll(){
      if(this.selectAll){
        this.select = ['c1', 'c2', 'c3', 'c4'];
      }else {
        this.select = [];
      }
    },
    clickPhoneCheck(){
      this.sendPhoneBtn = true;
      this.sendPhoneNum = true;
      if(this.expireCode === true) {
        this.expireCode = false;

      }
      const {phoneNum} = this
      //this.$emit('checkNum', {phoneNum})
      axios.get(API_BASE_URL+`/api/v1/users/checkPhone/${phoneNum}`)
          .then((res) => {
            //alert(res)
            console.log(res.data.num)
            console.log(res.data.socialType)
            console.log(res.data)
            this.checkCode = res.data.num;
            this.socialType = res.data.socialType;
            this.start()
          })
          .catch((res) => {
            console.log(res)
          })
    },
    start(){
      // 1초에 한번씩 start 호출
      this.polling = setInterval( () =>{
        this.timeCounter-- //1찍 감소
        if (this.timeCounter <= 0) {
          this.timeStop();
          this.timeCounterReset();
          this.expireCode = true;
          // 인증 코드 다시 보내야함.
        }
      },1000) // 1000ms, 1초
    },
    prettyTime() {
      let time = this.timeCounter / 60
      let minutes = parseInt(time)
      let secondes = Math.round((time - minutes) * 60)
      return this.pad(minutes, 2) + ":" + this.pad(secondes, 2)
    },
    // 2자리수로 만들어줌 09,08...
    pad(n, width) {
      n = n + '';
      return n.length >= width ? n : new Array(width - n.length + 1).join('0') + n
    },
    timeStop() {
      clearInterval(this.polling)
    },
    timeCounterReset(){
      this.sendPhoneBtn = false;
      this.sendPhoneNum = false;
      this.checkCode = '';
      this.phoneNum = '';
      this.phoneDubCode = '';
      this.timeCounter = EXPIRE_TIME;
    },
    register(){
      if(this.checkJoin()){
        alert('회원가입 성공')
       const {email, password, nickName, phoneNum, recommender} = this;
        let checkSMS = this.select.includes('c4');
        this.$emit('register', {email, password, nickName, phoneNum, recommender, checkSMS})
      }else {
        alert('회원가입 실패')
      }
    },
    phoneDubCheck(){
      this.timeStop();
      if(this.socialType === "NEW_MEM"){
        this.timeCounter = 0;
        if(this.phoneDubCode === this.checkCode){
          this.phoneCheckData = "인증 완료 되었습니다."
          this.phoneCheckDialog = true;

          this.successCheck = true;
          this.phoneCheck = true;
          this.existsPhoneDialog = false;
        }else {
          this.phoneCheckData = "인증번호가 일치하지 않습니다.\n확인 후 다시 입력해주세요."
          this.phoneDubCode = "";
          this.phoneCheckDialog = true
        }
      }else {
        this.timeCounterReset();
        if(this.socialType === "GENERAL") {
          this.phoneCheckData = '회원가입이 된 전화번호 입니다.';
        }else {
          this.phoneCheckData = "이미 " + this.socialType + "으로 가입된 회원의 전화번호 입니다.\n"
        }
        this.existsPhoneDialog = true;
      }
    },
    gLogin(){
      this.$router.push({name: 'LoginPage'})
    },
    idDubCheck(){
      const {email} = this;

      axios.get(API_BASE_URL + `/api/v1/users/checkId/${email}`)
          .then((res) => {
            this.idCheck = res.data;
            //alert(this.idCheck)
            if(this.idCheck){
              alert('사용가능한 아이디 입니다.')
            }else {
              alert('사용중인 아이디 입니다. ')
            }
            console.log(res)
          })
          .catch((res) => {
            console.log(res)
          })
    },
    checkSelect(select){
      select.sort();

      const arr1 = ['c1', 'c2', 'c3'];
      const arr2 = ['c1', 'c2', 'c3', 'c4'];

      let selectChecks;

      if(this.select.length === 3) {
        selectChecks = this.selectEquals(this.select, arr1)
      }else if(this.select.length === 4){
        selectChecks = this.selectEquals(this.select, arr2)
      }else return false;

      return selectChecks;
    },
    checkJoin(){
      this.textLengthCheck(this.email.length, this.password.length, this.password2.length, this.nickName.length, this.phoneNum.length)

      console.log('idCheck: '+ this.idCheck);
      console.log('passwordCheck: '+ this.passwordCheck);
      console.log('passwordCheck2: '+ this.passwordCheck2);
      console.log('nickNameCheck: '+ this.nickNameCheck);
      console.log('phoneCheck: '+ this.phoneCheck);
      console.log('idCheckSuccess: '+ this.select);

      //this.select.sort();
      let selectChecks = this.checkSelect(this.select);

      return !this.idCheckSuccess && !this.passwordCheck && !this.passwordCheck2 && !this.nickNameCheck && this.successCheck && selectChecks;
    },
    textLengthCheck(email, pw, pw2, nick, phone){
      if(email === 0) this.emailCheck = true;

      if(pw === 0) this.passwordCheck = true;

      if(pw2 === 0) this.passwordCheck2 = true;

      if(phone === 0) this.phoneCheck = false;

      if(nick === 0) this.nickNameCheck = true;
    },
    selectEquals(a, b){
      return a.length === b.length && a.every((v, i) => v === b[i]);
    }
  },
  watch: {
    email: function (val) { // dkfma!123@gmail.com 회원가입이 안됨.
      const email = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/

      this.emailCheck = !val.match(email);

      this.emailCheck = !val.match(email);
      this.idCheckSuccess = !val.match(email);
      this.idCheck = false;
    },
    password: function (val){
      const password =/(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\W)(?=\S+$).{8,16}/

      this.passwordCheck = !val.match(password);
    },
    password2: function (val){
      this.passwordCheck2 = val !== this.password;
    },
    nickName: function (val){
      this.nickNameCheck = val.length <= 0;
    },
    phoneNum: function (val){
      const phone = /\d{3}\d{4}\d{4}/g;
      //console.log(val)
      if(val.length === 0) this.phoneCheck = true;
      else if(!val.match(phone)) this.phoneCheck = false;
      else if(val.match(phone)) this.phoneCheck = true;

      this.sendPhoneBtn = !(val.match(phone))
    },
    idCheck: function (){
      if (this.idCheck === false){
        this.email = '';
        this.idCheck = true;
      }
    }
  },
  beforeUnmount() {
    clearInterval(this.polling)
  }
})
</script>

<style scoped>
#basic{
  margin: 10px 27% 0 27%;
  height: 100%;
}
p{
  color: rgba(33,33,36,0.6);
}
#phone_check{
  font-size: 16px;
  width: 18%;
  height: 54px;
  color: white;
  align-content: center;
  margin-left: 20px;
}
.my_btn{
  width: 100%;
  font-weight: bolder;
  border-radius: 5px;
  height: 54px;
}
</style>