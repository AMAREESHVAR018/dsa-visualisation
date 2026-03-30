/**
 * Merge Strings Alternately Algorithm
 * Interleaves characters from two strings
 */

export const mergeStringsAlternatelyAlgorithm = {
  key: "MergeStringsAlternately",
  label: "Merge Strings Alternately",
  category: "string",
  description:
    "Merge two strings by interleaving characters alternately from word1 and word2, handling different lengths.",
  codeLines: [
    "String result = \"\";",
    "int i = 0;",
    "while (i < word1.length() || i < word2.length()) {",
    "  if (i < word1.length()) {",
    "    result += word1.charAt(i);",
    "  }",
    "  if (i < word2.length()) {",
    "    result += word2.charAt(i);",
    "  }",
    "  i++;",
    "}",
    "return result;"
  ],
  inputSchema: [
    {
      id: "str1",
      label: "First String (word1)",
      type: "text",
      placeholder: "e.g., 'abc'",
      defaultValue: "abc"
    },
    {
      id: "str2",
      label: "Second String (word2)",
      type: "text",
      placeholder: "e.g., 'pqr'",
      defaultValue: "pqr"
    }
  ],
  createDefaultInput() {
    return { str1: "abc", str2: "pqr" };
  },
  buildTimeline({ str1 = "abc", str2 = "pqr" }) {
    const word1 = String(str1).trim();
    const word2 = String(str2).trim();
    const steps = [];
    let result = "";
    let i = 0;

    steps.push(makeStep({
      description: "Initialize: merge two strings alternately",
      why: "Start with empty result and index 0.",
      word1,
      word2,
      index: i,
      result,
      state: "init",
      tracker: { i, result: result || "(empty)" },
      codeLine: 1
    }));

    while (i < Math.max(word1.length, word2.length)) {
      if (i < word1.length) {
        result += word1[i];
        steps.push(makeStep({
          description: `Add from word1[${i}]: '${word1[i]}'`,
          why: "Append character from first string.",
          word1,
          word2,
          index: i,
          result,
          state: "add-word1",
          tracker: { i, from: "word1", char: word1[i], result },
          codeLine: 5
        }));
      }

      if (i < word2.length) {
        result += word2[i];
        steps.push(makeStep({
          description: `Add from word2[${i}]: '${word2[i]}'`,
          why: "Append character from second string.",
          word1,
          word2,
          index: i,
          result,
          state: "add-word2",
          tracker: { i, from: "word2", char: word2[i], result },
          codeLine: 8
        }));
      }

      i++;
    }

    steps.push(makeStep({
      description: `Merge complete: result = "${result}"`,
      why: `Interleaved all ${Math.max(word1.length, word2.length)} character pairs.`,
      word1,
      word2,
      index: i,
      result,
      state: "done",
      tracker: { result, length: result.length },
      codeLine: 12
    }));

    return { timeline: steps };
  }
};

function makeStep({
  description,
  why,
  word1,
  word2,
  index,
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
      string: result,
      index,
      word1,
      word2
    },
    highlightedElements: {
      state
    },
    codeLine,
    tracker,
    passCount: index
  };
}
