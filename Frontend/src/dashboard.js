
const API_BASE = 'http://localhost:8080/api/v1/swap'; // replace when server is ready


// read session set by login.html
const token     = sessionStorage.getItem('swap_token');
const email     = sessionStorage.getItem('swap_email');
let   mockMode  = sessionStorage.getItem('swap_mock') !== '0';

// guard: redirect to login if no session
if (!token) window.location.href = 'login.html';

// populate user info
if (email) {
  document.getElementById('user-email').textContent = email;
  const initials = email.split('@')[0].slice(0, 2).toUpperCase();
  document.getElementById('user-avatar').textContent = initials;
}

// hide banner if not mock
if (!mockMode) {
  document.getElementById('mock-banner').style.display = 'none';
}

let selectedFile = null;

function toggleLive(el) {
  mockMode = !el.checked;
  document.getElementById('mock-banner').style.display = mockMode ? 'flex' : 'none';
}

function doLogout() {
  sessionStorage.clear();
  window.location.href = 'login.html';
}

function showError(msg) {
  const el = document.getElementById('error-box');
  el.textContent = msg; el.className = 'feedback-box error'; el.style.display = 'block';
  document.getElementById('success-box').style.display = 'none';
}

function showSuccess(msg) {
  const el = document.getElementById('success-box');
  el.textContent = msg; el.className = 'feedback-box success'; el.style.display = 'block';
  document.getElementById('error-box').style.display = 'none';
}

function hideFeedback() {
  document.getElementById('error-box').style.display   = 'none';
  document.getElementById('success-box').style.display = 'none';
}

function handleDragOver(e) {
  e.preventDefault();
  document.getElementById('drop-zone').classList.add('dragover');
}

function handleDragLeave() {
  document.getElementById('drop-zone').classList.remove('dragover');
}

function handleDrop(e) {
  e.preventDefault();
  document.getElementById('drop-zone').classList.remove('dragover');
  const file = e.dataTransfer.files[0];
  if (file) setFile(file);
}

function handleFileSelect(e) {
  const file = e.target.files[0];
  if (file) setFile(file);
}

function setFile(file) {
  selectedFile = file;
  document.getElementById('file-name-display').textContent = file.name;
  const kb = file.size / 1024;
  document.getElementById('file-size-display').textContent =
    kb < 1024 ? `${kb.toFixed(1)} KB` : `${(kb / 1024).toFixed(1)} MB`;
  document.getElementById('file-pill').style.display = 'flex';
  document.getElementById('submit-btn').disabled = false;
  hideFeedback();
}

function removeFile() {
  selectedFile = null;
  document.getElementById('file-input').value = '';
  document.getElementById('file-pill').style.display  = 'none';
  document.getElementById('submit-btn').disabled = true;
}

function delay(ms) { return new Promise(r => setTimeout(r, ms)); }

async function doSubmit() {
  if (!selectedFile) return;
  hideFeedback();

  const btn = document.getElementById('submit-btn');
  btn.disabled = true;
  btn.innerHTML = '<span class="spinner"></span>Processing…';

  try {
    if (mockMode) {
      await delay(1600);
      const blob = new Blob(
        ['Mock result — server not connected yet'],
        { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' }
      );
      triggerDownload(blob, 'swap_result.xlsx');
      showSuccess('Demo: file "processed" successfully. (No real server was called.)');
      removeFile();

    } else {
      const formData = new FormData();
      formData.append('file', selectedFile);

      const res = await fetch(`${API_BASE}/api/v1/swap`, {
        method:  'POST',
        headers: { 'Authorization': `Bearer ${token}` },
        body:    formData
      });

      if (!res.ok) {
        const data = await res.json().catch(() => ({}));
        showError(data.message || `Error ${data.error || res.status}: Submission failed.`);
        return;
      }

      const blob = await res.blob();
      triggerDownload(blob, 'swap_result.xlsx');
      showSuccess('File processed successfully. Your result has been downloaded.');
      removeFile();
    }

  } catch (e) {
    showError('Could not reach the server. Check your connection.');
  } finally {
    btn.disabled = false;
    btn.textContent = 'Submit file';
  }
}

function triggerDownload(blob, filename) {
  const url = URL.createObjectURL(blob);
  const a   = document.createElement('a');
  a.href = url; a.download = filename; a.click();
  URL.revokeObjectURL(url);
}