<template>
  <div>
    <el-form>
      <el-form-item>
        <el-row :gutter="15" type="flex">
          <el-col :span="2">
            <el-button type="primary" size="small" icon="el-icon-refresh"
                       v-if="hasPermission('log:list')"
                       @click.native.prevent="refresh">刷新
            </el-button>
          </el-col>
          <el-col :span="12">
            <el-date-picker
              v-model="selectedSearchTime"
              type="datetimerange"
              format="yyyy-MM-dd HH:mm:ss"
              value-format="yyyy-MM-dd HH:mm:ss"
              align="right"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              :default-time="['12:00:00', '08:00:00']">
            </el-date-picker>
            <el-button icon="el-icon-search" size="small" @click="searchByTimeAndType">按时间搜索</el-button>
          </el-col>
          <el-col :span="7">
            <el-input
              v-model="search"
              size="medium"
              placeholder="输入方法名、操作描述或IP进行搜索..."/>
          </el-col>
          <el-col :span="2">
            <el-button type="danger" size="small" icon="el-icon-delete"
              @click="deleteLogByType">删除全部</el-button>
          </el-col>
        </el-row>
      </el-form-item>
    </el-form>
    <el-table :data="logList.filter(data => !search
                    || data.methodName.toLowerCase().includes(search.toLowerCase())
                    || data.description.toLowerCase().includes(search.toLowerCase())
                    || data.requestIp.toLowerCase().includes(search.toLowerCase()))"
              v-loading.body="listLoading"
              element-loading-text="全力加载中..."
              stripe border fit highlight-current-row>
      <el-table-column label="用户名" align="center" prop="username" min-width="40%">
        <template slot-scope="scope">
          <i class="el-icon-user"></i>
          <span v-text="scope.row.username"></span>
        </template>
      </el-table-column>
      <el-table-column label="IP" align="center" prop="requestIp" min-width="60%">
        <template slot-scope="scope">
          <i class="el-icon-place"></i>
          <span v-text="scope.row.requestIp"></span>
        </template>
      </el-table-column>
      <el-table-column label="操作描述" align="center" prop="description" min-width="50%">
        <template slot-scope="scope">
          <i class="el-icon-chat-dot-round"></i>
          <span v-text="scope.row.description"></span>
        </template>
      </el-table-column>
      <el-table-column label="方法名称" align="center" prop="methodName" min-width="40%">
        <template slot-scope="scope">
          <el-tooltip :content="scope.row.methodName" placement="bottom" effect="light">
            <span class="method-truncation">
              <i class="el-icon-printer"></i>
              {{ scope.row.methodName }}
            </span>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column label="参数" align="center" prop="params" min-width="40%">
        <template slot-scope="scope">
          <el-tooltip :content="scope.row.params" placement="bottom" effect="light">
            <span class="param-truncation">
              <i class="el-icon-mobile-phone"></i>
              {{ scope.row.params }}
            </span>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column label="操作耗时" align="center" sortable prop="costTime" min-width="50%">
        <template slot-scope="scope">
          <el-tag effect="dark">
            <i class="el-icon-time"></i>{{ scope.row.costTime }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="创建日期" align="center" sortable prop="createTime" min-width="70%">
        <template slot-scope="scope">
          <i class="el-icon-time"></i>
          <span v-text="scope.row.createTime"></span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center"
                       v-if="hasPermission('log:delete') || hasPermission('log:list')">
        <template slot-scope="scope">
          <el-button-group>
          <el-button type="primary" size="mini" icon="el-icon-document"
                     v-if="hasPermission('log:list')"
                     @click.native.prevent="showLogDetails(scope.row)">详情
          </el-button>
          <el-button type="danger" size="mini" icon="el-icon-delete"
                     v-if="hasPermission('log:delete')"
                     @click.native.prevent="deleteLogById(scope.row.id, scope.row.username, scope.row.description,
                      scope.row.createTime)">删除
          </el-button>
          </el-button-group>
        </template>
      </el-table-column>
    </el-table>
    <div class="pagination-container">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="page"
        :page-size="size"
        :total="total"
        :page-sizes="[10, 30, 50, 100]"
        background
        layout="total, sizes, prev, pager, next, jumper">
      </el-pagination>
    </div>

    <el-dialog :title="detailDialogTitle" width="90%" :close-on-click-modal="false"
               :visible.sync="detailDialogVisible" center>
      <el-scrollbar style="height:100%">
        <prism-editor v-model="logDetail" language="js" :readonly="true"></prism-editor>
      </el-scrollbar>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="detailDialogVisible = false" icon="el-icon-error">关闭</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  import { listLogsByType, listByTimeAndType, deleteById, deleteByType } from '@/api/log'
  import PrismEditor from 'vue-prism-editor'
  import "prismjs";
  import "prismjs/themes/prism.css";

  export default {
    name: 'CommonLog',
    components: { PrismEditor },
    props: {
      isErrorLog: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        logType: '',
        logList: [],
        search: '',
        total: 0,
        page: 1,
        size: 10,
        selectedSearchTime: '',
        listLoading: false,
        logDetail: '',
        detailDialogVisible: false,
      }
    },
    computed: {
      detailDialogTitle: function () {
        return this.isErrorLog ? '异常日志' : '业务日志'
      }
    },
    created() {
      if (this.hasPermission('log:list')) {
        this.logType = this.isErrorLog ? 'error' : 'business';
        this.listLogsByType();
      }
    },
    methods: {
      refresh(){
        this.listLogsByType();
        this.$message.success('已是最新');
      },
      listLogsByType(){
        this.listLoading = true;
        listLogsByType(this.page, this.size, this.logType).then(response =>{
          if (response.returnCode === 200) {
            this.logList = response.data.list;
            this.total = response.data.total;
            this.listLoading = false;
          }else{
            this.$message.error(response.message);
            console.error(response);
          }
        }).catch(error => {
          this.$message.error(error.message);
          console.error(error);
        })
      },
      showLogDetails(log) {
        this.detailDialogVisible = true;
        this.logDetail = this.isErrorLog ? log.exceptionDetails : log.params;
      },
      deleteLogById(id, username, description, createTime) {
        this.$confirm('您确认要删除用户 [' + username + '] 在 [' + createTime + '] 进行 [' + description + '] 操作的记录吗?', 'Warning', {
          confirmButtonText: '删除',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          deleteById(id).then(response => {
            if (response.returnCode === 200) {
              this.$message.success('删除记录成功！');
              this.listLogsByType();
            } else {
              this.$message.error(response.message);
              this.listLogsByType();
            }
          }).catch(error => {
            this.$message.error(error.message);
            console.error(error);
            this.listLogsByType();
          })
        });
      },
      deleteLogByType() {
        this.$confirm('您确认要删除所有' + this.detailDialogTitle + '的记录吗?', 'Warning', {
          confirmButtonText: '删除',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          deleteByType(this.logType).then(response => {
            if (response.returnCode === 200) {
              this.$message.success('删除记录成功！');
              this.listLogsByType();
            } else {
              this.$message.error(response.message);
              this.listLogsByType();
            }
          }).catch(error => {
            this.$message.error(error.message);
            console.error(error);
            this.listLogsByType();
          })
        });
      },
      searchByTimeAndType() {
        if (!this.selectedSearchTime) {
          this.page = 1;
          this.size = 10;
          this.listLogsByType();
          return;
        }
        this.listLoading = true;
        listByTimeAndType(this.page, this.size, this.logType, this.selectedSearchTime[0],
            this.selectedSearchTime[1]).then(response => {
          if (response.returnCode === 200) {
            this.logList = response.data.list;
            this.total = response.data.total;
            this.listLoading = false;
          }else{
            this.$message.error(response.message);
            console.error(response);
          }
        }).catch(error => {
          this.$message.error(error.message);
          console.error(error);
          this.listLogsByType();
        });
      },
      handleSizeChange(size) {
        this.size = size;
        this.page = 1;
        this.listLogsByType()
      },
      handleCurrentChange(page) {
        this.page = page;
        this.listLogsByType()
      },
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>

  .method-truncation {
    display: inline-block;
    width: 80%;
    overflow: hidden;
    text-overflow:ellipsis;
    white-space: nowrap;
  }

  .param-truncation {
    display: inline-block;
    width: 70%;
    overflow: hidden;
    text-overflow:ellipsis;
    white-space: nowrap;
  }

</style>
