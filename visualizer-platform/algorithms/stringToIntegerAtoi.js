/**
 * String To Integer (Atoi) Algorithm
 * Parses a string and converts it to an integer with validation
 */

export const stringToIntegerAtoiAlgorithm = {
  key: "StringToIntegerAtoi",
  label: "String To Integer (Atoi)",
  category: "string",
  description:
    "Parses a string character by character to extract and convert a valid integer, handling signs and whitespace.",
  codeLines: [
    "int sign = 1, result = 0;",
    "int i = 0;",
    "// Skip leading whitespace",
    "while (i < s.length() && s.charAt(i) == ' ') i++;",
    "// Check for sign",
    "if (i < s.length() && (s.charAt(i) == '+' || s.charAt(i) == '-')) {",
    "  sign = s.charAt(i) == '-' ? -1 : 1;",
    "  i++;",
    "}",
    "// Parse digits",
    "while (i < s.length() && Character.isDigit(s.charAt(i))) {",
    "  result = result * 10 + (s.charAt(i) - '0');",
    "  i++;",
    "}",
    "return result * sign;"
  ],
  inputSchema: [
    {
      id: "str",
      label: "String to Parse",
      type: "text",
      placeholder: "e.g., '  -42abc'",
      defaultValue: "  -42abc"
    }
  ],
  createDefaultInput() {
    return { str: "  -42abc" };
  },
  buildTimeline({ str = "  -42abc" }) {
    const s = String(str);
    const steps = [];
    let i = 0;
    let sign = 1;
    let result = 0;

    // Skip leading whitespace
    steps.push(makeStep({
      description: "Start parsing: initialize sign=1, result=0",
      why: "Set up variables to track sign and accumulated number.",
      string: s,
      index: i,
      sign,
      result,
      state: "init",
      tracker: { sign, result, state: "init" },
      codeLine: 1
    }));

    // Skip whitespace
    while (i < s.length && s[i] === " ") {
      steps.push(makeStep({
        description: `Skip whitespace at index ${i}`,
        why: "Leading whitespace is ignored.",
        string: s,
        index: i,
        sign,
        result,
        state: "skip-space",
        tracker: { index: i, char: `'${s[i]}'`, state: "skip-space" },
        codeLine: 4
      }));
      i++;
    }

    // Check for sign
    if (i < s.length && (s[i] === "+" || s[i] === "-")) {
      sign = s[i] === "-" ? -1 : 1;
      steps.push(makeStep({
        description: `Found sign: '${s[i]}' at index ${i}`,
        why: sign === -1 ? "Negative sign detected." : "Positive sign (or implicit).",
        string: s,
        index: i,
        sign,
        result,
        state: "sign",
        tracker: { index: i, sign: sign === -1 ? "-" : "+" },
        codeLine: 6
      }));
      i++;
    }

    // Parse digits
    while (i < s.length && /[0-9]/.test(s[i])) {
      const digit = Number(s[i]);
      result = result * 10 + digit;

      steps.push(makeStep({
        description: `Parse digit '${s[i]}' at index ${i}: result = ${result}`,
        why: `Building number: ${result - digit} * 10 + ${digit} = ${result}`,
        string: s,
        index: i,
        sign,
        result,
        state: "parse-digit",
        tracker: { index: i, digit, accumulated: result },
        codeLine: 12
      }));
      i++;
    }

    // Stop at non-digit
    if (i < s.length && !/[0-9]/.test(s[i])) {
      steps.push(makeStep({
        description: `Non-digit found at index ${i}: '${s[i]}' - stop parsing`,
        why: "Only consecutive digits are converted. Stop at first non-digit.",
        string: s,
        index: i,
        sign,
        result,
        state: "stop",
        tracker: { index: i, char: `'${s[i]}'`, state: "stop" },
        codeLine: 13
      }));
    }

    // Final result
    const finalResult = result * sign;
    steps.push(makeStep({
      description: `Conversion complete: ${sign === -1 ? "-" : ""}${result} = ${finalResult}`,
      why: `Apply sign and return final integer: ${finalResult}`,
      string: s,
      index: i,
      sign,
      result: finalResult,
      state: "done",
      tracker: { sign: sign === -1 ? "-" : "+", magnitude: result, final: finalResult },
      codeLine: 15
    }));

    return { timeline: steps };
  }
};

function makeStep({
  description,
  why,
  string,
  index,
  sign,
  result,
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
      index,
      result
    },
    highlightedElements: {
      currentIndex: index
    },
    codeLine,
    tracker,
    passCount: index
  };
}
