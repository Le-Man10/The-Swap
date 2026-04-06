// ─── CONFIG ───────────────────────────────────────────────
const API_BASE   = 'http://localhost:8080/api/v1/login'; // replace when server is ready
const MOCK_MODE  = true; // set to false when server is ready

const loginBtn = document.getElementById('login-btn');
const errorBox = document.getElementById('error-box');

document.getElementById('email').addEventListener('keydown', e => {
  if (e.key === 'Enter') document.getElementById('password').focus();
});
document.getElementById('password').addEventListener('keydown', e => {
  if (e.key === 'Enter') doLogin();
});

function showError(msg) {
  errorBox.textContent = msg;
  errorBox.style.display = 'block';
}

function delay(ms) { return new Promise(r => setTimeout(r, ms)); }

async function mockLogin(email, password) {
  await delay(900);
  if (!email.includes('@')) return { ok: false, message: 'Please enter a valid email address.' };
  if (password.length < 3)  return { ok: false, message: 'Incorrect email or password.' };
  return { ok: true, token: 'mock.jwt.' + btoa(email) };
}

async function doLogin() {
  errorBox.style.display = 'none';
  const email    = document.getElementById('email').value.trim();
  const password = document.getElementById('password').value;

  if (!email || !password) { showError('Please enter your email and password.'); return; }

  loginBtn.disabled = true;
  loginBtn.innerHTML = '<span class="spinner"></span>Signing in…';

  try {
    let token;

    if (MOCK_MODE) {
      const result = await mockLogin(email, password);
      if (!result.ok) { showError(result.message); return; }
      token = result.token;
    } else {
      const res  = await fetch(`${API_BASE}/api/v1/login`, {
        method:  'GET',
        headers: { 'Content-Type': 'application/json' },
        body:    JSON.stringify({ email, password })
      });
      const data = await res.json();
      if (!res.ok) { showError(data.message || `Error ${data.error}: Login failed`); return; }
      token = data.message;
    }

    // persist session then go to dashboard
    sessionStorage.setItem('swap_token', token);
    sessionStorage.setItem('swap_email', email);
    sessionStorage.setItem('swap_mock',  MOCK_MODE ? '1' : '0');
    window.location.href = 'dashboard.html';

  } catch (e) {
    showError('Could not reach the server. Check your connection.');
  } finally {
    loginBtn.disabled = false;
    loginBtn.textContent = 'Sign in';
  }
}