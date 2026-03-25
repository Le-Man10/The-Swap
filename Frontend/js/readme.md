login.html
  └── <script type="module" src="./js/login.js">
        └── imports auth.js         (to call setToken)

upload.html
  └── <script type="module" src="./js/upload.js">
        ├── imports auth.js         (to call isLoggedIn / clearToken)
        ├── imports apiClient.js    (to call apiFetch)
        └── imports fileValidator.js(to call validateCSV)

apiClient.js
  └── imports auth.js               (to call getToken / clearToken. need tokens)