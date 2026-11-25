import { defineConfig } from "vite";
import { resolve } from "node:path";

export default defineConfig({
    build: {
        rollupOptions: {
            input: {
                index: resolve(process.cwd(), "index.html"),
                login: resolve(process.cwd(), "login.html"),
                listar: resolve(process.cwd(), "pages/listar.html"),
                editar: resolve(process.cwd(), "pages/editar.html")
            }
        }
    }
});