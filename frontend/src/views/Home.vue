<template>
  <div class="home">
  <el-row>
    <el-col :span="6" :offset="2">
      <el-input placeholder="Please input" v-model="keyword"></el-input>
    </el-col>
    <el-col :span="4" :offset="1">
      <el-date-picker
        v-model="startDate"
        type="datetime"
        value-format="yyyy-MM-dd HH:mm:ss"
        placeholder="Select start date and time">
      </el-date-picker>
    </el-col>
    <el-col :span="4" :offset="1">
      <el-date-picker
        v-model="endDate"
        type="datetime"
        value-format="yyyy-MM-dd HH:mm:ss"
        placeholder="Select end date and time">
      </el-date-picker>
    </el-col>
    <el-col :span="2" :offset="1">
      <el-button type="primary" @click="search">Search</el-button>
    </el-col>
  </el-row>
  </div>
</template>

<script>

export default {
  name: 'Home', 
  data() {
    return {
      keyword: '',
      startDate: '',
      endDate: ''
    }
  },
  methods: {
    search() {
      console.log(this.keyword + '>>>>' + this.startDate + '>>>>' + this.endDate);
    },
  }
}
</script>
