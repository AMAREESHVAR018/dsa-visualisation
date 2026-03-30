export class ArrayRenderer {
  constructor(container) {
    this.container = container;
  }

  render(step) {
    const values = step.stateSnapshot.values || step.stateSnapshot.array || [];
    const compareSource = Array.isArray(step.highlightedElements.compare)
      ? step.highlightedElements.compare
      : step.highlightedElements.currentIndex !== undefined
        ? [step.highlightedElements.currentIndex]
        : [];
    const compare = new Set(compareSource);
    const swapped = new Set(step.highlightedElements.swapped || []);
    const sorted = new Set(step.highlightedElements.sorted || step.highlightedElements.highlightIndices || []);
    const max = Math.max(1, ...values);

    this.container.innerHTML = "";

    const wrap = document.createElement("div");
    wrap.className = "array-bars";

    values.forEach((value, index) => {
      const bar = document.createElement("div");
      bar.className = "array-bar";
      bar.style.height = `${Math.max(16, (value / max) * 260)}px`;
      bar.title = `index ${index}, value ${value}`;

      if (sorted.has(index)) bar.classList.add("bar-sorted");
      if (compare.has(index)) bar.classList.add("bar-compare");
      if (swapped.has(index)) bar.classList.add("bar-swapped");

      const valueTag = document.createElement("span");
      valueTag.className = "bar-value";
      valueTag.textContent = String(value);

      const indexTag = document.createElement("small");
      indexTag.className = "bar-index";
      indexTag.textContent = String(index);

      bar.appendChild(valueTag);
      bar.appendChild(indexTag);
      wrap.appendChild(bar);
    });

    this.container.appendChild(wrap);
  }
}
