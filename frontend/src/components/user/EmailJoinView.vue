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
        <v-text-field
            label="이메일을 입력해주세요."
            outlined
            color="#434f58"
            v-model="email"
        ></v-text-field>
      </div>

      <div style="height: 200px; margin-top: 25px;">
        <p>비밀번호 <span style="color: red; font-weight: lighter">*</span></p>
        <v-text-field
            :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
            :type="show ? 'text' : 'password'"
            @click:append="show = !show"
            label="비밀번호 (영문+숫자+특수문자 8자 이상)"
            outlined
            style="height: 80px"
            color="#434f58"
            v-model="password"
        ></v-text-field>
        <v-text-field
            :append-icon="showRe ? 'mdi-eye' : 'mdi-eye-off'"
            :type="showRe ? 'text' : 'password'"
            @click:append="showRe = !showRe"
            label="비밀번호 확인"
            outlined
            style="height: 82px"
            color="#434f58"
        ></v-text-field>
      </div>

      <div style="height: 100px; margin-top: 25px;">
        <p>닉네임 <span style="color: red; font-weight: lighter">*</span></p>
        <v-text-field
            label="닉네임을 입력해주세요."
            outlined
            color="#434f58"
            v-model="nickName"
        ></v-text-field>
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
          ></v-text-field>

          <v-btn color="DEEP_PINK"
                 depressed
                 :disabled = this.phoneCheck
                 @click="clickPhoneCheck()"
                 id="phone_check">인증요청</v-btn>
        </div>
      </div>

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

export default defineComponent({
  name: "EmailJoinView",
  data(){
    return{
      show: false,
      showRe: false,
      selectAll: false,
      phoneCheck: true,
      email: '',
      password: '',
      nickName: '',
      phoneNum: '',
      recommender: '',
      select: [],
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

    },
    register(){
      const {email, password, nickName, phoneNum, recommender} = this;
      let select = this.select.includes('c4');
      this.$emit('register', {email, password, nickName, phoneNum, recommender, select})
    }
  },
  watch: {
    phoneNum: function (val){
      this.phoneCheck = val === '';
    }
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