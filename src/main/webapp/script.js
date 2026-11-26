function checkIfInsideFigure(r, x, y) {
    if (x <= 0 && y >= 0 && x * x + y * y <= r * r) {
        return true;
    }

    if (x >= 0 && x <= r / 2 && y <= 0 && y >= -r) {
        return true;
    }

    if (x <= 0 && y <= 0 && y >= -2 * x - r) {
        return true;
    }

    return false;
}

const canvas = document.getElementById("graph");
const context = canvas.getContext("2d");

context.lineWidth = 1;
context.fillStyle = "#378CFA";

drawFigure(context, 3);

context.strokeStyle = "black";
drawCoords(canvas, context);

const form = document.getElementById("check-form");
const x_error = document.querySelector("#x-error");
const y_error = document.querySelector("#y-error");
const r_error = document.querySelector("#r-error");
const clear_button = document.querySelector(".clear-group button");
const r_element = document.querySelector("#r-input");
const y_element = document.querySelector("#y-input");
const x_element = document.querySelector("#x-checkbox");
const table_button = document.querySelector(".tablebutton-group button");
const bad_error_label = document.querySelector("#bad_error");

const INT = /^-?\d{1,9}$/;
const DECIMAL = /-?\d+([.,]\d+)?$/;
const VALID_MESSAGE = "Верное значение.";

class ValidationException extends Error {}

function validateX(values) {
    if (values.length === 0) {
        throw new ValidationException("Не выбран ни один X.");
    }
    return values.map(v => Number(v));  
}

function validateY(s) {
    if (s == null || s.trim() === "") {
        throw new ValidationException("Не введён Y.");
    }
    if (!DECIMAL.test(s.trim())) {
        throw new ValidationException("Y должен быть числом.");
    }
    const y = Number(s.trim().replace(",", "."));
    if (y <= -5 || y >= 3) {
        throw new ValidationException("Y должен быть в интервале (-5, 3)");
    }
    return y;
}

function validateR(s) {
    if (s == null || s.trim() === "") {
        throw new ValidationException("Не введён R.");
    }
    if (!DECIMAL.test(s.trim())) {
        throw new ValidationException("R должен быть числом.");
    }
    const r = Number(s.trim().replace(",", "."));
    if (r <= 2 || r >= 5) {
        throw new ValidationException("R должен быть в интервале (2, 5)");
    }
    return r;
}

form.addEventListener("submit", (event) => {
    event.preventDefault();
    let is_wrong = false;
    let y;
    let r;
    bad_error_label.textContent = "";
    const xs = Array.from(x_element.querySelectorAll('input[name="x"]:checked')).map(box => box.value);

    try {
        validateX(xs);
        x_error.textContent = "";
        x_element.classList.remove("error");
    } catch (e) {
        x_error.textContent = e.message;
        x_element.classList.add("error");
        is_wrong = true;
    }

    try {
        y = validateY(y_element.value);
        y_error.textContent = "";
        y_element.classList.remove("error");
    } catch (e) {
        y_error.textContent = e.message;
        y_element.classList.add("error");
        is_wrong = true;
    }

    try {
        r = validateR(r_element.value);
        r_error.textContent = "";
        r_element.classList.remove("error");
    } catch (e) {
        r_error.textContent = e.message;
        r_element.classList.add("error");
        is_wrong = true;
    }

    if (is_wrong){
        return;
    }

    form.submit();
});

canvas.addEventListener("click", (event) => {
    bad_error_label.textContent = "";
    let r;
    y_element.classList.remove("error");
    y_error.textContent = "";
    bad_error_label.textContent = "";
    r_element.classList.remove("error");
    r_error.textContent = "";
    bad_error_label.textContent = "";
    
    try {
        r = validateR(r_element.value);
        r_error.textContent = "";
        r_element.classList.remove("error");
    } catch (e) {
        r_error.textContent = e.message;
        r_element.classList.add("error");
        return;
    }

    const rect = canvas.getBoundingClientRect();
    const px = (event.clientX - rect.left) * (canvas.width / rect.width);
    const py = (event.clientY - rect.top) * (canvas.height / rect.height);
    const x = Number(((px - canvas.width / 2) / 40).toFixed(3));
    const y = Number(((canvas.height / 2 - py) / 40).toFixed(3));

    const tempForm = document.createElement("form");
    tempForm.method = "POST";
    tempForm.action = "controller";
    const input = document.createElement("input");
    input.type = "hidden";
    input.name = "clickEvent";
    input.value = "true";
    tempForm.appendChild(input);
    
    const fields = { x: x, y: y, r: r_element.value };
    for (const [name, value] of Object.entries(fields)) {
        const input = document.createElement("input");
        input.type = "hidden";
        input.name = name;
        input.value = value;
        tempForm.appendChild(input);
    }
    
    document.body.appendChild(tempForm);
    tempForm.submit();
    document.body.removeChild(tempForm);
});


clear_button.addEventListener("click", () => {
    form.reset();
    r_element.classList.remove("error");
    y_element.classList.remove("error");
    x_error.textContent = "";
    y_error.textContent = "";
    r_error.textContent = "";
    bad_error_label.textContent = "";
});

table_button.addEventListener("click", () => {
    bad_error_label.textContent = "";
    const tempForm = document.createElement("form");
    tempForm.method = "POST";
    tempForm.action = "controller";

    const input = document.createElement("input");
    input.type = "hidden";
    input.name = "clearTable";
    input.value = "true";
    tempForm.appendChild(input);

    document.body.appendChild(tempForm);
    tempForm.submit();
    document.body.removeChild(tempForm);
});

y_element.addEventListener("input", () => {
    y_element.classList.remove("error");
    y_error.textContent = "";
    bad_error_label.textContent = "";
});

r_element.addEventListener("input", () => {
    r_element.classList.remove("error");
    r_error.textContent = "";
    bad_error_label.textContent = "";

    let r;
    try {
        r = validateR(r_element.value);
    } catch (e) {
        return; 
    }

    context.clearRect(0, 0, canvas.width, canvas.height);
    context.lineWidth = 1;
    context.fillStyle = "#378CFA";
    drawFigure(context, r);
    context.strokeStyle = "black";
    drawCoords(canvas, context);
});

x_element.addEventListener("change", (event) => {
    x_element.classList.remove("error");
    x_error.textContent = "";
    bad_error_label.textContent = "";
});
