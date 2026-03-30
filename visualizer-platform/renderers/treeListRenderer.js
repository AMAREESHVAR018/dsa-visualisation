export class TreeListRenderer {
  constructor(container) {
    this.container = container;
  }

  render(step) {
    if (step.stateSnapshot.kind === "tree") {
      this.renderTree(step);
      return;
    }

    const values = step.stateSnapshot.values || [];
    const pointerIndex = Number(step.highlightedElements.pointerIndex ?? -1);
    const visited = step.highlightedElements.visitedNodes || [];

    this.container.innerHTML = "";

    const listWrap = document.createElement("div");
    listWrap.className = "list-chain";

    values.forEach((value, index) => {
      const node = document.createElement("div");
      node.className = "list-node";
      if (visited[index]) node.classList.add("node-visited");
      if (pointerIndex === index) node.classList.add("node-pointer");
      node.title = `Node ${index}, value ${value}`;

      node.innerHTML = `<div class="node-value">${value}</div><div class="node-index">index ${index}</div>`;
      listWrap.appendChild(node);

      if (index < values.length - 1) {
        const arrow = document.createElement("div");
        arrow.className = "list-arrow";
        arrow.textContent = "->";
        listWrap.appendChild(arrow);
      }
    });

    const pointerState = document.createElement("div");
    pointerState.className = "pointer-state";
    pointerState.textContent = pointerIndex >= values.length ? "Pointer: null" : `Pointer: node ${pointerIndex}`;

    this.container.appendChild(listWrap);
    this.container.appendChild(pointerState);
  }

  renderTree(step) {
    const nodes = step.stateSnapshot.nodes || [];
    const edges = step.stateSnapshot.edges || [];
    const highlighted = step.highlightedElements || {};

    this.container.innerHTML = "";

    const svg = document.createElementNS("http://www.w3.org/2000/svg", "svg");
    svg.classList.add("graph-svg");
    svg.setAttribute("viewBox", "0 0 800 340");

    edges.forEach(edge => {
      const source = nodes.find(node => node.id === edge.u);
      const target = nodes.find(node => node.id === edge.v);
      if (!source || !target) return;

      const line = document.createElementNS("http://www.w3.org/2000/svg", "line");
      line.setAttribute("x1", source.x);
      line.setAttribute("y1", source.y);
      line.setAttribute("x2", target.x);
      line.setAttribute("y2", target.y);
      line.setAttribute("stroke", "#8ea9bc");
      line.setAttribute("stroke-width", "2");
      svg.appendChild(line);
    });

    nodes.forEach(node => {
      const visited = highlighted.visitedNodes?.[node.id];
      const active = highlighted.currentNode === node.id;
      const queued = (highlighted.queueIndices || []).includes(node.id);

      const circle = document.createElementNS("http://www.w3.org/2000/svg", "circle");
      circle.setAttribute("cx", node.x);
      circle.setAttribute("cy", node.y);
      circle.setAttribute("r", "21");
      circle.setAttribute("fill", active ? "#f0b447" : queued ? "#6fb3dd" : visited ? "#2fa66f" : "#dbe8f4");
      circle.setAttribute("stroke", "#0f2d46");
      circle.setAttribute("stroke-width", "2");
      svg.appendChild(circle);

      const label = document.createElementNS("http://www.w3.org/2000/svg", "text");
      label.setAttribute("x", node.x);
      label.setAttribute("y", node.y + 5);
      label.setAttribute("text-anchor", "middle");
      label.setAttribute("class", "node-label");
      label.textContent = node.label;
      svg.appendChild(label);
    });

    this.container.appendChild(svg);
  }
}
