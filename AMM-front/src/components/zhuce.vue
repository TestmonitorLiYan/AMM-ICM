<template>
  <div id='div1'>
    <p>�û�����<input :value="username"></input></p>
    <p>���룺<input :value="password" type="password"></input></p>
    <el-progress :text-inside="true" :stroke-width="26" :percentage="70"></el-progress>
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
       //this.scendMethod();
    }
};
</script>