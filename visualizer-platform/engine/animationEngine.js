export class AnimationEngine {
  constructor(stateManager, hooks = {}) {
    this.stateManager = stateManager;
    this.onTick = hooks.onTick || (() => {});
    this.onComplete = hooks.onComplete || (() => {});
    this.timer = null;
    this.speed = 600;
    this.playing = false;
  }

  setSpeed(speedMs) {
    this.speed = Math.max(80, Number(speedMs) || 600);
    if (this.playing) {
      this.pause();
      this.play();
    }
  }

  isPlaying() {
    return this.playing;
  }

  play() {
    if (this.playing || !this.stateManager.hasSteps()) return;

    this.playing = true;
    this.timer = setInterval(() => {
      const moved = this.stateManager.next();
      this.onTick(this.stateManager.snapshot());

      if (!moved) {
        this.pause();
        this.onComplete(this.stateManager.snapshot());
      }
    }, this.speed);
  }

  pause() {
    if (this.timer) clearInterval(this.timer);
    this.timer = null;
    this.playing = false;
  }

  toggle() {
    if (this.playing) this.pause();
    else this.play();
  }

  stepForward() {
    this.pause();
    const moved = this.stateManager.next();
    if (!moved) this.onComplete(this.stateManager.snapshot());
  }

  stepBackward() {
    this.pause();
    this.stateManager.prev();
  }

  reset() {
    this.pause();
    this.stateManager.reset();
  }
}
