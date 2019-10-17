<template>
 <div>
   <form id="f1">
    <input type="file" ref="myfile">
    <el-button @click="upload" type="success" size="mini" icon="el-icon-upload2">上传</el-button>
  </form>
    <span></span>
 </div>
  </template>

k
<script>
  import axios from 'axios';
export default {
  data () {
    return {
  myfile:{},
  name: 'App',
  username:'',
  password:''
}
  },

methods:{
  upload(){
    axios.post("/myapi/sys/user/isRepeat", { file:this.myfile.file})
          .then(res => {
                alert("上传结束")
              })
        }
},
mounted(){
       //this.scendMethod();
    }
};
</script>

<style>
  
</style>