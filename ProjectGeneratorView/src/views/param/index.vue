<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form>
        <el-form-item>
          <el-row :gutter="15" type="flex">
            <el-col :span="2">
              <el-button type="primary" size="small" icon="el-icon-refresh"
                         @click.native.prevent="">刷新
              </el-button>
            </el-col>
            <el-col :span="2">
              <el-button type="primary" size="small" icon="el-icon-plus"
                         @click.native.prevent="">新增
              </el-button>
            </el-col>
            <el-col :span="7">
              <el-input
                v-model="searchText"
                size="medium"
                placeholder="输入参数名进行搜索..."/>
            </el-col>
          </el-row>
        </el-form-item>
      </el-form>
      <el-table :data="paramList.filter(data => !searchText
                    || data.name.toLowerCase().includes(searchText.toLowerCase()))"
                v-loading.body="listLoading"
                element-loading-text="全力加载中..."
                stripe border fit highlight-current-row>
        <el-table-column label="参数ID" align="center" prop="id" min-width="40%">
          <template slot-scope="scope">
            <i class="el-icon-user"></i>
            <span v-text="scope.row.id"></span>
          </template>
        </el-table-column>
        <el-table-column label="参数名称" align="center" prop="name" min-width="60%">
          <template slot-scope="scope">
            <i class="el-icon-place"></i>
            <span v-text="scope.row.name"></span>
          </template>
        </el-table-column>
        <el-table-column label="参数简介" align="center" prop="description" min-width="50%">
          <template slot-scope="scope">
            <i class="el-icon-chat-dot-round"></i>
            <span v-text="scope.row.description"></span>
          </template>
        </el-table-column>
        <el-table-column label="创建日期" align="center" sortable prop="createTime" min-width="70%">
          <template slot-scope="scope">
            <i class="el-icon-time"></i>
            <span v-text="scope.row.createTime"></span>
          </template>
        </el-table-column>
        <el-table-column label="修改日期" align="center" sortable prop="updateTime" min-width="70%">
          <template slot-scope="scope">
            <i class="el-icon-time"></i>
            <span v-text="scope.row.updateTime"></span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button-group>
              <el-button type="primary" size="mini" icon="el-icon-document"
                         @click.native.prevent="updateParam(scope.id)">修改
              </el-button>
              <el-button type="danger" size="mini" icon="el-icon-delete"
                         @click.native.prevent="deleteParam(scope.id)">删除
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
    </div>
  </div>
</template>

<style>

</style>

<script>
  import { listAllPublicParams, insertPublicParam } from '@/api/param'

  export default {
    name: 'ParamManage',
    components: { },
    data() {
      return {
        searchText: '',
        paramList: [],
        listLoading: false,
        total: 0,
        page: 1,
        size: 10,
      }
    },
    mounted() {

    },
    created() {
      this.listAllPublicParams();
    },
    methods: {
      listAllPublicParams(){
        this.listLoading = true;
        listAllPublicParams(this.page, this.size).then(response =>{
          if (response.returnCode === 200) {
            this.paramList = response.data.list;
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
      updateParam(paramID) {
        this.$message.warning("马上支持")
      },
      deleteParam(paramID) {
        this.$message.warning("马上支持")
      },
      handleSizeChange(size) {
        this.size = size;
        this.page = 1;
        this.listAllPublicParams()
      },
      handleCurrentChange(page) {
        this.page = page;
        this.listAllPublicParams()
      },
    }
  }
</script>
