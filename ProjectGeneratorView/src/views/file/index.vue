<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form>
        <el-form-item>
          <el-row :gutter="20">
            <el-col :span="6">
              <el-button type="success" icon="el-icon-refresh"
                         v-if="hasPermission('file:list')"
                         @click.native.prevent="refresh">刷新
              </el-button>
              <router-link :to="'/file/upload/'">
                <el-button type="primary" icon="el-icon-upload2"
                           v-if="hasPermission('file:upload')">上传
                </el-button>
              </router-link>
            </el-col>
            <el-col :span="8" :offset="10">
              <el-input
                v-model="search"
                size="medium"
                placeholder="输入关键字搜索"/>
            </el-col>
          </el-row>
        </el-form-item>
      </el-form>
    </div>
    <el-table :data="fileList.filter(data => !search
                    || data.fileName.toLowerCase().includes(search.toLowerCase())
                    || data.fileType.includes(search)
                    || data.lastModifyTime.includes(search)
                    || data.fileDownloadUri.includes(search))"
              v-loading.body="listLoading"
              element-loading-text="全力加载中..."
              :default-sort = "{prop: 'date', order: 'descending'}"
              border fit highlight-current-row>
      <el-table-column label="文件名" min-width="80&" align="center" sortable>
        <template slot-scope="scope">
          <span v-text="scope.row.fileName"></span>
        </template>
      </el-table-column>
      <el-table-column label="文件类型" align="center" min-width="40%" sortable>
        <template slot-scope="scope">
          <span v-text="scope.row.fileType"></span>
        </template>
      </el-table-column>
      <el-table-column label="文件大小" align="center" min-width="40%" sortable>
        <template slot-scope="scope">
          <span v-text="scope.row.size+' B'"></span>
        </template>
      </el-table-column>
      <el-table-column label="最后修改时间" align="center" min-width="60%" prod="date" sortable>
        <template slot-scope="scope">
          <span v-text="scope.row.lastModifyTime"></span>
        </template>
      </el-table-column>
      <el-table-column label="操作" min-width="60%" align="center"
                       v-if="hasPermission('file:download') || hasPermission('file:delete')">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" icon="el-icon-download"
                     v-if="hasPermission('file:download')" :loading="btnLoading"
                     @click.native.prevent="downloadFile(scope.row.fileName)">下载
          </el-button>
          <el-button type="danger" size="mini" icon="el-icon-delete"
                     v-if="hasPermission('file:delete')"
                     @click.native.prevent="deleteFile(scope.row.fileName)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

  </div>
</template>

<script>
  import { getAllFiles , downloadFile, deleteFile } from '@/api/file'
  import { getUserInfo } from '@/api/user'
  import { mapGetters } from 'vuex'

  export default {
    components: { },
    name: 'FileList',
//  components: { Pagination },
    data() {
      return {
        search: '',
        fileList: [], // 文件列表
        listLoading: false, // 数据加载等待动画
        btnLoading: false, // 按钮等待动画
        readonly: false, // 只读输入框
      };
    },
    created() {
      if (this.hasPermission('file:list')) {
        this.getAllFilesList()
      }
      getUserInfo().then(response => {
        console.log(response)
      })
    },
    methods: {
      refresh(){
        this.getAllFilesList();
        this.$message.success('已是最新');
      },
      getAllFilesList() {
        this.listLoading = true;
        getAllFiles().then(response => {
          this.fileList = response.data;
          this.listLoading = false;
        })
      },
      downloadFile(fileName) {
        this.btnLoading = true;
        downloadFile(fileName).then(response => {
          let blob = new Blob([response]);
          if (window.navigator.msSaveOrOpenBlob) {
            navigator.msSaveBlob(blob, fileName);
            this.btnLoading = false;
          } else {
            let link = document.createElement("a");
            let evt = document.createEvent("HTMLEvents");
            evt.initEvent("click", false, false);
            link.href = URL.createObjectURL(blob);
            link.download = fileName;
            link.style.display = "none";
            document.body.appendChild(link);
            link.click();
            window.URL.revokeObjectURL(link.href);
            this.btnLoading = false;
          }
        }).catch(error => {
          this.$message.error("无法下载文件");
          console.error(error);
        })
      },
      deleteFile(fileName){
        this.$confirm('您确认要删除 ' + fileName + ' 文件吗?', 'Warning', {
          confirmButtonText: '删除',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          deleteFile(fileName).then(response => {
            if (response.returnCode === 200) {
              this.$message.success('删除文件成功！');
              this.getAllFilesList();
            } else {
              this.$message.error("删除文件失败");
              this.getAllFilesList();
              console.error(response);
            }
          }).catch(error => {
            this.$message.error("删除文件失败");
            console.error(error);
          })
        });
      }
    }
  };
</script>

<style scoped>
  .pagination-container {
    background: #fff;
    padding: 20px 16px;
  }
</style>
