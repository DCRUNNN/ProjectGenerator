(window.webpackJsonp=window.webpackJsonp||[]).push([[22],{oJh5:function(e,t,s){"use strict";var o=s("plSN");s.n(o).a},plSN:function(e,t,s){},u9Ud:function(e,t,s){"use strict";s.r(t);var o=s("t3Un"),i=s("QxqB"),a=s.n(i),n=(s("wZee"),s("qHiR"),{name:"CommonLog",components:{PrismEditor:a.a},props:{isErrorLog:{type:Boolean,default:!1}},data:function(){return{logType:"",logList:[],search:"",total:0,page:1,size:10,selectedSearchTime:"",listLoading:!1,logDetail:"",detailDialogVisible:!1}},computed:{detailDialogTitle:function(){return this.isErrorLog?"异常日志":"业务日志"}},created:function(){this.hasPermission("log:list")&&(this.logType=this.isErrorLog?"error":"business",this.listLogsByType())},methods:{refresh:function(){this.listLogsByType(),this.$message.success("已是最新")},listLogsByType:function(){var e,t,s,i=this;this.listLoading=!0,(e=this.page,t=this.size,s=this.logType,Object(o.a)({url:"/log/listLogsByType",method:"get",params:{page:e,size:t,logType:s}})).then(function(e){200===e.returnCode?(i.logList=e.data.list,i.total=e.data.total,i.listLoading=!1):(i.$message.error(e.message),console.error(e))}).catch(function(e){i.$message.error(e.message),console.error(e)})},showLogDetails:function(e){this.detailDialogVisible=!0,this.logDetail=this.isErrorLog?e.exceptionDetails:e.params},deleteLogById:function(e,t,s,i){var a=this;this.$confirm("您确认要删除用户 ["+t+"] 在 ["+i+"] 进行 ["+s+"] 操作的记录吗?","Warning",{confirmButtonText:"删除",cancelButtonText:"取消",type:"warning"}).then(function(){var t;(t=e,Object(o.a)({url:"/log/deleteLogById",method:"get",params:{logId:t}})).then(function(e){200===e.returnCode?(a.$message.success("删除记录成功！"),a.listLogsByType()):(a.$message.error(e.message),a.listLogsByType())}).catch(function(e){a.$message.error(e.message),console.error(e),a.listLogsByType()})})},deleteLogByType:function(){var e=this;this.$confirm("您确认要删除所有"+this.detailDialogTitle+"的记录吗?","Warning",{confirmButtonText:"删除",cancelButtonText:"取消",type:"warning"}).then(function(){var t;(t=e.logType,Object(o.a)({url:"/log/deleteLogByType",method:"get",params:{logType:t}})).then(function(t){200===t.returnCode?(e.$message.success("删除记录成功！"),e.listLogsByType()):(e.$message.error(t.message),e.listLogsByType())}).catch(function(t){e.$message.error(t.message),console.error(t),e.listLogsByType()})})},searchByTimeAndType:function(){var e,t,s,i,a,n=this;if(!this.selectedSearchTime)return this.page=1,this.size=10,void this.listLogsByType();this.listLoading=!0,(e=this.page,t=this.size,s=this.logType,i=this.selectedSearchTime[0],a=this.selectedSearchTime[1],Object(o.a)({url:"/log/listByTimeAndType",method:"get",params:{page:e,size:t,logType:s,startTime:i,endTime:a}})).then(function(e){200===e.returnCode?(n.logList=e.data.list,n.total=e.data.total,n.listLoading=!1):(n.$message.error(e.message),console.error(e))}).catch(function(e){n.$message.error(e.message),console.error(e),n.listLogsByType()})},handleSizeChange:function(e){this.size=e,this.page=1,this.listLogsByType()},handleCurrentChange:function(e){this.page=e,this.listLogsByType()}}}),l=(s("oJh5"),s("KHd+")),r=Object(l.a)(n,function(){var e=this,t=e.$createElement,s=e._self._c||t;return s("div",[s("el-form",[s("el-form-item",[s("el-row",{attrs:{gutter:15,type:"flex"}},[s("el-col",{attrs:{span:2}},[e.hasPermission("log:list")?s("el-button",{attrs:{type:"primary",size:"small",icon:"el-icon-refresh"},nativeOn:{click:function(t){return t.preventDefault(),e.refresh(t)}}},[e._v("刷新\n          ")]):e._e()],1),e._v(" "),s("el-col",{attrs:{span:12}},[s("el-date-picker",{attrs:{type:"datetimerange",format:"yyyy-MM-dd HH:mm:ss","value-format":"yyyy-MM-dd HH:mm:ss",align:"right","start-placeholder":"开始日期","end-placeholder":"结束日期","default-time":["12:00:00","08:00:00"]},model:{value:e.selectedSearchTime,callback:function(t){e.selectedSearchTime=t},expression:"selectedSearchTime"}}),e._v(" "),s("el-button",{attrs:{icon:"el-icon-search",size:"small"},on:{click:e.searchByTimeAndType}},[e._v("按时间搜索")])],1),e._v(" "),s("el-col",{attrs:{span:7}},[s("el-input",{attrs:{size:"medium",placeholder:"输入方法名、操作描述或IP进行搜索..."},model:{value:e.search,callback:function(t){e.search=t},expression:"search"}})],1),e._v(" "),s("el-col",{attrs:{span:2}},[s("el-button",{attrs:{type:"danger",size:"small",icon:"el-icon-delete"},on:{click:e.deleteLogByType}},[e._v("删除全部")])],1)],1)],1)],1),e._v(" "),s("el-table",{directives:[{name:"loading",rawName:"v-loading.body",value:e.listLoading,expression:"listLoading",modifiers:{body:!0}}],attrs:{data:e.logList.filter(function(t){return!e.search||t.methodName.toLowerCase().includes(e.search.toLowerCase())||t.description.toLowerCase().includes(e.search.toLowerCase())||t.requestIp.toLowerCase().includes(e.search.toLowerCase())}),"element-loading-text":"全力加载中...",stripe:"",border:"",fit:"","highlight-current-row":""}},[s("el-table-column",{attrs:{label:"用户名",align:"center",prop:"username","min-width":"40%"},scopedSlots:e._u([{key:"default",fn:function(t){return[s("i",{staticClass:"el-icon-user"}),e._v(" "),s("span",{domProps:{textContent:e._s(t.row.username)}})]}}])}),e._v(" "),s("el-table-column",{attrs:{label:"IP",align:"center",prop:"requestIp","min-width":"60%"},scopedSlots:e._u([{key:"default",fn:function(t){return[s("i",{staticClass:"el-icon-place"}),e._v(" "),s("span",{domProps:{textContent:e._s(t.row.requestIp)}})]}}])}),e._v(" "),s("el-table-column",{attrs:{label:"操作描述",align:"center",prop:"description","min-width":"50%"},scopedSlots:e._u([{key:"default",fn:function(t){return[s("i",{staticClass:"el-icon-chat-dot-round"}),e._v(" "),s("span",{domProps:{textContent:e._s(t.row.description)}})]}}])}),e._v(" "),s("el-table-column",{attrs:{label:"方法名称",align:"center",prop:"methodName","min-width":"40%"},scopedSlots:e._u([{key:"default",fn:function(t){return[s("el-tooltip",{attrs:{content:t.row.methodName,placement:"bottom",effect:"light"}},[s("span",{staticClass:"method-truncation"},[s("i",{staticClass:"el-icon-printer"}),e._v("\n            "+e._s(t.row.methodName)+"\n          ")])])]}}])}),e._v(" "),s("el-table-column",{attrs:{label:"参数",align:"center",prop:"params","min-width":"40%"},scopedSlots:e._u([{key:"default",fn:function(t){return[s("el-tooltip",{attrs:{content:t.row.params,placement:"bottom",effect:"light"}},[s("span",{staticClass:"param-truncation"},[s("i",{staticClass:"el-icon-mobile-phone"}),e._v("\n            "+e._s(t.row.params)+"\n          ")])])]}}])}),e._v(" "),s("el-table-column",{attrs:{label:"操作耗时",align:"center",sortable:"",prop:"costTime","min-width":"50%"},scopedSlots:e._u([{key:"default",fn:function(t){return[s("el-tag",{attrs:{effect:"dark"}},[s("i",{staticClass:"el-icon-time"}),e._v(e._s(t.row.costTime)+"\n        ")])]}}])}),e._v(" "),s("el-table-column",{attrs:{label:"创建日期",align:"center",sortable:"",prop:"createTime","min-width":"70%"},scopedSlots:e._u([{key:"default",fn:function(t){return[s("i",{staticClass:"el-icon-time"}),e._v(" "),s("span",{domProps:{textContent:e._s(t.row.createTime)}})]}}])}),e._v(" "),e.hasPermission("log:delete")||e.hasPermission("log:list")?s("el-table-column",{attrs:{label:"操作",align:"center"},scopedSlots:e._u([{key:"default",fn:function(t){return[s("el-button-group",[e.hasPermission("log:list")?s("el-button",{attrs:{type:"primary",size:"mini",icon:"el-icon-document"},nativeOn:{click:function(s){return s.preventDefault(),e.showLogDetails(t.row)}}},[e._v("详情\n        ")]):e._e(),e._v(" "),e.hasPermission("log:delete")?s("el-button",{attrs:{type:"danger",size:"mini",icon:"el-icon-delete"},nativeOn:{click:function(s){return s.preventDefault(),e.deleteLogById(t.row.id,t.row.username,t.row.description,t.row.createTime)}}},[e._v("删除\n        ")]):e._e()],1)]}}])}):e._e()],1),e._v(" "),s("div",{staticClass:"pagination-container"},[s("el-pagination",{attrs:{"current-page":e.page,"page-size":e.size,total:e.total,"page-sizes":[10,30,50,100],background:"",layout:"total, sizes, prev, pager, next, jumper"},on:{"size-change":e.handleSizeChange,"current-change":e.handleCurrentChange}})],1),e._v(" "),s("el-dialog",{attrs:{title:e.detailDialogTitle,width:"90%","close-on-click-modal":!1,visible:e.detailDialogVisible,center:""},on:{"update:visible":function(t){e.detailDialogVisible=t}}},[s("el-scrollbar",{staticStyle:{height:"100%"}},[s("prism-editor",{attrs:{language:"js",readonly:!0},model:{value:e.logDetail,callback:function(t){e.logDetail=t},expression:"logDetail"}})],1),e._v(" "),s("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[s("el-button",{attrs:{type:"primary",icon:"el-icon-error"},on:{click:function(t){e.detailDialogVisible=!1}}},[e._v("关闭")])],1)],1)],1)},[],!1,null,"6295d15c",null);t.default=r.exports}}]);
//# sourceMappingURL=22.c1021d6a1bac9cf19469.js.map