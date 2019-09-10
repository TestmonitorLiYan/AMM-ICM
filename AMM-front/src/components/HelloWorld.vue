<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
    <h2>There is AMM ICM App</h2>
    <h3 id="lo">
    <a href="/login">登录</a>&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;<a href="/zhuce">注册</a>
  </h3>
  <input :value='buttonV' type="button" @click='myfirstmethod' id="bu"/>
  </div>

</template>

<script>
export default {
  name: 'hello',
  data () {
    return {
      msg: 'Welcome to My Vue.js App',
      buttonV: '你猜'
    }
  },
  methods:{
    myfirstmethod(){
        this.$router.push({ path: "/login" });
    },
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  a{
    color:black;
  }
h1, h2 {
  font-weight: normal;
}
#lo{
  color: aqua
}
.hello{
  background-image:url(/static/u=4253056807,2612952362&fm=76.jpg)
}
#bu{
  color:black;
  width: 40px;
  height: 20px;
}
</style>
