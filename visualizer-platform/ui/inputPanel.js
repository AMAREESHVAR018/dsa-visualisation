export function renderInputForm(container, schema, values, onChange, onRun, onReset) {
  container.innerHTML = "";

  const form = document.createElement("form");
  form.className = "input-form";
  form.addEventListener("submit", event => {
    event.preventDefault();
    onRun();
  });

  schema.forEach(field => {
    const wrap = document.createElement("label");
    wrap.className = "input-group";
    wrap.innerHTML = `<span>${field.label}</span>`;

    const control = createControl(field, values[field.id] ?? field.defaultValue ?? "");
    control.addEventListener("input", event => onChange(field.id, event.target.value));
    wrap.appendChild(control);
    form.appendChild(wrap);
  });

  const buttons = document.createElement("div");
  buttons.className = "input-actions";

  const runButton = document.createElement("button");
  runButton.type = "submit";
  runButton.className = "btn-solid";
  runButton.textContent = "Apply Input";
  runButton.title = "Rebuild timeline with current input";

  const defaultsButton = document.createElement("button");
  defaultsButton.type = "button";
  defaultsButton.className = "btn-soft";
  defaultsButton.textContent = "Default Example";
  defaultsButton.title = "Load recommended sample input";
  defaultsButton.addEventListener("click", onReset);

  buttons.appendChild(runButton);
  buttons.appendChild(defaultsButton);
  form.appendChild(buttons);
  container.appendChild(form);
}

function createControl(field, value) {
  if (field.type === "textarea") {
    const textarea = document.createElement("textarea");
    textarea.value = value;
    textarea.rows = 6;
    return textarea;
  }

  const input = document.createElement("input");
  input.type = field.type || "text";
  input.value = value;
  if (field.placeholder) input.placeholder = field.placeholder;
  return input;
}
