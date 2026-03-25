import { getToken, clearToken } from './auth.js';

export async function apiFetch(url, options = {}) {
  const token = getToken();

  const headers = { ...options.headers };

  if (token) {
    headers['Authorization'] = `Bearer ${token}`;
  }

  const response = await fetch(url, { ...options, headers });

  if (response.status === 401) {
    clearToken();
    window.location.href = '/login.html';
    return;
  }

  return response;
}