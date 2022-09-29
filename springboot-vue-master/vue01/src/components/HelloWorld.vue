<template>
    <div>
      <!-- 需要放在一个表单里面 -->
      <el-form :inline="true" class="demo-form-inline">
      <!-- 搜索框 -->
          <el-form-item label="姓名">
            <el-input v-model="searchUser" placeholder="请输入姓名"></el-input>
          </el-form-item>
          <!-- 查询按钮 -->
          <el-form-item >
            <el-button type="primary" @click="getBooksByName">搜索</el-button>
          </el-form-item>
          <!-- 添加按钮 -->
          <el-form-item >
            <el-button type="primary" @click="addDialogVisible = true">添加</el-button>
          </el-form-item>
          <!-- 刷新 -->
          <el-form-item >
            <el-button type="primary" @click="getBooks">刷新</el-button>
          </el-form-item>
          <el-form-item >
            <el-button type="primary" @click="handleBIthDelete()">批量删除</el-button>
          </el-form-item>
          <el-form-item>
            <el-select v-model="colData" multiple placeholder="请选择">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>


      </el-form>
      <!-- 表格数据 -->
       <el-table
      height="250"
      :data="books"
      style="width: 100%"
      @cell-click="clickTable"
      :default-sort = "{prop: 'userDate', order: 'descending'}"
      @selection-change="handleSelectionChange">
        <el-table-column
          type="selection"
          width="55">
        </el-table-column>
       <el-table-column type="expand" fixed="left" label="">
          <template slot-scope="props">
            <el-form label-position="left" inline class="demo-table-expand">
                <el-form-item label="课程">
                  <span>{{ props.row.userCourse }}</span>
                </el-form-item>
                <!-- <el-form-item label="日期">
                  <span>{{ props.row.userDate }}</span>
                </el-form-item> -->
            </el-form>
          </template>
       </el-table-column>
       <el-table-column
        v-if="colData.includes('userId')"
        prop="userId"
        label="编号"
        width="180">
      </el-table-column>
      <el-table-column
        v-if="colData.includes('userName')"
        prop="userName"
        label="姓名"
        width="180">
      </el-table-column>
      <el-table-column
        v-if="colData.includes('userDate')"
        prop="userDate"
        label="日期"
        sortable
        width="180">
      </el-table-column>
      <el-table-column
        v-if="colData.includes('userAddress')"
        prop="userAddress"
        label="地址">
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
            size="mini"
            @click="handleUpdate(scope.row)">更新</el-button>
          <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.row.userId)">删除</el-button>
        </template>
      </el-table-column>
       </el-table>
          <div class="area-divider">
          <el-divider> 从表</el-divider>
          </div>
          <!-- 从表数据 -->
          <el-table
          v-if="subtable.length>0"
          :data="subtable"
          style="width: 100%">
            <el-table-column
              prop="userNativePlace"
              label="籍贯"
              width="180">
            </el-table-column>
            <el-table-column
              prop="userTel"
              label="电话">
            </el-table-column>
          </el-table>
    <!-- 内嵌添加表单 -->
    <el-dialog
      title="添加表单"
      :visible.sync="addDialogVisible"
      width="50%">
      <el-form :model="addBookData" :rules="rules" ref="ruleForm"  label-width="100px">
        <el-form-item label="姓名" prop="name" class="userNameStype">
          <el-input v-model="addBookData.name" ></el-input>
        </el-form-item>
        <el-form-item label="日期" prop="date">
          <el-date-picker
            v-model="addBookData.date"
            type="datetime"
            placeholder="选择日期"
            value-format="yyyy-MM-dd HH:mm:ss">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="地址" prop="address" class="userAddressStype">
          <el-input  v-model="addBookData.address" ></el-input>
        </el-form-item>
        <el-form-item label="课程选择">
          <el-select v-model="addBookData.course" placeholder="请选择课程">
            <el-option label="语文" value="语文"></el-option>
            <el-option label="数学" value="数学"></el-option>
            <el-option label="英语" value="英语"></el-option>
          </el-select>
        </el-form-item>
        <!-- 添加从表信息 -->
        <el-form-item class="formTable">
          <div class="dialog-divider" style="margin-top: -15px; margin-bottom: 25px !important">
            <el-divider content-position="center">联系方式
            </el-divider>
          </div>
          <div class="flex-row">
            <el-tooltip  placement="top">
              <el-button type="primary" size="small" icon="el-icon-plus" @click="insertDetialEvent">
              </el-button>
            </el-tooltip>
          </div>
          <div class="area-content" style="
                        margin: 0;
                        padding: 0;
                        margin-top: 8px;
                        min-width: 100%;
                        max-width: 100%;
                      ">
            <el-table
              :data="tableDataCongBiao"
              style="width: 100%"
              >
                <el-table-column
                  prop="nativePlace"
                  label="籍贯"
                  width="180">
                  <template slot-scope="props">
                    <!-- <span>{{ props.row.nativePlace }}</span> -->
                  <el-input v-model="props.row.nativePlace"></el-input>
                  </template>
                </el-table-column>
                <el-table-column
                  prop="tel"
                  label="电话"
                  width="180">
                  <template slot-scope="props">
                  <el-input v-model="props.row.tel"></el-input>
                  </template>
                </el-table-column>
            </el-table>
          </div>
        </el-form-item>

        <!-- 从表可添加 -->

        <!-- <el-form :model="addSubtable"  label-width="100px">
          <el-form-item label="籍贯" prop="nativePlace" class="nativePlaceStype">
          <el-input v-model="addSubtable.nativePlace" ></el-input>
          </el-form-item>
          <el-form-item label="电话" prop="tel" class="telStype">
          <el-input v-model="addSubtable.tel" ></el-input>
          </el-form-item>
        </el-form> -->
        <!-- <el-form-item> -->
          <!-- Cascader 级联选择器 -->
          <!-- <el-cascader
            v-model="Value"
            :options="options"
            :props="{ expandTrigger: 'hover' }"
            @change="handleCascaderChange">
          </el-cascader>
        </el-form-item> -->
        <!-- <el-form-item label="授课老师">
          <el-select v-model="form.region" placeholder="请选择授课老师">
            <el-option label="王老师" value="teacherWang"></el-option>
            <el-option label="宋老师" value="teacherSong"></el-option>
            <el-option label="胡老师" value="teacherHu"></el-option>
          </el-select>
        </el-form-item> -->

        <el-form-item size="medium">
          <el-button type="primary" @click="addBook">添加</el-button>
          <el-button type="info" @click="cancel">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <!-- 内嵌更新表单 -->
    <el-dialog
      title="更新表单"
      :visible.sync="updateDialogVisible"
      width="50%">
      <el-form :model="updateBookData" :rules="rules" ref="ruleForm"  label-width="100px">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="updateBookData.userName" ></el-input>
        </el-form-item>
        <el-form-item label="日期" prop="date">
          <el-date-picker
            v-model="updateBookData.userDate"
            type="datetime"
            placeholder="选择日期"
            value-format="yyyy-MM-dd HH:mm:ss">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="地址" prop="userAddress">
          <el-input v-model="updateBookData.userAddress" ></el-input>
        </el-form-item>
        <el-form-item label="课程选择">
          <el-select v-model="updateBookData.course" placeholder="请选择课程">
            <el-option label="语文" value="语文"></el-option>
            <el-option label="数学" value="数学"></el-option>
            <el-option label="英语" value="英语"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item size="medium">
          <el-button type="primary" @click="handleUpdateBook">更新</el-button>
          <el-button type="info" @click="cancel">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <!-- 分页插件 -->
    <!-- 其中 :total="pageTotal"是控制查询完展示多少页的，传的数据是查询结果的总条数-->
    <div class="block">
    <span class="demonstration"></span>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page.sync="currentPage"
      :page-size.sync="pageSize"
      :page-count="5"
      layout=" prev, pager, next, jumper"
      :total="pageTotal">
    </el-pagination>
  </div>
    </div>


</template>

<script>
export default {
    data(){
      return{
          //添加多条从表数据
          tableDataCongBiao:[
            {
            nativePlace:"",
            tel:"",
          }
          ],

          // nativePlace:"",
          // tel:"",
          //姓名搜索
          searchUser:"",
          addDialogVisible:false,
          currentPage: 1,
          pageTotal: 100,
          pageSize: 5,
          //结束分页以及查询结果的数据
          books:[],
          addBookData:{
              name:"",
              date:"",
              address:"",
              course:"",
          },
          //会多一个id。因为需要id来更新
          updateBookData:{
              id:"",
              name:"",
              date:"",
              address:"",
              course:"",
          },
          // addSubtable:{
          //       nativePlace:"",
          //       tel:"",
          //     },
          //添加内嵌表单 默认false
          addDialogVisible:false,
          //更新内嵌表单 默认false
          updateDialogVisible:false,
          //批量删除
          multipleSelection: [],
          subtable:[],
          //是否显示该列 可以选择的选项
          colData:[],
          options: [{
          value: 'userId',
          label: '用户ID'
        }, {
          value: 'userName',
          label: '用户名'
        }, {
          value: 'userDate',
          label: '日期'
        }, {
          value: 'userAddress',
          label: '地址'
        }],
          //校验规则
          rules:{
              name:[
                {required:true,message:"姓名不能为空",trigger:"blur"},
               ],
              date: [
                  { required: true, message: '日期不能为空', trigger: 'blur' },
                ],
              address:[ {required:true,message:"地址不能为空",trigger:"blur"},]
              },
      }
    },
    methods:{
      //页码变化
      handleSizeChange(){
        this.getBooks();
      },
      //当前页变化
      handleCurrentChange(){
        this.getBooks();
      },
      //获取分页
      getBooks(){
        let postDate={
          pageIndex:this.currentPage,
          pageSize:this.pageSize,
        }
        this.axios.post("/bookManager_ajax/books",postDate)
                  .then(Response=>{
                      console.log(Response);
                      this.books=Response.data.records;
                      console.log(Response.data.total);
                      this.pageTotal=Response.data.total;
                      this.searchUser="";
                  }).catch(error=>{
                      console.log(error);
                  });
      },
      //降序分页 添加完可以看到当时添加的
      getBooksByDesc(){
        let postDate={
          pageIndex:this.currentPage,
          pageSize:this.pageSize,
        }
        this.axios.post("/pageByDesc",postDate)
                  .then(Response=>{
                      console.log(Response.data);
                      this.books=Response.data.records;
                      console.log(this.books)
                  }).catch(error=>{
                      console.log(error);
                  });
      },
      //查询加分页
      getBooksByName(){
        let postDate={
          pageIndex:this.currentPage,
          pageSize:this.pageSize,
          userName:this.searchUser
        }
        console.log(postDate);
        this.axios.post("/pageAndSeclectByName",postDate)
                  .then(Response=>{

                      this.books=Response.data.records;
                      this.pageTotal=Response.data.total;

                  }).catch(error=>{
                      console.log(error);
                  });

      },
      clickTable(row){
        console.log(row.userName);
        this.axios.get("/findSubInformation/"+row.userName)
                  .then(Response=>{
                    console.log(Response);
                      this.subtable=Response.data;

                  }).catch(error=>{
                      console.log(error);
                  });

      },
      //添加方法
      //添加主子表 用两个类来接收
      addBook(){
        this.$refs["ruleForm"].validate(valid=>{
          if(valid){
            let postdate={
              userName:this.addBookData.name,
              userDate:this.addBookData.date,
              userAddress:this.addBookData.address,
              link:this.tableDataCongBiao
            }
            console.log(postdate);
              this.axios.post("/addBook",postdate,{headers:{'content-type':'application/json;charset=UTF-8'}})
                        .then(Response=>{
                          this.addDialogVisible=false;
                          console.log(Response);
                          if(Response.status==200){
                              //添加完调用降序分页
                              this.getBooksByDesc();
                               alert("添加成功");
                               //添加成功后需要关闭那个内嵌表单并且将添加数据置空
                               this.addBookData={};

                          }else{
                               alert("添加失败");
                          }
                        }).catch(error=>{
                          console.log(error);
                        })
          }else{
            alert("校验失败")
          }
        })

      },
      //删除方法
      handleDelete(id){
        console.log(id);
         this.$confirm("删除操作, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",}).then(()=>{
          this.axios.get("/delete/"+id).then(response=>{
            console.log(response);
          if (response.status == 200) {
              this.$message({
                    type: "success",
                    message: "删除成功!",
                    duration:1000,
                  });
              // alert("删除成功");
              //删除完后需要刷新页面
              this.getBooks();
            }else{
              alert("删除失败");
            }
          }).catch((error) => {
            //  this.$message({
            //     type: "info",
            //     message: "已取消删除",
            //   });
            console.log(error);
            });
        })
      },

      //批量删除
      handleBIthDelete(){
         this.$confirm("删除操作, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",}).then(()=>{
          // 确定执行 then 方法
        var idList = [];
        // 遍历数组得到每个 id 值，设置到 idList 里面
        for (var i = 0; i < this.multipleSelection.length; i++) {
          var obj = this.multipleSelection[i];
          var id = obj.userId;
          idList.push(id);
        };
        console.log(idList)
          this.axios.post("/deleteBitch",idList).then(response=>{
            console.log(response);
          if (response.status == 200) {
               this.$message({
                    type: "success",
                    message: "删除成功!",
                    duration:1000,
                  });
              //删除完后需要刷新页面
              this.getBooks();
            }else{
              alert("删除失败");
            }
          }).catch((error) => {
              console.log(error);
            });
        })
      },
      //当表格复选框选项发生变化的时候触发
      handleSelectionChange(selection) {
        this.multipleSelection = selection;
      },
      //更新数据 先弹窗 需要查最新数据修改 再点击更新然后会处理实际的更新
      handleUpdate(rowData){
        //先弹出更新框
        this.updateDialogVisible=true;
        //需要先拿到当前行的数据
        //第一个方法不建议，因为数据是双向绑定的，如果这边改了但是没有提交就会改变元数据
        // this.updateBookData=rowData;
        //需要断开两者连接 转成json串再转换为json对象 两个就分开了
        //也可以复制源数据 Object.assign(target, ...sources)
        //  参数： target--->目标对象
        //        source--->源对象
        //        返回值：target，即目标对象
        //this.updateBookData=JSON.parse(JSON.stringify(rowData));
        //最建议先查一下数据 通过id查询最新数据
        this.axios.get("/bookmanager-ajax/"+rowData.userId)
        .then(response=>{
          this.updateBookData=response.data;

        }).catch(error=>{
          console.log(error);
        })
      },
      handleUpdateBook(){
        console.log(this.updateBookData);
        this.updateDialogVisible=false;
        this.axios.post("/update",this.updateBookData)
        .then(response=>{
          if(response.status==200){
             //更新完需要重新刷新
            this.getBooks();
            alert("更新成功");
          }else{
            alert("更新失败");
          }
        }).catch(error=>{
          console.log(error);
        })
      },
      //取消按钮 数据重新置空
      cancel(){
        this.addDialogVisible=false;
        this.updateDialogVisible=false;
        this.addBookData={};
        this.updateBookData={};
        this.tableDataCongBiao=[];
      },
      handleCascaderChange(value){
         console.log(value);
      },
      //添加从表数据行
      insertDetialEvent() {
          this.tableDataCongBiao.push({});
        },
    },
    //页面一进来就需要进行分页
    created(){
       this.getBooks();
    }

}
</script>

<style>
  .demo-table-expand {
    font-size: 0;
  }
  .demo-table-expand label {
    width: 50px;
    color: #99a9bf;
  }
  .demo-table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 20%;
  }
  .userNameStype{
    width: 68%;
  }
  .userAddressStype{
    width: 68%;
  }
</style>
