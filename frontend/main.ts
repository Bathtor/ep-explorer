import "@fontsource/play/400.css";
import "@fontsource/play/700.css";
import "@fortawesome/fontawesome-free/css/all.min.css";
import { init } from "@hitkeep/tracker";
import "./styles/normalize.css";
import "./styles/main.css";
import "./styles/standard.css";

import "../src/main/resources/Stats.js";

import "scalajs:main.js";

const hitkeepHost = import.meta.env.VITE_HITKEEP_HOST;
if (hitkeepHost) {
  init({ host: hitkeepHost });
}
