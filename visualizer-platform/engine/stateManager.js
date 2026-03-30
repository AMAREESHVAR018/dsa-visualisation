export class StateManager {
  constructor() {
    this.timeline = [];
    this.pointer = 0;
    this.meta = {};
    this.listeners = new Set();
  }

  setTimeline(timeline, meta = {}) {
    this.timeline = (timeline || []).map((step, index) => ({
      stepNumber: index + 1,
      description: step.description || "",
      why: step.why || "",
      stateSnapshot: step.stateSnapshot || {},
      highlightedElements: step.highlightedElements || {},
      codeLine: step.codeLine || 1,
      tracker: step.tracker || {},
      passCount: step.passCount || 0,
      negativeCycle: Boolean(step.negativeCycle)
    }));

    this.pointer = 0;
    this.meta = meta;
    this.emit();
  }

  getCurrent() {
    return this.timeline[this.pointer] || null;
  }

  hasSteps() {
    return this.timeline.length > 0;
  }

  canNext() {
    return this.pointer < this.timeline.length - 1;
  }

  canPrev() {
    return this.pointer > 0;
  }

  next() {
    if (!this.canNext()) return false;
    this.pointer += 1;
    this.emit();
    return true;
  }

  prev() {
    if (!this.canPrev()) return false;
    this.pointer -= 1;
    this.emit();
    return true;
  }

  reset() {
    this.pointer = 0;
    this.emit();
  }

  jumpTo(index) {
    const clamped = Math.max(0, Math.min(index, this.timeline.length - 1));
    if (clamped !== this.pointer) {
      this.pointer = clamped;
      this.emit();
    }
  }

  subscribe(listener) {
    this.listeners.add(listener);
    listener(this.snapshot());
    return () => this.listeners.delete(listener);
  }

  snapshot() {
    return {
      index: this.pointer,
      total: this.timeline.length,
      step: this.getCurrent(),
      meta: this.meta
    };
  }

  emit() {
    const snapshot = this.snapshot();
    this.listeners.forEach(listener => listener(snapshot));
  }
}
