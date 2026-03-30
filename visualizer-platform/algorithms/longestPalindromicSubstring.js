/**
 * Longest Palindromic Substring Algorithm
 * Expands around centers to find longest palindromic substring
 */

export const longestPalindromicSubstringAlgorithm = {
  key: "LongestPalindromicSubstring",
  label: "Longest Palindromic Substring",
  category: "string",
  description:
    "Expand-around-center technique to find the longest substring that reads the same forwards and backwards.",
  codeLines: [
    "String longest = \"\";",
    "for (int i = 0; i < s.length(); i++) {",
    "  // Odd length palindromes (single center)",
    "  String p1 = expandAroundCenter(s, i, i);",
    "  // Even length palindromes (two centers)",
    "  String p2 = expandAroundCenter(s, i, i + 1);",
    "  String p = p1.length() > p2.length() ? p1 : p2;",
    "  if (p.length() > longest.length()) {",
    "    longest = p;",
    "  }",
    "}",
    "return longest;"
  ],
  inputSchema: [
    {
      id: "str",
      label: "String to Search",
      type: "text",
      placeholder: "e.g., 'babad'",
      defaultValue: "babad"
    }
  ],
  createDefaultInput() {
    return { str: "babad" };
  },
  buildTimeline({ str = "babad" }) {
    const s = String(str).trim();
    const steps = [];
    let longest = "";
    let longestStart = 0;
    let longestEnd = 0;

    steps.push(makeStep({
      description: "Initialize search with empty palindrome",
      why: "We'll check every position as a potential center and expand outward.",
      string: s,
      center: -1,
      start: -1,
      end: -1,
      longest,
      tracker: { checked: 0, longest: longest || "(empty)" },
      codeLine: 1
    }));

    for (let i = 0; i < s.length; i++) {
      // Odd-length palindrome (single character center)
      let left = i;
      let right = i;
      while (left >= 0 && right < s.length && s[left] === s[right]) {
        left--;
        right++;
      }

      const p1 = s.substring(left + 1, right);
      if (p1.length > longest.length) {
        longest = p1;
        longestStart = left + 1;
        longestEnd = right;
      }

      steps.push(makeStep({
        description: `Expand odd-length from center ${i}: found "${p1}"`,
        why: p1.length > 0 ? `Characters match symmetrically around ${i}` : "No match",
        string: s,
        center: i,
        start: left + 1,
        end: right,
        longest,
        tracker: { center: i, type: "odd", found: p1 || "(none)" },
        codeLine: 4
      }));

      // Even-length palindrome (two character center)
      left = i;
      right = i + 1;
      while (left >= 0 && right < s.length && s[left] === s[right]) {
        left--;
        right++;
      }

      const p2 = s.substring(left + 1, right);
      if (p2.length > longest.length) {
        longest = p2;
        longestStart = left + 1;
        longestEnd = right;
      }

      steps.push(makeStep({
        description: `Expand even-length from center ${i}-${i + 1}: found "${p2}"`,
        why: p2.length > 0 ? `Characters match between ${i} and ${i + 1}` : "No match",
        string: s,
        center: i,
        start: left + 1,
        end: right,
        longest,
        tracker: { center: `${i}-${i + 1}`, type: "even", found: p2 || "(none)" },
        codeLine: 5
      }));
    }

    steps.push(makeStep({
      description: `Search complete. Longest palindrome: "${longest}"`,
      why: `Checked all ${s.length} positions. Found substring from ${longestStart} to ${longestEnd - 1}`,
      string: s,
      center: -1,
      start: longestStart,
      end: longestEnd,
      longest,
      tracker: { result: longest, length: longest.length },
      codeLine: 12
    }));

    return { timeline: steps };
  }
};

function makeStep({
  description,
  why,
  string,
  center,
  start,
  end,
  longest,
  tracker,
  codeLine
}) {
  return {
    description,
    why,
    stateSnapshot: {
      mode: "string",
      string,
      center,
      start,
      end,
      longest
    },
    highlightedElements: {
      highlightRange: start >= 0 && end > start ? [start, end - 1] : []
    },
    codeLine,
    tracker,
    passCount: center + 1
  };
}
