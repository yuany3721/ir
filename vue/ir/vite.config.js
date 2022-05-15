import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";
import AutoImport from "unplugin-auto-import/vite";
import Components from "unplugin-vue-components/vite";
import { ElementPlusResolver } from "unplugin-vue-components/resolvers";

// https://vitejs.dev/config/
export default defineConfig({
    plugins: [
        vue(),
        AutoImport({
            resolvers: [ElementPlusResolver()],
        }),
        Components({
            resolvers: [ElementPlusResolver()],
        }),
    ],
    server: {
        host: "localhost",
        port: 8080, // 端口号
        https: false,
        proxy: {
            "/proxy": {
                target: "http://localhost:8082", //代理接口
                changeOrigin: true,
                rewrite: (path) => path.replace(/^\/proxy/, ""),
            },
        },
    },
});
