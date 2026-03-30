/**
 * Replace Zero With Five Algorithm
 * Replaces all zeros in an integer with the digit 5
 */

export const replaceZeroWithFiveInIntegerAlgorithm = {
  key: "ReplaceZeroWithFiveInInteger",
  label: "Replace Zero With Five In Integer",
  category: "string",
  description:
    "Convert integer to string, replace all '0' digits with '5', and convert back to integer.",
  codeLines: [
    "String s = String.valueOf(num);",
    "s = s.replace('0', '5');",
    "return Integer.parseInt(s);"
  ],
  inputSchema: [
    {
      id: "num",
      label: "Integer Number",
      type: "text",
      placeholder: "e.g., '10203'",
      defaultValue: "10203"
    }
  ],
  createDefaultInput() {
    return { num: "10203" };
  },
  buildTimeline({ num = "10203" }) {
    const s = String(num).trim();
    const steps = [];

    steps.push(makeStep({
      description: `Convert number to string: "${s}"`,
      why: "Convert integer to string representation to process digit by digit.",
      string: s,
      result: s,
      processedCount: 0,
      state: "convert",
      tracker: { input: num, asString: `"${s}"` },
      codeLine: 1
    }));

    let result = "";
    for (let i = 0; i < s.length; i++) {
      const char = s[i];
      const newChar = char === "0" ? "5" : char;
      result += newChar;

      steps.push(makeStep({
        description: `Process digit[${i}]: '${char}' ${char === "0" ? "→ '5'" : "(unchanged)"}`,
        why: char === "0" ? "Replace zero with five." : "Keep non-zero digit.",
        string: s,
        result,
        processedCount: i + 1,
        state: "replacing",
        tracker: { index: i, original: char, replaced: newChar, result },
        codeLine: 2
      }));
    }

    const finalNum = Number(result);
    steps.push(makeStep({
      description: `Conversion complete: "${result}" = ${finalNum}`,
      why: `All zeros replaced with fives. Convert back to integer: ${finalNum}`,
      string: s,
      result,
      processedCount: s.length,
      state: "done",
      tracker: { original: num, replaced: result, asInteger: finalNum },
      codeLine: 3
    }));

    return { timeline: steps };
  }
};

function makeStep({
  description,
  why,
  string,
  result,
  processedCount,
  state,
  tracker,
  codeLine
}) {
  return {
    description,
    why,
    stateSnapshot: {
      mode: "string",
      string,
      result
    },
    highlightedElements: {
      state
    },
    codeLine,
    tracker,
    passCount: processedCount
  };
}
