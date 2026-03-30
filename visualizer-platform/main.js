import { AnimationEngine } from "./engine/animationEngine.js";
import { getTemplateByClassName, srcTemplates } from "./algorithms/srcTemplates.js";
import { StateManager } from "./engine/stateManager.js";
import { algorithmRegistry, getAlgorithmByKey } from "./algorithms/index.js";
import { RendererHub } from "./renderers/index.js";
import { highlightCodeLine, renderCodePanel } from "./ui/codeSync.js";
import { renderInputForm } from "./ui/inputPanel.js";
import { renderJudge0Panel } from "./ui/judge0Panel.js";
import { renderTracker } from "./ui/trackerPanel.js";
import { Gamification } from "./ui/gamification.js";

const stateManager = new StateManager();
const renderer = new RendererHub(document.getElementById("visualStage"));

const engine = new AnimationEngine(stateManager, {
  onComplete(snapshot) {
    const algorithmKey = snapshot.meta.algorithmKey;
    if (algorithmKey && snapshot.total > 0) {
      gamification.markCompletion(algorithmKey);
    }
    refreshPlayPauseLabel();
  }
});

const gamification = new Gamification(
  document.getElementById("achievementText"),
  document.getElementById("xpText")
);

const ui = {
  algorithmList: document.getElementById("algorithmList"),
  search: document.getElementById("algorithmSearch"),
  algorithmTitle: document.getElementById("algorithmTitle"),
  algorithmDesc: document.getElementById("algorithmDescription"),
  codePanel: document.getElementById("codePanel"),
  inputPanel: document.getElementById("inputPanel"),
  templateSelect: document.getElementById("srcTemplateSelect"),
  loadTemplateButton: document.getElementById("loadTemplateButton"),
  trackerPanel: document.getElementById("trackerPanel"),
  whyPanel: document.getElementById("whyPanel"),
  stepCaption: document.getElementById("stepCaption"),
  timelineCounter: document.getElementById("timelineCounter"),
  passCounter: document.getElementById("passCounter"),
  speed: document.getElementById("speedControl"),
  speedLabel: document.getElementById("speedLabel"),
  scrubber: document.getElementById("timelineScrubber"),
  scrubberLabel: document.getElementById("scrubberLabel"),
  themeToggle: document.getElementById("themeToggle"),
  playButton: document.getElementById("playButton"),
  pauseButton: document.getElementById("pauseButton"),
  forwardButton: document.getElementById("forwardButton"),
  backButton: document.getElementById("backButton"),
  resetButton: document.getElementById("resetButton"),
  judge0Panel: document.getElementById("judge0Panel")
};

const appState = {
  selectedAlgorithm: getInitialAlgorithm(),
  filteredAlgorithms: [...algorithmRegistry],
  inputValues: {}
};

init();

function init() {
  appState.inputValues = appState.selectedAlgorithm.createDefaultInput();
  window.dsaVisualizer = {
    stateManager,
    engine,
    get timeline() {
      return stateManager.timeline;
    }
  };
  hydrateTheme();
  wireControls();
  wireTemplatePicker();
  renderJudge0Panel(ui.judge0Panel, {
    endpoint: "/api/judge0/execute"
  });
  renderSidebar();
  mountAlgorithm(appState.selectedAlgorithm.key, { pushUrl: false });
  stateManager.subscribe(onStepChanged);
}

function wireControls() {
  ui.search.addEventListener("input", () => {
    const query = ui.search.value.trim().toLowerCase();
    appState.filteredAlgorithms = algorithmRegistry.filter(algorithm => {
      return algorithm.label.toLowerCase().includes(query) || algorithm.key.toLowerCase().includes(query);
    });
    renderSidebar();
  });

  ui.playButton.addEventListener("click", () => {
    engine.play();
    refreshPlayPauseLabel();
  });

  ui.pauseButton.addEventListener("click", () => {
    engine.pause();
    refreshPlayPauseLabel();
  });

  ui.forwardButton.addEventListener("click", () => {
    engine.stepForward();
    refreshPlayPauseLabel();
  });

  ui.backButton.addEventListener("click", () => {
    engine.stepBackward();
    refreshPlayPauseLabel();
  });

  ui.resetButton.addEventListener("click", () => {
    engine.reset();
    refreshPlayPauseLabel();
  });

  ui.speed.addEventListener("input", () => {
    const speed = Number(ui.speed.value);
    ui.speedLabel.textContent = `${speed} ms`;
    engine.setSpeed(speed);
  });

  ui.scrubber.addEventListener("input", () => {
    engine.pause();
    stateManager.jumpTo(Number(ui.scrubber.value));
    refreshPlayPauseLabel();
  });

  ui.themeToggle.addEventListener("click", () => {
    const dark = document.body.classList.toggle("dark-mode");
    localStorage.setItem("vizDarkMode", dark ? "1" : "0");
  });

  window.addEventListener("keydown", event => {
    if (["INPUT", "TEXTAREA"].includes(document.activeElement?.tagName)) return;

    if (event.code === "Space") {
      event.preventDefault();
      engine.toggle();
      refreshPlayPauseLabel();
      return;
    }

    if (event.code === "ArrowRight") {
      event.preventDefault();
      engine.stepForward();
      refreshPlayPauseLabel();
      return;
    }

    if (event.code === "ArrowLeft") {
      event.preventDefault();
      engine.stepBackward();
      refreshPlayPauseLabel();
      return;
    }

    if (event.key.toLowerCase() === "r") {
      event.preventDefault();
      engine.reset();
      refreshPlayPauseLabel();
    }
  });
}

function wireTemplatePicker() {
  ui.templateSelect.innerHTML = "";

  srcTemplates.forEach(template => {
    const option = document.createElement("option");
    option.value = template.className;
    option.textContent = template.label;
    ui.templateSelect.appendChild(option);
  });

  const initial = srcTemplates.find(template => template.algorithmKey === appState.selectedAlgorithm.key);
  if (initial) ui.templateSelect.value = initial.className;

  ui.loadTemplateButton.addEventListener("click", () => {
    const className = ui.templateSelect.value;
    const template = getTemplateByClassName(className);
    if (!template) return;

    mountAlgorithm(template.algorithmKey, { pushUrl: true });
    appState.inputValues = { ...template.inputValues };
    renderInputUI(appState.selectedAlgorithm);
    rebuildTimeline();
  });
}

function renderSidebar() {
  ui.algorithmList.innerHTML = "";

  appState.filteredAlgorithms.forEach(algorithm => {
    const button = document.createElement("button");
    button.type = "button";
    button.className = "algorithm-item";
    if (algorithm.key === appState.selectedAlgorithm.key) {
      button.classList.add("selected");
    }

    button.innerHTML = `
      <span class="algo-label">${algorithm.label}</span>
      <span class="algo-category">${algorithm.category}</span>
    `;

    button.addEventListener("click", () => {
      mountAlgorithm(algorithm.key, { pushUrl: true });
    });

    ui.algorithmList.appendChild(button);
  });
}

function mountAlgorithm(algorithmKey, options = { pushUrl: true }) {
  engine.pause();

  const algorithm = getAlgorithmByKey(algorithmKey);
  appState.selectedAlgorithm = algorithm;
  appState.inputValues = algorithm.createDefaultInput();

  ui.algorithmTitle.textContent = algorithm.label;
  ui.algorithmDesc.textContent = algorithm.description;

  renderCodePanel(ui.codePanel, algorithm.codeLines);
  renderInputUI(algorithm);
  syncTemplatePicker(algorithm.key);
  configureVisualizationMode(algorithm.category);

  rebuildTimeline();
  renderSidebar();

  if (options.pushUrl) {
    const url = new URL(window.location.href);
    url.searchParams.set("algo", algorithm.key);
    window.history.replaceState({}, "", url);
  }
}

/**
 * SMART VISUALIZATION MODE
 * Switches between graph visualization and execution visualization
 * based on algorithm type (category)
 */
function configureVisualizationMode(algorithmCategory) {
  const visualStage = document.getElementById("visualStage");
  
  // Determine if this is a graph algorithm
  const isGraphAlgorithm = ["graph", "tree"].includes(algorithmCategory?.toLowerCase());
  
  // Set data attribute for CSS hook
  visualStage.dataset.mode = algorithmCategory || "array";
  
  // For graph algorithms, ensure SVG can render
  // For others, ensure array/matrix visualization renders properly
  if (!isGraphAlgorithm) {
    visualStage.style.justifyContent = "flex-start";
    visualStage.style.alignItems = "flex-start";
  } else {
    visualStage.style.justifyContent = "center";
    visualStage.style.alignItems = "center";
  }
}

function syncTemplatePicker(algorithmKey) {
  if (!ui.templateSelect) return;
  const match = srcTemplates.find(template => template.algorithmKey === algorithmKey);
  if (match) ui.templateSelect.value = match.className;
}

function renderInputUI(algorithm) {
  renderInputForm(
    ui.inputPanel,
    algorithm.inputSchema,
    appState.inputValues,
    (id, value) => {
      appState.inputValues[id] = value;
    },
    () => rebuildTimeline(),
    () => {
      appState.inputValues = algorithm.createDefaultInput();
      renderInputUI(algorithm);
      rebuildTimeline();
    }
  );
}

function rebuildTimeline() {
  const { timeline } = appState.selectedAlgorithm.buildTimeline(appState.inputValues);
  stateManager.setTimeline(timeline, { algorithmKey: appState.selectedAlgorithm.key });
  refreshPlayPauseLabel();
}

function onStepChanged(snapshot) {
  const step = snapshot.step;
  if (!step) {
    ui.stepCaption.textContent = "No steps generated.";
    syncScrubber(snapshot);
    return;
  }

  renderer.render(step);
  renderTracker(ui.trackerPanel, step.tracker);
  highlightCodeLine(ui.codePanel, step.codeLine);

  ui.stepCaption.textContent = `${step.stepNumber}. ${step.description}`;
  ui.whyPanel.textContent = step.why || "No explanation for this step.";
  ui.timelineCounter.textContent = `Step ${snapshot.index + 1} / ${snapshot.total}`;
  ui.passCounter.textContent = `Pass: ${step.passCount || 0}`;
  syncScrubber(snapshot);
}

function refreshPlayPauseLabel() {
  const playing = engine.isPlaying();
  ui.playButton.disabled = playing;
  ui.pauseButton.disabled = !playing;
}

function hydrateTheme() {
  const dark = localStorage.getItem("vizDarkMode") === "1";
  if (dark) document.body.classList.add("dark-mode");
}

function syncScrubber(snapshot) {
  const total = Math.max(1, snapshot.total || 1);
  ui.scrubber.max = String(total - 1);
  ui.scrubber.value = String(snapshot.index || 0);
  ui.scrubberLabel.textContent = `Step ${snapshot.index + 1}`;
}

function getInitialAlgorithm() {
  const params = new URLSearchParams(window.location.search);
  const algoParam = params.get("algo");
  return getAlgorithmByKey(algoParam || "bubbleSort");
}
