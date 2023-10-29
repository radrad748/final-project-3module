let errorDiv;
let errorEmail;
let errorPassword;
let errorName;

document.querySelector('form').addEventListener('submit', function (event) {
        event.preventDefault();
        if (errorDiv) {
            errorDiv.remove();
        }
        if (errorEmail) {
            errorEmail.remove();
        }
        if (errorPassword) {
            errorPassword.remove();
        }
        if (errorName) {
            errorName.remove();
        }
        const email = document.querySelector('input[name="text-input-email-name"]').value;
        const password = document.querySelector('input[name="password-input-name"]').value;
        const name = document.querySelector('input[name="text-input-name-name"]').value;

        if (!isValidEmail(email)) {
            errorEmail = document.createElement('div');
            errorEmail.textContent = 'Неверный email';
            errorEmail.style.color = 'red';

            const emailDiv = document.getElementById('email-div');
            emailDiv.appendChild(errorEmail);
        }
        if (!isStrongPassword(password)) {
            errorPassword = document.createElement('div');
            errorPassword.textContent = 'Пароль должен содержать цифры, символы верхнего и нижнего регистра';
            errorPassword.style.color = 'red';

            const passwordDiv = document.getElementById('password-div');
            passwordDiv.appendChild(errorPassword);
        }
        if (!isValidName(name)) {
            errorName = document.createElement('div');
            errorName.textContent = 'Имя должно быть более 1 символа';
            errorName.style.color = 'red';

            const nameDiv = document.getElementById('name-div');
            nameDiv.appendChild(errorName);
        }

        if (isValidEmail(email) && isStrongPassword(password) && isValidName(name)) {
            fetch('/registration-check', {
                method: 'POST',
                body: JSON.stringify({name, email, password}),
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
                        errorDiv.textContent = data.response;
                        errorDiv.style.color = 'red';

                        const registerForm = document.getElementById('form');
                        registerForm.appendChild(errorDiv);
                    }
                })
        }
    });


function isValidEmail(email) {
    const emailRegex = /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i;
    return emailRegex.test(email);
}

function isStrongPassword(password) {
    const digitRegex = /\d/;
    const uppercaseRegex = /[A-Z]/;
    const lowercaseRegex = /[a-z]/;

    const validPassword =  (
        digitRegex.test(password) &&
        uppercaseRegex.test(password) &&
        lowercaseRegex.test(password)
    );
    return validPassword;
}

function isValidName(name) {
   const validName = name && name.trim().length > 1;
   return validName;
}