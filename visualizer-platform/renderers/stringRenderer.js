/**
 * String Renderer - Visualizes string algorithms with pointer movement, ranges, and DP tables
 * Used for palindrome checking, string merging, character replacement, regex matching
 */

export class StringRenderer {
  constructor(container) {
    this.container = container;
  }

  render(step) {
    this.container.innerHTML = "";

    if (!step || !step.stateSnapshot) {
      this.container.textContent = "No string to display.";
      return;
    }

    const { stateSnapshot, highlightedElements = {} } = step;
    
    // Handle different visualization types
    if (stateSnapshot.dpTable) {
      this.renderDPTable(stateSnapshot, highlightedElements);
    } else if (stateSnapshot.word1 && stateSnapshot.word2) {
      this.renderMergedStrings(stateSnapshot, highlightedElements);
    } else {
      this.renderSimpleString(stateSnapshot, highlightedElements);
    }
  }

  renderSimpleString(stateSnapshot, highlightedElements) {
    const { string = "", left, right, start, end, index, result } = stateSnapshot;

    if (!string) {
      this.container.textContent = "No string to display.";
      return;
    }

    // Create string visualization with characters
    const stringDiv = document.createElement("div");
    stringDiv.className = "string-visualization";
    stringDiv.style.cssText = `
      display: flex;
      justify-content: center;
      align-items: flex-end;
      gap: 8px;
      min-height: 200px;
      padding: 24px;
      flex-wrap: wrap;
    `;

    for (let i = 0; i < string.length; i++) {
      const char = string[i];
      const charBox = document.createElement("div");

      let classes = ["string-char"];
      let pointerLabel = "";

      // Pointer highlighting
      if (typeof left === "number" && i === left) {
        classes.push("char-left-pointer");
        pointerLabel = "L";
      }
      if (typeof right === "number" && i === right) {
        classes.push("char-right-pointer");
        pointerLabel += "R";
      }

      // Range highlighting
      if (typeof start === "number" && typeof end === "number" && i >= start && i < end) {
        classes.push("char-range");
      }

      // Current index highlighting
      if (typeof index === "number" && i === index) {
        classes.push("char-current-index");
        if (!pointerLabel) pointerLabel = "→";
      }

      // Verified characters
      if (
        typeof left === "number" &&
        typeof right === "number" &&
        ((i < left && left >= right) || (i > right && left >= right))
      ) {
        classes.push("char-verified");
      }

      if (typeof start === "number" && typeof end === "number" && left >= right && i < string.length) {
        classes.push("char-verified");
      }

      // Match/Mismatch highlighting
      if (highlightedElements?.compared === "mismatch" && (i === left || i === right)) {
        classes.push("char-mismatch");
      } else if (highlightedElements?.compared === "match" && (i === left || i === right)) {
        classes.push("char-match");
      } else if (highlightedElements?.allMatched) {
        classes.push("char-verified");
      }

      charBox.className = classes.join(" ");

      charBox.innerHTML = `
        <div class="char-display">${char.toUpperCase()}</div>
        <div class="char-index">${i}</div>
        ${pointerLabel ? `<div class="pointer-label">${pointerLabel}</div>` : ""}
      `;

      stringDiv.appendChild(charBox);
    }

    this.container.appendChild(stringDiv);

    // Add result display if available
    if (result !== undefined && result !== null) {
      const resultDiv = document.createElement("div");
      resultDiv.className = "string-result";
      resultDiv.style.cssText = `
        text-align: center;
        margin-top: 12px;
        font-size: 0.9rem;
        font-family: var(--mono);
        font-weight: 600;
        color: var(--brand-2);
      `;
      resultDiv.textContent = `Result: "${result}"`;
      this.container.appendChild(resultDiv);
    }

    // Add state indicators
    const stateDiv = document.createElement("div");
    stateDiv.className = "string-state";
    stateDiv.style.cssText = `
      text-align: center;
      margin-top: 16px;
      font-size: 0.85rem;
      color: var(--muted);
      font-family: var(--mono);
    `;

    let stateText = "";
    if (typeof left === "number" && typeof right === "number") {
      if (left >= right) {
        stateText = "✓ Pointers crossed";
        stateDiv.style.color = "var(--ok)";
        stateDiv.style.fontWeight = "700";
      } else {
        stateText = `Pointers: left=${left}, right=${right}`;
      }
    } else if (typeof index === "number") {
      stateText = `Index: ${index}`;
    }

    if (highlightedElements?.allMatched) {
      stateText = "✓ All characters matched";
      stateDiv.style.color = "var(--ok)";
      stateDiv.style.fontWeight = "700";
    } else if (highlightedElements?.compared === "mismatch") {
      stateText = `✗ Mismatch found`;
      stateDiv.style.color = "var(--danger)";
      stateDiv.style.fontWeight = "700";
    }

    stateDiv.textContent = stateText;
    this.container.appendChild(stateDiv);
  }

  renderMergedStrings(stateSnapshot, highlightedElements) {
    const { word1 = "", word2 = "", string = "", index } = stateSnapshot;

    const container = document.createElement("div");
    container.style.cssText = `
      display: grid;
      grid-template-columns: 1fr 1fr;
      gap: 16px;
      padding: 16px;
    `;

    // Word 1
    const word1Div = this.createStringDisplay(word1, index, true, "Word 1");
    container.appendChild(word1Div);

    // Word 2
    const word2Div = this.createStringDisplay(word2, index, false, "Word 2");
    container.appendChild(word2Div);

    this.container.appendChild(container);

    // Result
    if (string) {
      const resultDiv = document.createElement("div");
      resultDiv.className = "string-result";
      resultDiv.style.cssText = `
        text-align: center;
        margin-top: 16px;
        font-size: 0.9rem;
        font-family: var(--mono);
        font-weight: 600;
        color: var(--brand-2);
      `;
      resultDiv.textContent = `Merged: "${string}"`;
      this.container.appendChild(resultDiv);
    }
  }

  createStringDisplay(str, index, isWord1, label) {
    const div = document.createElement("div");
    div.style.cssText = `
      border: 1px solid var(--line);
      border-radius: 8px;
      padding: 12px;
      background: color-mix(in srgb, var(--panel) 82%, white 18%);
    `;

    const titleDiv = document.createElement("div");
    titleDiv.style.cssText = `
      font-size: 0.75rem;
      font-weight: 700;
      color: var(--brand-2);
      margin-bottom: 8px;
      text-transform: uppercase;
    `;
    titleDiv.textContent = label;
    div.appendChild(titleDiv);

    const charsDiv = document.createElement("div");
    charsDiv.style.cssText = `
      display: flex;
      gap: 4px;
      flex-wrap: wrap;
    `;

    for (let i = 0; i < str.length; i++) {
      const charBox = document.createElement("div");
      charBox.style.cssText = `
        width: 30px;
        height: 30px;
        border: 1px solid var(--line);
        border-radius: 6px;
        display: flex;
        align-items: center;
        justify-content: center;
        font-weight: 700;
        font-size: 0.85rem;
        background: color-mix(in srgb, var(--panel) 80%, white 20%);
        ${typeof index === "number" && i === index ? "border-color: var(--brand); box-shadow: 0 0 0 2px var(--brand);" : ""}
      `;
      charBox.textContent = str[i].toUpperCase();
      charsDiv.appendChild(charBox);
    }

    div.appendChild(charsDiv);
    return div;
  }

  renderDPTable(stateSnapshot, highlightedElements) {
    // For regex matching - show string/pattern side-by-side with match status
    const { string = "", pattern = "", strIdx = 0, patIdx = 0 } = stateSnapshot;
    const result = stateSnapshot.result;

    const container = document.createElement("div");
    container.style.cssText = `
      display: flex;
      flex-direction: column;
      gap: 16px;
      padding: 20px;
    `;

    // Result indicator (if final)
    if (result !== null && result !== undefined) {
      const resultDiv = document.createElement("div");
      resultDiv.style.cssText = `
        text-align: center;
        padding: 12px 16px;
        border-radius: 8px;
        font-weight: 700;
        font-size: 1.1rem;
        ${result ? "background: var(--ok); color: white;" : "background: var(--danger); color: white;"}
      `;
      resultDiv.textContent = result ? "✓ MATCH" : "✗ NO MATCH";
      container.appendChild(resultDiv);
    }

    // String display
    const strDisplay = document.createElement("div");
    strDisplay.style.cssText = `
      display: flex;
      align-items: center;
      gap: 8px;
      flex-wrap: wrap;
    `;
    strDisplay.innerHTML = `<strong style="min-width: 80px;">String:</strong>`;
    
    if (string) {
      for (let i = 0; i < string.length; i++) {
        const char = document.createElement("span");
        char.style.cssText = `
          display: inline-flex;
          align-items: center;
          justify-content: center;
          width: 32px;
          height: 32px;
          border: 2px solid var(--line);
          border-radius: 6px;
          font-weight: 700;
          font-family: var(--mono);
          font-size: 0.9rem;
          background: ${i === strIdx ? "color-mix(in srgb, var(--warn) 20%, white 80%)" : "color-mix(in srgb, var(--panel) 80%, white 20%)"}
          ${i === strIdx ? "; border-color: var(--warn); box-shadow: 0 0 0 2px var(--warn);" : ""}
        `;
        char.textContent = string[i];
        strDisplay.appendChild(char);
      }
    }
    
    // Add [END] marker
    const endMarker = document.createElement("span");
    endMarker.style.cssText = `
      display: inline-flex;
      align-items: center;
      justify-content: center;
      height: 32px;
      padding: 0 8px;
      border: 2px dashed var(--line);
      border-radius: 6px;
      font-size: 0.75rem;
      font-weight: 600;
      ${strIdx >= string.length ? "border-color: var(--warn); background: var(--warn); color: white;" : ""}
    `;
    endMarker.textContent = "[END]";
    strDisplay.appendChild(endMarker);
    
    container.appendChild(strDisplay);

    // Pattern display
    const patDisplay = document.createElement("div");
    patDisplay.style.cssText = `
      display: flex;
      align-items: center;
      gap: 8px;
      flex-wrap: wrap;
    `;
    patDisplay.innerHTML = `<strong style="min-width: 80px;">Pattern:</strong>`;
    
    if (pattern) {
      for (let i = 0; i < pattern.length; i++) {
        const char = document.createElement("span");
        const isSpecial = pattern[i] === '.' || pattern[i] === '*';
        char.style.cssText = `
          display: inline-flex;
          align-items: center;
          justify-content: center;
          width: 32px;
          height: 32px;
          border: 2px solid var(--line);
          border-radius: 6px;
          font-weight: 700;
          font-family: var(--mono);
          font-size: 0.9rem;
          background: ${i === patIdx ? "color-mix(in srgb, var(--brand) 20%, white 80%)" : isSpecial ? "color-mix(in srgb, var(--muted) 10%, white 90%)" : "color-mix(in srgb, var(--panel) 80%, white 20%)"}
          ${i === patIdx ? "; border-color: var(--brand); box-shadow: 0 0 0 2px var(--brand);" : ""}
        `;
        char.title = isSpecial ? (pattern[i] === '.' ? "Wildcard: matches any char" : "Repeat: zero or more") : "";
        char.textContent = pattern[i];
        patDisplay.appendChild(char);
      }
    }
    
    // Add [END] marker
    const patEndMarker = document.createElement("span");
    patEndMarker.style.cssText = `
      display: inline-flex;
      align-items: center;
      justify-content: center;
      height: 32px;
      padding: 0 8px;
      border: 2px dashed var(--line);
      border-radius: 6px;
      font-size: 0.75rem;
      font-weight: 600;
      ${patIdx >= pattern.length ? "border-color: var(--brand); background: var(--brand); color: white;" : ""}
    `;
    patEndMarker.textContent = "[END]";
    patDisplay.appendChild(patEndMarker);
    
    container.appendChild(patDisplay);

    this.container.appendChild(container);
  }
}

