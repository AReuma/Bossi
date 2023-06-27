<template>
    <div id='nav'>
      <div id="header">

        <div id="title">
          <div id="bColor">B</div>
          <div id="ossiColor">ossi</div>
        </div>

        <div style="display: flex; width: 100%; padding-top: 15px">
          <div id='nav_list'>
            <div style="float: left; padding-right: 7%; color: #fc9899">작품</div>
            <div style="float: left; color: #434f58">클래스</div>
          </div>

          <div id='nav_right'>
            <div id="searchBar">
              <input
                  class="search"
                  type="text"
                  placeholder="물품이나 동네를 검색해보세요"
              />
            </div>

            <div>
              <v-btn id='my_button' depressed>
                로그인
              </v-btn>
            </div>
          </div>
        </div>
      </div>
    </div>
</template>

<script>
export default {
  name: "HomeView",
  data() {
    return {
    }
  }
};
</script>

<style scoped>
  #nav{
    background-color: white;
    color: #212124;
    top: 0;
    left: 0;
    width: 100%;
    max-width: 100vw;
    padding-top: 10px;
    /* position: fixed;
    z-index: 999; */
  }
  #header {
    max-width: 1200px;
    height: 64px;
    padding: 12px 16px;
    box-sizing: border-box;
    justify-content: space-between;
    width: 100%;
    margin: 0 auto;
    position: relative;
    display: flex;
    align-items: center;
  }
  #title {
    text-decoration: none;
    width: 160px;
  }
  #bColor{
    color: #fc9899;
    font-family: ONE-Mobile-POP, serif;
    font-size: 42px;
    float: left;
  }
  #ossiColor{
    color: #3d4148;
    font-family: GangwonEduSaeeum_OTFMediumA, serif;
    font-size: 42px;
    float: left;
  }
  #nav_list{
    width: 180px;
    font-size: 25px;
    font-family: GmarketSansMedium,serif;
  }
  #nav_list .router-link-active,
  #nav_list .router-link-exact-active {
    text-decoration: none;
    line-height: 1.32;
    font-size: 18px;
    font-style: normal;
    margin-right: 30px;
    color: green;
  }
  #nav_item{
    text-decoration: none;
    line-height: 1.32;
    font-size: 18px;
    font-weight: 700;
    font-style: normal;
    margin-right: 30px;
    color: #4d5159;
  }
  #nav_right{
    display: flex;
  }
  #searchBar {
    display: block;
    position: relative;
    margin-right: 12px;
  }
  input {
    width: 350px;
    line-height: 1.36;
    font-size: 16px;
    /*background-color: #f2f3f6;*/
    box-sizing: border-box;
    height: 40px;
    /*padding: 9px 12px;*/
    border: #fc9899 2px solid;
    border-radius: 6px;
    color: #212124;
  }
  #my_button{
    line-height: 1.3;
    font-size: 16px;
    /*letter-spacing: -2%;*/
    display: block;
    white-space: nowrap;
    background-color: white;
    color: #212124;
    height: 40px;
    font-weight: 700;
    width: fit-content;
    min-height: 28px;
    padding: 5px 10px;
    border-radius: 3px;
    border: 1px solid #d1d3d8;
    background: none;
    appearance: none;
    justify-content: center;
    align-items: center;
    cursor: pointer;
  }
</style>