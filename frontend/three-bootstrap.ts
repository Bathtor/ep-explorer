import * as THREECore from "three";

// The r88 add-ons and Scala.js facades use the mutable global THREE namespace.
Object.assign(globalThis, { THREE: { ...THREECore } });
