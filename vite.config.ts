import { fileURLToPath } from "node:url";
import { defineConfig } from "vite";

export default defineConfig(({ command }) => {
  const linkerOutput = command === "serve" ? "epmapviewer-fastopt" : "epmapviewer-opt";
  const scalaMain = fileURLToPath(
    new URL(`./target/out/sjs1/scala-2.13.18/epmapviewer/${linkerOutput}/main.js`, import.meta.url),
  );

  return {
    root: "frontend",
    publicDir: "../src/main/resources/WEB-INF",
    resolve: {
      alias: {
        "scalajs:main.js": scalaMain,
      },
    },
    build: {
      outDir: "../dist",
      emptyOutDir: true,
      chunkSizeWarningLimit: 2000,
      rollupOptions: {
        output: {
          entryFileNames: "epmapviewer-opt.js",
        },
      },
    },
  };
});
