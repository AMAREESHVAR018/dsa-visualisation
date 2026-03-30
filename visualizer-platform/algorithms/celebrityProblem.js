/**
 * Celebrity Problem Algorithm
 * Find the person who is known by everyone but knows nobody
 */

export const celebrityProblemAlgorithm = {
  key: "CelebrityProblem",
  label: "Celebrity Problem",
  category: "array",
  description:
    "Find celebrity: person known by all but knows nobody. Uses two-pointer approach.",
  codeLines: [
    "int n = know.length;",
    "int candidate = 0;",
    "for (int i = 1; i < n; i++) {",
    "  if (know[candidate][i]) candidate = i;",
    "}",
    "for (int i = 0; i < n; i++) {",
    "  if (i != candidate && (know[candidate][i] || !know[i][candidate]))",
    "    return -1;",
    "}",
    "return candidate;"
  ],
  inputSchema: [
    {
      id: "size",
      label: "Number of Persons (2-10)",
      type: "number",
      placeholder: "e.g., 5",
      defaultValue: 5
    }
  ],
  createDefaultInput() {
    return { size: 5 };
  },
  buildTimeline({ size = 5 }) {
    const n = Math.min(Math.max(parseInt(size), 2), 10);
    // Create mock knowledge matrix
    const know = Array(n)
      .fill(0)
      .map(() => Array(n).fill(0));

    // Person (n-1) is celebrity for demo
    for (let i = 0; i < n - 1; i++) {
      know[i][n - 1] = 1; // Everyone knows person (n-1)
    }

    const steps = [];
    let candidate = 0;

    steps.push(makeStep({
      description: `Find celebrity among ${n} persons using two-pointer technique`,
      why: "Start with candidate=0, eliminate non-celebrities by checking relationships",
      know,
      candidate: 0,
      checked: new Set(),
      state: "init",
      tracker: { n, candidate: 0 },
      codeLine: 1
    }));

    // Elimination phase
    const checkedSet = new Set();
    for (let i = 1; i < n; i++) {
      const knowsCandidate = know[candidate][i];

      steps.push(makeStep({
        description: `Check: know[${candidate}][${i}] = ${knowsCandidate ? 1 : 0}`,
        why: knowsCandidate
          ? `Person ${candidate} knows ${i}, so ${candidate} is not celebrity → ${i} is new candidate`
          : `Person ${candidate} doesn't know ${i}, so ${candidate} might be celebrity`,
        know,
        candidate: knowsCandidate ? i : candidate,
        checked: checkedSet,
        state: "eliminate",
        tracker: {
          knows: `[${candidate}][${i}]`,
          value: knowsCandidate,
          newCandidate: knowsCandidate ? i : candidate
        },
        codeLine: 3
      }));

      if (knowsCandidate) {
        candidate = i;
      }
      checkedSet.add(i);
    }

    steps.push(makeStep({
      description: `Candidate selected: person ${candidate}`,
      why: "Person not eliminated in first pass is likely celebrity",
      know,
      candidate,
      checked: checkedSet,
      state: "candidate-found",
      tracker: { candidate },
      codeLine: 5
    }));

    // Verification phase
    let isValid = true;
    const failedChecks = [];

    steps.push(makeStep({
      description: `Verify: check if person ${candidate} is actual celebrity`,
      why: "Celebrity must be known by all and know nobody",
      know,
      candidate,
      checked: checkedSet,
      state: "verify-start",
      tracker: { phase: "verification" },
      codeLine: 6
    }));

    for (let i = 0; i < n; i++) {
      if (i === candidate) continue;

      const knowsMe = know[i][candidate]; // i knows candidate?
      const iKnowThem = know[candidate][i]; // candidate knows i?

      const isValid_i = knowsMe && !iKnowThem;

      if (!isValid_i) {
        isValid = false;
        failedChecks.push(i);
      }

      steps.push(makeStep({
        description: `Check ${i}↔${candidate}: knows=${knowsMe}, unknown=${!iKnowThem}`,
        why: isValid_i
          ? `Person ${i} knows ${candidate} and ${candidate} doesn't know ${i} ✓`
          : `Relationship invalid: ${failedChecks.length} failures found`,
        know,
        candidate,
        checked: new Set([...failedChecks]),
        state: isValid_i ? "verify-pass" : "verify-fail",
        tracker: {
          person: i,
          knowsCandidate: knowsMe ? "✓" : "✗",
          candidateKnowsThem: iKnowThem ? "✗" : "✓",
          valid: isValid_i
        },
        codeLine: 7
      }));
    }

    const result = isValid ? candidate : -1;

    steps.push(makeStep({
      description: result === -1 ? "No celebrity found" : `Celebrity is person ${result}`,
      why: result === -1
        ? "No person satisfies celebrity properties"
        : `Person ${result} is known by all and knows nobody`,
      know,
      candidate: result,
      checked: new Set(),
      state: "done",
      tracker: { result: result === -1 ? "NONE" : result },
      codeLine: 10
    }));

    return { timeline: steps };
  }
};

function makeStep({
  description,
  why,
  know,
  candidate,
  checked,
  state,
  tracker,
  codeLine
}) {
  const values = know.map(row => row.reduce((sum, cell) => sum + cell, 0));
  const checkedIndices = Array.from(checked);

  return {
    description,
    why,
    stateSnapshot: {
      mode: "array",
      values,
      knowMatrix: know,
      candidate,
      checked: checkedIndices,
      state
    },
    highlightedElements: {
      compare: candidate >= 0 ? [candidate] : [],
      sorted: checkedIndices
    },
    codeLine,
    tracker,
    passCount: checked.size
  };
}
