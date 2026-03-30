export class GraphRenderer {
  constructor(container) {
    this.container = container;
  }

  render(step) {
    const snapshot = step.stateSnapshot;
    const highlighted = step.highlightedElements || {};
    const nodes = snapshot.nodes || [];
    const edges = snapshot.edges || [];

    this.container.innerHTML = "";

    const svg = document.createElementNS("http://www.w3.org/2000/svg", "svg");
    svg.setAttribute("viewBox", "0 0 800 380");
    svg.classList.add("graph-svg");

    edges.forEach((edge, index) => {
      const source = nodes.find(node => node.id === edge.u);
      const target = nodes.find(node => node.id === edge.v);
      if (!source || !target) return;

      const mstEdges = highlighted.mstEdges || [];
      const isMst = mstEdges.includes(edge.id);
      const isActive = isCurrentEdge(index, highlighted);

      const line = document.createElementNS("http://www.w3.org/2000/svg", "line");
      line.setAttribute("x1", source.x);
      line.setAttribute("y1", source.y);
      line.setAttribute("x2", target.x);
      line.setAttribute("y2", target.y);
      line.setAttribute("stroke", isActive ? "#d6452a" : isMst ? "#2fa66f" : "#9eb3c5");
      line.setAttribute("stroke-width", isActive ? "4" : isMst ? "3" : "2");
      svg.appendChild(line);

      if (snapshot.directed) {
        addArrow(svg, source, target, isActive);
      }

      const label = document.createElementNS("http://www.w3.org/2000/svg", "text");
      label.setAttribute("x", (source.x + target.x) / 2);
      label.setAttribute("y", (source.y + target.y) / 2 - 6);
      label.setAttribute("text-anchor", "middle");
      label.setAttribute("class", "edge-weight");
      label.textContent = String(edge.w ?? "");
      svg.appendChild(label);
    });

    nodes.forEach(node => {
      const group = document.createElementNS("http://www.w3.org/2000/svg", "g");

      const circle = document.createElementNS("http://www.w3.org/2000/svg", "circle");
      circle.setAttribute("cx", node.x);
      circle.setAttribute("cy", node.y);
      circle.setAttribute("r", "24");
      circle.setAttribute("fill", nodeFill(node.id, highlighted));
      circle.setAttribute("stroke", "#0f2d46");
      circle.setAttribute("stroke-width", "2");

      const title = document.createElementNS("http://www.w3.org/2000/svg", "title");
      title.textContent = `Node ${node.id}`;
      circle.appendChild(title);

      const text = document.createElementNS("http://www.w3.org/2000/svg", "text");
      text.setAttribute("x", node.x);
      text.setAttribute("y", node.y + 4);
      text.setAttribute("text-anchor", "middle");
      text.setAttribute("class", "node-label");
      text.textContent = String(node.id);

      const info = document.createElementNS("http://www.w3.org/2000/svg", "text");
      info.setAttribute("x", node.x);
      info.setAttribute("y", node.y + 38);
      info.setAttribute("text-anchor", "middle");
      info.setAttribute("class", "node-sub-label");
      info.textContent = node.label || "";

      group.appendChild(circle);
      group.appendChild(text);
      group.appendChild(info);
      svg.appendChild(group);
    });

    if (highlighted.negativeCycle) {
      const banner = document.createElement("div");
      banner.className = "negative-cycle-banner";
      banner.textContent = "Negative cycle detected";
      this.container.appendChild(banner);
    }

    this.container.appendChild(svg);
  }
}

function isCurrentEdge(edgeIndex, highlighted) {
  return highlighted.currentEdge === edgeIndex;
}

function nodeFill(nodeId, highlighted) {
  if (highlighted.updatedNode === nodeId) return "#2fa66f";
  if (highlighted.currentNode === nodeId) return "#f0b447";

  const visited = highlighted.visitedNodes || [];
  if (visited[nodeId]) return "#6fb3dd";
  return "#dbe8f4";
}

function addArrow(svg, source, target, active) {
  const angle = Math.atan2(target.y - source.y, target.x - source.x);
  const tipX = target.x - 24 * Math.cos(angle);
  const tipY = target.y - 24 * Math.sin(angle);
  const leftX = tipX - 10 * Math.cos(angle - Math.PI / 7);
  const leftY = tipY - 10 * Math.sin(angle - Math.PI / 7);
  const rightX = tipX - 10 * Math.cos(angle + Math.PI / 7);
  const rightY = tipY - 10 * Math.sin(angle + Math.PI / 7);

  const path = document.createElementNS("http://www.w3.org/2000/svg", "path");
  path.setAttribute("d", `M ${tipX} ${tipY} L ${leftX} ${leftY} L ${rightX} ${rightY} Z`);
  path.setAttribute("fill", active ? "#d6452a" : "#9eb3c5");
  svg.appendChild(path);
}
