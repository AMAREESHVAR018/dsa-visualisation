import fs from "node:fs/promises";
import path from "node:path";
import { fileURLToPath } from "node:url";

import { algorithmRegistry } from "../algorithms/index.js";
import { StateManager } from "../engine/stateManager.js";
import { AnimationEngine } from "../engine/animationEngine.js";

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);
const mainJsPath = path.resolve(__dirname, "../main.js");

function isGraphAlgorithm(algorithm) {
  return String(algorithm.category || "").toLowerCase() === "graph";
}

function isStringAlgorithm(algorithm) {
  return String(algorithm.category || "").toLowerCase() === "string";
}

function isSortAlgorithm(algorithm) {
  return /sort/i.test(algorithm.key || "") || /sort/i.test(algorithm.label || "");
}

function isSearchAlgorithm(algorithm) {
  return /search/i.test(algorithm.key || "") || /search/i.test(algorithm.label || "");
}

function modeForStep(step) {
  return step?.stateSnapshot?.mode || "unknown";
}

function hasTracker(step) {
  return step && step.tracker && Object.keys(step.tracker).length > 0;
}

function serializeState(step) {
  try {
    return JSON.stringify({
      stateSnapshot: step?.stateSnapshot || {},
      highlightedElements: step?.highlightedElements || {}
    });
  } catch {
    return String(step?.stepNumber || "");
  }
}

function inspectAlgorithm(algorithm) {
  const result = {
    key: algorithm.key,
    label: algorithm.label,
    category: algorithm.category,
    timelineExists: false,
    timelineLength: 0,
    animationWorking: false,
    stepTracking: false,
    uiConnectedByEngine: false,
    expectedMode: null,
    modeMismatch: false,
    noVisualChange: false,
    pointerMovementCheck: null,
    graphDataCheck: null,
    issues: []
  };

  let timeline;
  try {
    const input = typeof algorithm.createDefaultInput === "function" ? algorithm.createDefaultInput() : {};
    const output = algorithm.buildTimeline(input);
    timeline = output?.timeline;
  } catch (error) {
    result.issues.push(`buildTimeline error: ${error.message}`);
    return result;
  }

  if (!Array.isArray(timeline) || timeline.length === 0) {
    result.issues.push("No timeline steps generated");
    return result;
  }

  result.timelineExists = true;
  result.timelineLength = timeline.length;
  result.animationWorking = timeline.length > 1;
  result.stepTracking = timeline.some(hasTracker);

  if (!result.animationWorking) {
    result.issues.push("Only one step; animation is effectively static");
  }
  if (!result.stepTracking) {
    result.issues.push("Tracker data missing in all steps");
  }

  const modes = new Set(timeline.map(modeForStep));
  const primaryMode = timeline[0]?.stateSnapshot?.mode;

  if (isGraphAlgorithm(algorithm)) {
    result.expectedMode = "graph";
    if (primaryMode !== "graph") {
      result.modeMismatch = true;
      result.issues.push(`Expected graph mode but got ${primaryMode || "none"}`);
    }

    const hasNodesEdges = timeline.some(s => Array.isArray(s?.stateSnapshot?.nodes) && Array.isArray(s?.stateSnapshot?.edges));
    const hasEdgeAnimation = timeline.some(s => typeof s?.highlightedElements?.currentEdge === "number");
    const hasNodeUpdate = timeline.some(s => s?.highlightedElements?.updatedNode !== undefined || s?.highlightedElements?.currentNode !== undefined);
    result.graphDataCheck = hasNodesEdges && (hasEdgeAnimation || hasNodeUpdate);

    if (!hasNodesEdges) result.issues.push("Graph data missing: nodes/edges not present");
    if (!hasEdgeAnimation) result.issues.push("Edge relaxation/traversal highlighting missing");
    if (!hasNodeUpdate) result.issues.push("Node update highlighting missing");
  }

  if (isStringAlgorithm(algorithm)) {
    result.expectedMode = "string";
    if (primaryMode !== "string") {
      result.modeMismatch = true;
      result.issues.push(`Expected string mode but got ${primaryMode || "none"}`);
    }
  }

  const category = String(algorithm.category || "").toLowerCase();

  if (category === "matrix") {
    result.expectedMode = "matrix";
    if (primaryMode !== "matrix") {
      result.modeMismatch = true;
      result.issues.push(`Expected matrix mode but got ${primaryMode || "none"}`);
    }
  } else if (category === "tree-list") {
    result.expectedMode = "tree-list";
    if (primaryMode !== "tree-list") {
      result.modeMismatch = true;
      result.issues.push(`Expected tree-list mode but got ${primaryMode || "none"}`);
    }
  } else if (!isGraphAlgorithm(algorithm) && !isStringAlgorithm(algorithm)) {
    result.expectedMode = "array";
    if (primaryMode !== "array") {
      result.modeMismatch = true;
      result.issues.push(`Expected array mode but got ${primaryMode || "none"}`);
    }
  }

  if (isSortAlgorithm(algorithm)) {
    const hasArrayValues = timeline.some(s => Array.isArray(s?.stateSnapshot?.values) || Array.isArray(s?.stateSnapshot?.array));
    const hasSortSignals = timeline.some(s => (s?.highlightedElements?.sorted || []).length > 0 || (s?.highlightedElements?.compare || []).length > 0 || (s?.highlightedElements?.swapped || []).length > 0);
    if (!hasArrayValues) result.issues.push("Sorting visualization missing array values");
    if (!hasSortSignals) result.issues.push("Sorting animation missing compare/swap/sorted highlights");
  }

  if (isSearchAlgorithm(algorithm)) {
    const pointerSeries = timeline.map(s => {
      const compare = s?.highlightedElements?.compare || [];
      return compare.length > 0 ? compare[0] : -1;
    });
    const moved = pointerSeries.some((x, i) => i > 0 && x !== pointerSeries[i - 1] && x >= 0);
    result.pointerMovementCheck = moved;
    if (!moved) result.issues.push("Search pointer movement not visible in highlight data");
  }

  const uniqueStateCount = new Set(timeline.map(serializeState)).size;
  result.noVisualChange = uniqueStateCount <= 1;
  if (result.noVisualChange) {
    result.issues.push("State snapshot does not change across steps");
  }

  const sm = new StateManager();
  sm.setTimeline(timeline, { algorithmKey: algorithm.key });
  const engine = new AnimationEngine(sm);

  engine.stepForward();
  const movedAfterStep = sm.snapshot().index > 0 || timeline.length === 1;
  engine.stepBackward();
  const movedBack = sm.snapshot().index === 0;
  engine.setSpeed(250);
  const speedApplied = engine.speed === 250;

  result.uiConnectedByEngine = movedAfterStep && movedBack && speedApplied;
  if (!result.uiConnectedByEngine) {
    result.issues.push("Engine step/speed behavior failed in simulation");
  }

  if (modes.size > 1 && !isGraphAlgorithm(algorithm) && !isStringAlgorithm(algorithm)) {
    result.issues.push(`Mixed modes detected in one timeline: ${Array.from(modes).join(", ")}`);
  }

  return result;
}

async function inspectUiWiring() {
  const source = await fs.readFile(mainJsPath, "utf8");
  const checks = {
    hasPlayClick: source.includes("ui.playButton.addEventListener(\"click\"") && source.includes("engine.play()"),
    hasPauseClick: source.includes("ui.pauseButton.addEventListener(\"click\"") && source.includes("engine.pause()"),
    hasStepForwardClick: source.includes("ui.forwardButton.addEventListener(\"click\"") && source.includes("engine.stepForward()"),
    hasStepBackClick: source.includes("ui.backButton.addEventListener(\"click\"") && source.includes("engine.stepBackward()"),
    hasSpeedControl: source.includes("ui.speed.addEventListener(\"input\"") && source.includes("engine.setSpeed(speed)"),
    hasScrubberControl: source.includes("ui.scrubber.addEventListener(\"input\"") && source.includes("stateManager.jumpTo"),
    hasModeSwitch: source.includes("configureVisualizationMode(algorithm.category)") && source.includes("visualStage.dataset.mode")
  };

  return checks;
}

async function run() {
  const uiChecks = await inspectUiWiring();
  const results = algorithmRegistry.map(inspectAlgorithm);

  const broken = results.filter(r => r.issues.length > 0);
  const ok = results.filter(r => r.issues.length === 0);

  const summary = {
    totalAlgorithms: results.length,
    fullyPassing: ok.length,
    withIssues: broken.length,
    uiChecks
  };

  console.log("=== QA SUMMARY ===");
  console.log(JSON.stringify(summary, null, 2));
  console.log("\n=== PER ALGORITHM REPORT ===");

  for (const r of results) {
    console.log(`\nAlgorithm: ${r.key}`);
    console.log(`- Visualization exists: ${r.timelineExists ? "PASS" : "FAIL"}`);
    console.log(`- Animation working: ${r.animationWorking ? "PASS" : "FAIL"}`);
    console.log(`- Step tracking: ${r.stepTracking ? "PASS" : "FAIL"}`);
    console.log(`- UI engine simulation: ${r.uiConnectedByEngine ? "PASS" : "FAIL"}`);
    console.log(`- Expected mode: ${r.expectedMode || "n/a"}`);
    console.log(`- Mode mismatch: ${r.modeMismatch ? "YES" : "NO"}`);
    if (r.pointerMovementCheck !== null) {
      console.log(`- Search pointer movement: ${r.pointerMovementCheck ? "PASS" : "FAIL"}`);
    }
    if (r.graphDataCheck !== null) {
      console.log(`- Graph animation data: ${r.graphDataCheck ? "PASS" : "FAIL"}`);
    }
    console.log(`- Step count: ${r.timelineLength}`);
    if (r.issues.length > 0) {
      for (const issue of r.issues) {
        console.log(`  ISSUE: ${issue}`);
      }
    }
  }

  const outputPath = path.resolve(__dirname, "../qa-report.json");
  await fs.writeFile(outputPath, JSON.stringify({ summary, results }, null, 2), "utf8");
  console.log(`\nSaved machine report to: ${outputPath}`);

  if (broken.length > 0) {
    process.exitCode = 2;
  }
}

run().catch(error => {
  console.error(error);
  process.exit(1);
});
