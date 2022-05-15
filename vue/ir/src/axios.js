import axios from "axios";
import qs from "qs";

// axios.defaults.baseURL = "/proxy";
axios.defaults.baseURL = "http://api.yuany3721.top/ir";
//设置超时
axios.defaults.timeout = 10000;

axios.interceptors.request.use(
    (config) => {
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

axios.interceptors.response.use(
    (response) => {
        if (response.status == 200) {
            return Promise.resolve(response);
        } else {
            return Promise.reject(response);
        }
    },
    (error) => {
        console.log("请求异常", error);
    }
);

export default {
    post(url, data) {
        return new Promise((resolve, reject) => {
            axios({
                method: "post",
                url,
                data: qs.stringify(data),
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded",
                },
            })
                .then((res) => {
                    resolve(res.data);
                })
                .catch((err) => {
                    reject(err);
                });
        });
    },
    post_json(url, data) {
        return new Promise((resolve, reject) => {
            axios({
                method: "post",
                url,
                data: JSON.stringify(data),
                headers: {
                    "Content-Type": "application/json;charset=UTF-8;",
                },
            })
                .then((res) => {
                    resolve(res.data);
                })
                .catch((err) => {
                    reject(err);
                });
        });
    },
    get(url) {
        return new Promise((resolve, reject) => {
            axios({
                method: "get",
                url,
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded",
                },
            })
                .then((res) => {
                    resolve(res.data);
                })
                .catch((err) => {
                    reject(err);
                });
        });
    },
};
