import { isLoggedIn } from './auth.js';
import { apiFetch }   from './apiClient.js';
import { validateCSV } from './fileValidator.js';

// Guard: bounce to login if no token
if (!isLoggedIn()) {
  window.location.href = '/login.html';
}

const form = document.getElementById('upload-form');
const fileInput = document.getElementById('file-input');
const submitBtn = document.getElementById('submit-btn');
const statusMsg = document.getElementById('status-msg');

form.addEventListener('submit', async (e) => {
  e.preventDefault();
  statusMsg.textContent = '';

  const file = fileInput.files[0];
  if (!file) { statusMsg.textContent = 'Please select a file.'; return; }

  // Validata before sending
  const fileText = await file.text();
  const errors = validateCSV(fileText);

  if (errors.length > 0) {
    statusMsg.innerHTML = '<strong>Fix these issues:</strong><br>' +
      errors.map(e => `• ${e}`).join('<br>');
    return;
  }

  // Builds and send the request
  const formData = new FormData();
  formData.append('file', file);

  submitBtn.disabled = true;
  submitBtn.textContent = 'Uploading...';

  try {
    const response = await apiFetch('/api/v1/swap', {
      method: 'POST',
      body: formData
    });

    if (!response) return; // 401 redirect already handled (apiFetch returns undefined)

    if (response.ok) {
      statusMsg.textContent = '✓ File uploaded successfully!';
      form.reset();
    } else {
      const err = await response.json().catch(() => null);
      statusMsg.textContent = err?.message ?? `Upload failed (${response.status}).`;
    }
  } catch {
    statusMsg.textContent = 'Network error — please try again.';
  } finally {
    submitBtn.disabled = false;
    submitBtn.textContent = 'Upload';
  }
});