var canvas = document.getElementById('plane');
var ctx = canvas.getContext('2d');
var xSelect = document.getElementById('xInput');
var yInput = document.getElementById('yInput');
var rBoxes = document.querySelectorAll('input[name="r"]');
var form = document.getElementById('checkForm');
var errorEl = document.getElementById('formError');
var resultsBody = document.getElementById('resultsBody');

var size = 480;
var scale = size / 9;

function px(x) { return size / 2 + x * scale; }
function py(y) { return size / 2 - y * scale; }

function draw(r, x, y, hit) {
  ctx.clearRect(0, 0, size, size);

  // оси
  ctx.strokeStyle = '#333';
  ctx.fillStyle = '#333';
  ctx.font = '11px sans-serif';
  ctx.textAlign = 'center';
  ctx.beginPath();
  ctx.moveTo(10, py(0));
  ctx.lineTo(size - 10, py(0));
  ctx.moveTo(px(0), size - 10);
  ctx.lineTo(px(0), 10);
  ctx.stroke();
  ctx.fillText('x', size - 10, py(0) - 10);
  ctx.fillText('y', px(0) + 10, 15);

  for (var i = -4; i <= 4; i++) {
    if (i == 0) continue;
    ctx.fillText(i, px(i), py(0) + 14);
  }
  for (i = -3; i <= 3; i++) {
    if (i == 0) continue;
    ctx.fillText(i, px(0) - 12, py(i) + 4);
  }

  // область (вариант 22710)
  ctx.beginPath();
  ctx.moveTo(px(r), py(0));
  for (i = 0; i <= 40; i++) {
    var a = (Math.PI / 2) * (i / 40);
    ctx.lineTo(px(r * Math.cos(a)), py(r * Math.sin(a)));
  }
  ctx.lineTo(px(0), py(r / 2));
  ctx.lineTo(px(-r), py(0));
  ctx.lineTo(px(-r), py(-r));
  ctx.lineTo(px(0), py(-r));
  ctx.lineTo(px(0), py(0));
  ctx.lineTo(px(r), py(0));
  ctx.closePath();
  ctx.fillStyle = 'rgba(51,133,255,0.55)';
  ctx.fill();
  ctx.strokeStyle = '#2f6fd6';
  ctx.stroke();

  // точка
  if (y !== null) {
    ctx.beginPath();
    ctx.arc(px(x), py(y), 5, 0, Math.PI * 2);
    ctx.fillStyle = hit === null ? '#666' : (hit ? 'green' : 'red');
    ctx.fill();
    ctx.strokeStyle = '#000';
    ctx.stroke();
  }
}

function inRegion(x, y, r) {
  if (x >= 0 && y >= 0 && x * x + y * y <= r * r) return true;
  if (x <= 0 && y >= 0 && y <= r / 2 && x >= 2 * y - r) return true;
  if (x >= -r && x <= 0 && y >= -r && y <= 0) return true;
  return false;
}

function getR() {
  for (var i = 0; i < rBoxes.length; i++) {
    if (rBoxes[i].checked) return Number(rBoxes[i].value);
  }
  return null;
}

function preview() {
  var y = parseFloat(yInput.value);
  draw(getR(), Number(xSelect.value), isNaN(y) ? null : y, null);
}

for (var i = 0; i < rBoxes.length; i++) {
  rBoxes[i].onchange = function () {
    if (this.checked) {
      for (var j = 0; j < rBoxes.length; j++) {
        if (rBoxes[j] !== this) rBoxes[j].checked = false;
      }
    } else {
      this.checked = true;
    }
    preview();
  };
}

xSelect.onchange = preview;
yInput.oninput = preview;

function addRow(x, y, r, hit, ts) {
  var tr = document.createElement('tr');
  var dt = new Date(ts).toLocaleString('ru-RU', { dateStyle: 'long', timeStyle: 'medium' });
  tr.innerHTML = '<td>' + x + '</td><td>' + y + '</td><td>' + r + '</td>' +
    '<td class="' + (hit ? 'hit-yes' : 'hit-no') + '">' + (hit ? 'Да' : 'Нет') + '</td>' +
    '<td>' + dt + '</td>';
  resultsBody.appendChild(tr);
}

form.onsubmit = function (e) {
  e.preventDefault();
  errorEl.textContent = '';

  var x = Number(xSelect.value);
  var yRaw = yInput.value.trim();
  var r = getR();

  if (!/^-?\d+(\.\d+)?$/.test(yRaw) || Number(yRaw) < -3 || Number(yRaw) > 3) {
    errorEl.textContent = 'Y должен быть числом от -3 до 3.';
    return;
  }
  if (!r) {
    errorEl.textContent = 'Выберите значение R.';
    return;
  }

  var y = Number(yRaw);
  var hit = inRegion(x, y, r);
  var ts = new Date().toISOString();

  var saved = JSON.parse(localStorage.getItem('lab1-results') || '[]');
  saved.push({ x: x, y: y, r: r, hit: hit, ts: ts });
  localStorage.setItem('lab1-results', JSON.stringify(saved));

  addRow(x, y, r, hit, ts);
  draw(r, x, y, hit);
};

var saved = JSON.parse(localStorage.getItem('lab1-results') || '[]');
for (i = 0; i < saved.length; i++) {
  addRow(saved[i].x, saved[i].y, saved[i].r, saved[i].hit, saved[i].ts);
}
preview();
