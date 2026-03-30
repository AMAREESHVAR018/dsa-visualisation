const XP_KEY = "dsaVizXp";
const COMPLETED_KEY = "dsaVizCompleted";

export class Gamification {
  constructor(badgeEl, xpEl) {
    this.badgeEl = badgeEl;
    this.xpEl = xpEl;
    this.xp = Number(localStorage.getItem(XP_KEY) || 0);
    this.completed = new Set(JSON.parse(localStorage.getItem(COMPLETED_KEY) || "[]"));
    this.render();
  }

  markCompletion(algoKey) {
    if (this.completed.has(algoKey)) return;

    this.completed.add(algoKey);
    this.xp += 25;
    localStorage.setItem(XP_KEY, String(this.xp));
    localStorage.setItem(COMPLETED_KEY, JSON.stringify([...this.completed]));
    this.render();
  }

  render() {
    this.xpEl.textContent = `XP: ${this.xp}`;

    const unlocked = [];
    if (this.completed.size >= 1) unlocked.push("First Steps");
    if (this.completed.size >= 3) unlocked.push("Algorithm Explorer");
    if (this.completed.size >= 6) unlocked.push("Path Master");

    this.badgeEl.textContent = unlocked.length
      ? `Achievements: ${unlocked.join(" | ")}`
      : "Achievements: Start a visualization to earn XP";
  }
}
