/**
 * Check Palindrome String Algorithm
 * Visualizes two-pointer approach comparing characters from start and end
 */

export const checkPalindromeStringAlgorithm = {
  key: "CheckPalindromeString",
  label: "Check Palindrome String",
  category: "string",
  description:
    "Two-pointer technique to verify if a string reads the same forwards and backwards. Pointers move inward comparing characters.",
  codeLines: [
    "public static boolean isPalindrome(String s) {",
    "  if (s == null || s.length() == 0) return true;",
    "  int left = 0, right = s.length() - 1;",
    "  while (left < right) {",
    "    if (Character.toLowerCase(s.charAt(left)) !=",
    "        Character.toLowerCase(s.charAt(right))) {",
    "      return false;",
    "    }",
    "    left++;",
    "    right--;",
    "  }",
    "  return true;",
    "}"
  ],
  inputSchema: [
    {
      id: "str",
      label: "String to Check",
      type: "text",
      placeholder: "Enter string (e.g., 'racecar')",
      defaultValue: "racecar"
    }
  ],
  createDefaultInput() {
    return { str: "racecar" };
  },
  buildTimeline({ str = "racecar" }) {
    const s = String(str).trim();
    const steps = [];
    let left = 0;
    let right = s.length - 1;
    let stepNum = 0;
    let isPalindrome = true;

    // Initial state
    steps.push(makeStep({
      description: `Initialize pointers: left=0, right=${s.length - 1}`,
      why: "Set up two pointers at both ends of the string to compare inward.",
      string: s,
      left,
      right,
      comparedIndices: [],
      compared: "",
      isPalindrome: true,
      tracker: { left, right, isPalindrome: "true" },
      codeLine: 3
    }));

    // Compare characters step by step
    while (left < right) {
      const charLeft = s[left].toLowerCase();
      const charRight = s[right].toLowerCase();
      const match = charLeft === charRight;

      steps.push(makeStep({
        description: `Compare left='${s[left]}' and right='${s[right]}' at indices ${left}, ${right}`,
        why: match ? "Characters match, continue inward." : "Mismatch! String is not a palindrome.",
        string: s,
        left,
        right,
        comparedIndices: [],
        compared: match ? "match" : "mismatch",
        isPalindrome,
        tracker: {
          left,
          right,
          "left char": `'${s[left]}'`,
          "right char": `'${s[right]}'`,
          match: match ? "✓" : "✗"
        },
        codeLine: 4
      }));

      if (!match) {
        isPalindrome = false;
        steps.push(makeStep({
          description: `Mismatch found at indices ${left} and ${right}`,
          why: `'${s[left].toUpperCase()}' ≠ '${s[right].toUpperCase()}' - return false`,
          string: s,
          left,
          right,
          comparedIndices: Array.from({ length: s.length }, (_, i) => i),
          compared: "mismatch",
          isPalindrome: false,
          tracker: {
            result: "NOT palindrome",
            mismatch: `pos ${left} vs ${right}`
          },
          codeLine: 7
        }));
        break;
      }

      left++;
      right--;

      steps.push(makeStep({
        description: `Move pointers inward: left=${left}, right=${right}`,
        why: "Characters matched, move both pointers closer to the center.",
        string: s,
        left,
        right,
        comparedIndices: Array.from({ length: left }, (_, i) => i).concat(
          Array.from({ length: s.length - right - 1 }, (_, i) => s.length - 1 - i)
        ),
        compared: "",
        isPalindrome,
        tracker: {
          left,
          right,
          verified: `${left} chars matched`
        },
        codeLine: 9
      }));
    }

    // Final state
    if (isPalindrome && left >= right) {
      steps.push(makeStep({
        description: "Pointers crossed! All characters matched.",
        why: "All character pairs matched from outside to center. This is a palindrome!",
        string: s,
        left,
        right,
        comparedIndices: Array.from({ length: s.length }, (_, i) => i),
        compared: "allMatched",
        isPalindrome: true,
        tracker: {
          result: "✓ IS palindrome",
          verified: "all chars"
        },
        codeLine: 12
      }));
    }

    return {
      timeline: steps
    };
  }
};

function makeStep({
  description,
  why,
  string,
  left,
  right,
  comparedIndices,
  compared,
  isPalindrome,
  tracker,
  codeLine
}) {
  return {
    description,
    why,
    stateSnapshot: {
      mode: "string",
      string,
      left,
      right,
      comparedIndices
    },
    highlightedElements: {
      compared,
      allMatched: compared === "allMatched"
    },
    codeLine,
    tracker,
    passCount: Math.floor(string.length / 2)
  };
}
