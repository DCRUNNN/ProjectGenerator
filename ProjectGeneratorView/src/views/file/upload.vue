<template>
  <div class="app-container">
    <div class="filter-container">
      <div class="upload-container">
        <div class="upload-header">
          <h2>上传文件</h2>
        </div>
        <div class="upload-content">
          <div class="single-upload">
            <h3>上传单个文件</h3>
            <form id="singleUploadForm" name="singleUploadForm">
              <input type="file" name="file" class="file-input" @change="getSingleFile($event)" required>
              <el-button class="submit-btn" type="primary" icon="el-icon-upload2" :loading="uploadBTLoading"
                         @click="submitSingleFile($event)">上传</el-button>
            </form>
          </div>
          <div class="multiple-upload">
            <h3>上传多个文件</h3>
            <form id="multipleUploadForm" name="multipleUploadForm">
              <input type="file" name="files" class="file-input" @change="getMultipleFiles($event)" multiple required />
              <el-button class="submit-btn" type="primary" icon="el-icon-upload" :loading="uploadBTLoading"
                         @click="submitMultipleFile($event)">上传</el-button>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import { uploadSingleFile , uploadMultipleFile } from '@/api/file'
  import axios from 'axios';

  export default {
    data() {
      return {
        file: '',
        fileList:'',
        uploadBTLoading: false,
      }
    },
    computed: {},
    methods: {
      getSingleFile: function (event) {
        this.file = event.target.files[0];
        console.log(this.file);
      },
      getMultipleFiles:function(event){
        this.fileList = event.target.files;
        console.log(this.fileList);
      },
      submitSingleFile: function (event) {
        this.uploadBTLoading = true;
        event.preventDefault();
        let formData = new FormData();
        formData.append("file", this.file);
        uploadSingleFile(formData).then(response => {
          if(response.returnCode === 200) {
            this.$message.success("成功上传 " + this.file.name + " 文件");
            this.uploadBTLoading = false;
          }
        }).catch(error => {
          this.$message.error("上传失败");
          this.uploadBTLoading = false;
          console.error(error);
        })
      },
      submitMultipleFile: function(event){
        this.uploadBTLoading = true;
        event.preventDefault();
        let formData = new FormData();
        for(let index = 0; index < this.fileList.length; index++) {
          formData.append("files", this.fileList[index]);
        }
        uploadMultipleFile(formData).then(response => {
          if(response.returnCode === 200) {
            this.$message.success("成功上传"+ this.fileList.length + "个文件");
            this.uploadBTLoading = false;
          }
        }).catch(error => {
          this.$message.error("上传失败");
          this.uploadBTLoading = false;
          console.error(error);
        })
      },
    }
  };
</script>

<style>
  /** {*/
     /*-webkit-box-sizing: border-box;*/
     /*-moz-box-sizing: border-box;*/
    /*box-sizing: border-box;*/
  /*}*/

  body {
    margin: 0;
    padding: 0;
    font-weight: 400;
    font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
    font-size: 1rem;
    line-height: 1.58;
    color: #333;
    /*background-color: #f4f4f4;*/
  }

  body:before {
    height: 100%;
    width: 100%;
    position: absolute;
    top: 0;
    left: 0;
    /*background: #128ff2;*/
    content: "";
    z-index: 0;
  }

  .clearfix:after {
    display: block;
    content: "";
    clear: both;
  }

  h1, h2, h3, h4, h5, h6 {
    margin-top: 20px;
    margin-bottom: 20px;
  }

  h1 {
    font-size: 1.7em;
  }

  a {
    color: #128ff2;
  }

  button {
    box-shadow: none;
    border: 1px solid transparent;
    font-size: 14px;
    outline: none;
    line-height: 100%;
    white-space: nowrap;
    vertical-align: middle;
    padding: 0.6rem 1rem;
    border-radius: 2px;
    transition: all 0.2s ease-in-out;
    cursor: pointer;
    min-height: 38px;
  }

  button.primary {
    background-color: #128ff2;
    box-shadow: 0 2px 2px 0 rgba(0, 0, 0, 0.12);
    color: #fff;
  }

  input {
    font-size: 1rem;
  }

  input[type="file"] {
    border: 1px solid #128ff2;
    padding: 6px;
    max-width: 100%;
  }

  .file-input {
    width: 100%;
  }

  .submit-btn {
    display: block;
    margin-top: 15px;
    min-width: 100px;
  }

  @media screen and (min-width: 500px) {
    .file-input {
      width: calc(100% - 115px);
    }

    .submit-btn {
      display: inline-block;
      margin-top: 0;
      margin-left: 10px;
    }
  }

  .upload-container {
    max-width: 80%;
    margin-left: auto;
    margin-right: auto;
    background-color: #fff;
    box-shadow: 0 1px 11px rgba(0, 0, 0, 0.27);
    margin-top: 2%;
    min-height: 400px;
    position: relative;
    padding: 20px;
  }

  .upload-header {
    border-bottom: 1px solid #ececec;
  }

  .upload-header h2 {
    font-weight: 500;
  }

  .single-upload {
    padding-bottom: 20px;
    margin-bottom: 20px;
    border-bottom: 1px solid #e8e8e8;
  }

</style>
