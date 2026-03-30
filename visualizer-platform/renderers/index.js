import { ArrayRenderer } from "./arrayRenderer.js";
import { GraphRenderer } from "./graphRenderer.js";
import { MatrixRenderer } from "./matrixRenderer.js";
import { TreeListRenderer } from "./treeListRenderer.js";
import { StringRenderer } from "./stringRenderer.js";

export class RendererHub {
  constructor(container) {
    this.renderers = {
      array: new ArrayRenderer(container),
      graph: new GraphRenderer(container),
      matrix: new MatrixRenderer(container),
      "tree-list": new TreeListRenderer(container),
      string: new StringRenderer(container)
    };
  }

  render(step) {
    if (!step) return;
    const mode = step.stateSnapshot.mode;
    const renderer = this.renderers[mode] || this.renderers.array;
    renderer.render(step);
  }
}
