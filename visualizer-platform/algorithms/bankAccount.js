export const bankAccountAlgorithm = {
  key: "BankAccount",
  label: "Bank Account",
  category: "array",
  description: "Track account balance through deposits and withdrawals.",
  codeLines: ["int balance = 1000;", "withdraw(500);", "deposit(200);", "return balance;"],
  inputSchema: [{ id: "balance", label: "Initial Balance", type: "number", defaultValue: 1000 }],
  createDefaultInput() { return { balance: 1000 }; },
  buildTimeline({ balance = 1000 }) {
    const amt = Math.max(0, parseInt(balance));
    const steps = [];
    steps.push({ description: `Balance: $${amt}`, why: "Starting balance", stateSnapshot: { mode: "array", values: [amt] }, highlightedElements: { index: 0 }, codeLine: 1, tracker: { balance: amt }, passCount: 0 });
    steps.push({ description: `Withdraw $500: $${amt} - $500 = $${amt - 500}`, why: "Apply withdrawal", stateSnapshot: { mode: "array", values: [amt - 500] }, highlightedElements: { swapped: [0] }, codeLine: 2, tracker: { operation: "withdraw", balance: amt - 500 }, passCount: 1 });
    steps.push({ description: `Deposit $200: $${amt - 500} + $200 = $${amt - 300}`, why: "Apply deposit", stateSnapshot: { mode: "array", values: [amt - 300] }, highlightedElements: { sorted: [0] }, codeLine: 3, tracker: { operation: "deposit", balance: amt - 300 }, passCount: 2 });
    return { timeline: steps };
  }
};
