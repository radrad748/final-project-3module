let errorDiv;

document.querySelector('form').addEventListener('submit', function (event) {
    event.preventDefault();
    if (errorDiv) {
        errorDiv.remove();
    }
    const email = document.querySelector('input[name="email"]').value;
    const password = document.querySelector('input[name="password"]').value;

    fetch('/login', {
        method: 'POST',
        body: JSON.stringify({email, password}),
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => response.json())
        .then(data => {
            if (data.isValid) {
                window.location.href = data.redirectUrl;
            } else {
                errorDiv = document.createElement('div');
                errorDiv.textContent = 'Неверный логин и/или пароль';
                errorDiv.style.color = 'red';

                const loginButtonDiv = document.getElementById('login-button');
                loginButtonDiv.appendChild(errorDiv);
            }
        })
})