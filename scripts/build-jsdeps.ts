const scripts = [
  "node_modules/three/build/three.min.js",
  "src/main/resources/CopyShader.js",
  "src/main/resources/FXAAShader.js",
  "src/main/resources/Stats.js",
  "src/main/resources/OrbitControls.js",
  "src/main/resources/EffectComposer.js",
  "src/main/resources/CSS3DRenderer.js",
  "src/main/resources/ClearPass.js",
  "src/main/resources/RenderPass.js",
  "src/main/resources/TexturePass.js",
  "src/main/resources/ShaderPass.js",
];

const contents = await Promise.all(scripts.map(async (path) => {
  const file = Bun.file(path);
  if (!(await file.exists())) throw new Error(`Missing JavaScript dependency: ${path}`);
  return await file.text();
}));

await Bun.write("dist/epmapviewer-jsdeps.js", contents.join("\n;\n"));
