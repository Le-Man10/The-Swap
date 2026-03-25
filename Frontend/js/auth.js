// Stores the JWT in memory (disappears on refresh — safer than localStorage)
let _token = null;

export function setToken(token) { _token = token; }
export function getToken()      { return _token; }
export function clearToken()    { _token = null; }
export function isLoggedIn()    { return _token !== null; }