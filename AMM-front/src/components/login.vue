<template>
  <div id='div1'>
    <p>用户名：<input :value="username"></input></p>
    <p>密码：<input :value="password" type="password"></input></p>
  </div>
</template>

<script>
export default {
  data () {
    return {
  name: 'App',
  username:'',
  password:''
}
  },

methods:{
  scendMethod(){
      this.username='liyan'
      this.password='123456'
  },
},
mounted(){
       this.scendMethod();
    }
};
</script>

<style>
  
</style>