<template>
    <el-card class="ir">
        <el-checkbox v-model="isBool" border>高级检索</el-checkbox>
        <br /><br />
        <el-select v-model="select" slot="prepend" placeholder="请选择">
            <el-option label="标题检索" value="title"></el-option>
            <el-option label="全文检索" value="passage"></el-option>
            <el-option label="作者检索" value="author"></el-option>
        </el-select>
        <div v-show="isBool">
            <el-form :model="form">
                <el-form-item label="包含以下全部的关键词：">
                    <el-input v-model="form.have"></el-input>
                </el-form-item>
                <el-form-item label="包含以下任意一个关键词：">
                    <el-input v-model="form.unsure"></el-input>
                </el-form-item>
                <el-form-item label="不包括以下关键词：">
                    <el-input v-model="form.nothave"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="search" :loading="isSearching">搜索</el-button>
                    <el-button @click="isBool = false">取消</el-button>
                </el-form-item>
            </el-form>
        </div>
        <div v-show="!isBool">
            <br />
            <el-input placeholder="请输入内容" v-model="form.unsure" class="input-with-select"> </el-input>
            <br />
            <br />
            <el-button type="primary" @click="search" :loading="isSearching">搜索</el-button>
        </div>
        <hr />
        <div class="searched" v-show="searched">
            <ul>
                <li v-for="(item, index) in results" :key="index">
                    {{ index + 1 }}. {{ item.title }}
                    <div class="seperation-bar"></div>
                    作者：
                    <span v-for="(author, index) in item.authors" :key="index">
                        {{ author }}
                    </span>
                    <br />
                    发表于： {{ item.publication }}
                    <span class="show" @click="show(item)">点击查看文章</span>
                </li>
            </ul>
            <br />
            <el-drawer v-model="drawer" :title="drawerTitle" direction="rtl" size="50%">
                <iframe v-show="isiframe" name="showHere" scrolling="auto" :src="iframeURL" class="iframe"> </iframe>
            </el-drawer>
            <div v-show="noResult">没有找到对应结果。</div>
            <hr />
        </div>
        <div class="point">
            当前可搜索文章如下：
            <ul>
                <li v-for="(doc, index) in docs" :key="index">
                    {{ index + 1 }}.{{ doc.title }} <br />
                    <b>作者：</b>{{ doc.author }} <br />
                    <b>刊物：</b>{{ doc.publication }} <br />
                    <b>关键词：</b>{{ doc.word }}
                </li>
            </ul>
        </div>
        <hr />
        <div class="handlePassages">
            <el-button type="primary" round @click="refresh" :disabled="true">刷新顺排档</el-button>
            <div class="upload">
                <el-upload
                    ref="upload"
                    :auto-upload="false"
                    drag
                    :http-request="uploadRequest"
                    :file-list="fileList"
                    accept=".txt"
                    action="customize"
                    :disabled="true"
                >
                    <i class="el-icon-upload"></i>
                    <div class="el-upload__text">
                        将文件拖到此处，或
                        <em>点击上传</em>
                        <br />
                        （此功能暂时关闭）
                    </div>
                </el-upload>
                <el-button type="primary" @click="submitUpload" :disabled="true">确定上传</el-button>
            </div>
        </div>
    </el-card>
</template>

<script type="text/javascript">
export default {
    name: "InformationRetrieval",
};
</script>

<script setup>
import { reactive, getCurrentInstance, onMounted, toRefs, computed } from "vue";
const { proxy } = getCurrentInstance();

const data = reactive({
    fileList: [],
    select: "title",
    isSearching: false,
    results: [],
    searched: false,
    isBool: false,
    isiframe: false,
    iframeURL: "",
    docs: [],
    drawer: false,
    drawerTitle: "",
});

const form = reactive({
    have: "",
    unsure: "",
    nothave: "",
});

const noResult = computed(() => {
    return data.results.length == 0;
});

function getAll() {
    proxy.$http
        .get("/getAllDoc")
        .then((res) => {
            // console.log(res.data);
            res.data.forEach((doc) => {
                const words = doc.word.split("{");
                //   console.log(doc.word.split("{"));
                let tempword = "";
                for (let i = 1; i < words.length; i++) {
                    tempword += " " + words[i].split(",")[0];
                }
                //   console.log(tempword);
                doc.word = tempword;
            });
            data.docs = res.data;
        })
        .catch(function () {
            console.log("%c error in getAllDocs", "color:red;");
        });
}

function refresh() {
    console.log("refresh doc");
}

function uploadRequest(params) {
    console.log("uploadFile", params);
}

function submitUpload() {
    console.log("submit file");
}

function search() {
    data.isiframe = false;
    data.isSearching = true;
    proxy.$http
        .post("/search", {
            type: data.select,
            have: form.have,
            unsure: form.unsure,
            nothave: form.nothave,
        })
        .then((res) => {
            console.log(res);
            data.isSearching = false;
            data.results = res.data;
            data.searched = true;
        })
        .catch(function () {
            console.log("%c error in searching", "color:red;");
            data.isSearching = false;
        });
}

function show(item) {
    data.isiframe = true;
    data.iframeURL = "src/assets/files/" + item.id + ".txt";
    data.drawer = true;
    data.drawerTitle = item.title;
}

onMounted(() => {
    getAll();
});

const { fileList, select, isSearching, results, searched, isBool, isiframe, iframeURL, docs, drawer, drawerTitle } =
    toRefs(data);
</script>

<style scoped>
@media screen and (min-width: 1000px) {
    .ir {
        width: 80%;
        max-width: 900;
        margin: auto;
        text-align: left;
    }
}

@media screen and (max-width: 1000px) {
    .ir {
        width: calc((1000px - 100%) / 6 + 80%);
        max-width: 100%;
        margin: auto;
        text-align: left;
    }
}

.show {
    color: #4775ff;
    font-size: 10px;
    cursor: pointer;
    margin-left: 25px;
    display: inline-block;
}
.point {
    color: #22755f;
    font-size: 10px;
    margin-left: 15px;
}
.point ul {
    margin: 0;
}
.point ul > li {
    margin-bottom: 5px;
}
.iframe {
    align-self: center;
    width: 90%;
    margin: 0 auto;
    margin-left: 5%;
    height: 500px;
}
.handlePassages {
    max-width: 360px;
    text-align: center;
    /* margin: 10px auto; */
}
.upload {
    margin-top: 10px;
}
.searched ul {
    list-style: none;
    padding: 0;
}
.searched li {
    padding: 10px;
    margin: 5px;
    border: 1px solid #4775ff;
    border-radius: 15px;
}
.seperation-bar {
    display: block;
    width: 30px;
    height: 1px;
    margin: 10px 0 10px;
    background-color: #4775ff;
    position: relative;
}
.el-input__inner {
    min-width: 8em !important;
}
</style>
