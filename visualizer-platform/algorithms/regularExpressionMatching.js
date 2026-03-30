/**
 * Regular Expression Matching Algorithm
 * Step-by-step pattern matching visualization with '.' and '*' support
 */

export const regularExpressionMatchingAlgorithm = {
  key: "RegularExpressionMatching",
  label: "Regular Expression Matching",
  category: "string",
  description:
    "Match string against pattern with regex support: '.' matches any single char, '*' matches zero or more.",
  codeLines: [
    "// . matches any single char",
    "// * matches zero or more of preceding element",
    "boolean match(String s, String p) {",
    "  if (p.isEmpty()) return s.isEmpty();",
    "  boolean firstMatch = !s.isEmpty() && (p.charAt(0)=='.' || s.charAt(0)==p.charAt(0));",
    "  if (p.length() >= 2 && p.charAt(1) == '*') {",
    "    return match(s, p.substring(2)) || (firstMatch && match(s.substring(1), p));",
    "  }",
    "  return firstMatch && match(s.substring(1), p.substring(1));",
    "}"
  ],
  inputSchema: [
    {
      id: "s",
      label: "String to Match",
      type: "text",
      placeholder: "e.g., 'aa'",
      defaultValue: "aa"
    },
    {
      id: "p",
      label: "Pattern",
      type: "text",
      placeholder: "e.g., 'a'",
      defaultValue: "a"
    }
  ],
  createDefaultInput() {
    return { s: "aa", p: "a" };
  },
  buildTimeline({ s = "aa", p = "a" }) {
    const str = String(s).trim();
    const pattern = String(p).trim();
    const steps = [];

    steps.push(makeStep({
      description: `Start matching: string="${str}", pattern="${pattern}"`,
      why: "Try to match string against pattern with . (any char) and * (zero or more) support.",
      str,
      pattern,
      strIdx: 0,
      patIdx: 0,
      result: null,
      state: "init",
      tracker: { string: `"${str}"`, pattern: `"${pattern}"` },
      codeLine: 2
    }));

    // Simulate matching
    let sIdx = 0;
    let pIdx = 0;
    let matches = true;

    while (pIdx < pattern.length && sIdx <= str.length) {
      const pChar = pattern[pIdx];
      const sChar = sIdx < str.length ? str[sIdx] : null;

      // Handle * operator
      if (pIdx + 1 < pattern.length && pattern[pIdx + 1] === "*") {
        const prevChar = pattern[pIdx];
        steps.push(makeStep({
          description: `Found '*' operator for '${prevChar}' at position ${pIdx}`,
          why: "Can match zero or more of the preceding character.",
          str,
          pattern,
          strIdx: sIdx,
          patIdx: pIdx,
          result: null,
          state: "star-found",
          tracker: { strPos: sIdx, patPos: pIdx, operator: `${prevChar}*` },
          codeLine: 6
        }));

        // Skip the * and matching character in pattern
        pIdx += 2;

        // Try to match zero occurrences (skip this part entirely)
        steps.push(makeStep({
          description: `Try matching zero '${prevChar}' - skip to pattern[${pIdx}]`,
          why: "'*' can match zero occurrences, so try next pattern part.",
          str,
          pattern,
          strIdx: sIdx,
          patIdx: pIdx,
          result: null,
          state: "star-zero",
          tracker: { matched: 0, of: prevChar },
          codeLine: 6
        }));
      } else if (sChar !== null && (pChar === "." || pChar === sChar)) {
        // Characters match
        steps.push(makeStep({
          description: `Match: '${sChar}' = '${pChar}' at position ${sIdx}`,
          why: pChar === "." ? "'.' matches any character" : "Characters match",
          str,
          pattern,
          strIdx: sIdx,
          patIdx: pIdx,
          result: null,
          state: "match",
          tracker: { strChar: `'${sChar}'`, patChar: `'${pChar}'`, action: "advance both" },
          codeLine: 9
        }));

        sIdx++;
        pIdx++;
      } else {
        // Characters don't match
        steps.push(makeStep({
          description: `Mismatch: '${sChar || "EOF"}' ≠ '${pChar}' at string[${sIdx}], pattern[${pIdx}]`,
          why: "Characters don't match and no wildcard to handle this.",
          str,
          pattern,
          strIdx: sIdx,
          patIdx: pIdx,
          result: false,
          state: "mismatch",
          tracker: { expected: `'${pChar}'`, found: sChar ? `'${sChar}'` : "end-of-string" },
          codeLine: 9
        }));
        matches = false;
        break;
      }
    }

    // Check if pattern is fully consumed
    if (matches && pIdx !== pattern.length) {
      // Check if remaining pattern is all *
      let allStar = true;
      for (let i = pIdx; i < pattern.length; i += 2) {
        if (i + 1 >= pattern.length || pattern[i + 1] !== "*") {
          allStar = false;
          break;
        }
      }
      if (!allStar) matches = false;
    }

    const finalResult = matches && sIdx === str.length && pIdx === pattern.length;

    steps.push(makeStep({
      description: finalResult
        ? `✓ MATCH: "${str}" matches pattern "${pattern}"`
        : `✗ NO MATCH: "${str}" does not match pattern "${pattern}"`,
      why: finalResult
        ? "String fully consumed and pattern fully matched!"
        : "Pattern or string has remaining unmatched content.",
      str,
      pattern,
      strIdx: sIdx,
      patIdx: pIdx,
      result: finalResult,
      state: finalResult ? "success" : "failure",
      tracker: { result: finalResult ? "MATCH ✓" : "NO MATCH ✗" },
      codeLine: 10
    }));

    return { timeline: steps };
  }
};

function makeStep({
  description,
  why,
  str,
  pattern,
  strIdx,
  patIdx,
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
      string: str,
      pattern,
      strIdx,
      patIdx
    },
    highlightedElements: {
      state,
      result
    },
    codeLine,
    tracker,
    passCount: strIdx + patIdx
  };
}

