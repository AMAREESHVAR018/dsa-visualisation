export class MatrixRenderer {
  constructor(container) {
    this.container = container;
  }

  render(step) {
    const matrix = step.stateSnapshot.matrix || [];
    const highlighted = step.highlightedElements || {};
    const pivot = highlighted.pivot || { k: -1, i: -1, j: -1 };

    this.container.innerHTML = "";

    const table = document.createElement("table");
    table.className = "matrix-table";

    matrix.forEach((row, i) => {
      const tr = document.createElement("tr");
      row.forEach((value, j) => {
        const td = document.createElement("td");
        td.textContent = value === Infinity ? "inf" : String(value);

        if (i === pivot.i && j === pivot.j) td.classList.add("matrix-active");
        if (i === pivot.k || j === pivot.k) td.classList.add("matrix-pivot-axis");

        tr.appendChild(td);
      });
      table.appendChild(tr);
    });

    this.container.appendChild(table);
  }
}
