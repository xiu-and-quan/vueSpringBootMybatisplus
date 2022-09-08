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
      </el-form>
      <!-- 表格数据 -->
       <el-table
      :data="books"
      style="width: 100%">
       <el-table-column
        prop="userId"
        label="编号"
        width="180">
      </el-table-column>
      <el-table-column
        prop="userName"
        label="姓名"
        width="180">
      </el-table-column>
      <el-table-column
        prop="userDate"
        label="日期"
        width="180">
      </el-table-column>
      <el-table-column
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

    <!-- 内嵌添加表单 -->
    <el-dialog
      title="添加表单"
      :visible.sync="addDialogVisible"
      width="50%">
      <el-form :model="addBookData" :rules="rules" ref="ruleForm"  label-width="100px">
        <el-form-item label="姓名" prop="name">
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
        <el-form-item label="地址" prop="address">
          <el-input v-model="addBookData.address" ></el-input>
        </el-form-item>
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
        <el-form-item size="medium">
          <el-button type="primary" @click="handleUpdateBook">更新</el-button>
          <el-button type="info" @click="cancel">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <!-- 分页插件 -->
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
          },
          //会多一个id。因为需要id来更新
          updateBookData:{
              id:"",
              name:"",
              date:"",
              address:"",
          },
          //添加内嵌表单 默认false
          addDialogVisible:false,
          //更新内嵌表单 默认false
          updateDialogVisible:false,
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
        console.log(this.currentPage);
        console.log(this.pageSize);
        let postDate={
          pageIndex:this.currentPage,
          pageSize:this.pageSize,
        }
        this.axios.post("/bookManager_ajax/books",postDate)
                  .then(Response=>{
                      console.log(Response.data);
                      this.books=Response.data.records;
                      console.log(this.books)
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
                      console.log(Response.data);
                      this.books=Response.data.records;
                      console.log(this.books)
                  }).catch(error=>{
                      console.log(error);
                  });

      },
      //添加方法
      addBook(){
        this.$refs["ruleForm"].validate(valid=>{
          if(valid){
            let postdate={
              userName:this.addBookData.name,
              userDate:this.addBookData.date,
              userAddress:this.addBookData.address,
            };
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
              alert("删除成功");
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
      //更新数据 先弹窗 需要查最新数据修改 再点击更新然后会处理实际的更新
      handleUpdate(rowData){
        //先弹出更新框
        this.updateDialogVisible=true;
        console.log(rowData.id);
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
      }

    },
    //页面一进来就需要进行分页
    created(){
       this.getBooks();
    }

}
</script>

<style>

</style>
