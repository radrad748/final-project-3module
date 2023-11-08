
document.getElementById('formName').addEventListener('submit', function (event) {
    event.preventDefault();
    deleteWrongText();

    var nameInput = document.getElementById('name-input');
    var name = nameInput.value;

    if (isValidName(name)) {
        fetch('/settings', {
            method: 'POST',
            body: JSON.stringify({name}),
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(response => response.json())
            .then(data => {
                if (data.isValid) {
                    var message = 'вы успешно изменили имя';
                    successfulChange('wrong-name', message);
                } else {
                    var message = 'имя должно содержать от 1 до 30 символов';
                    addWrong('wrong-name', message);
                }
            })

    } else {
        var message = 'имя должно содержать от 1 до 30 символов';
        addWrong('wrong-name', message);
    }
});
document.getElementById('formEmail').addEventListener('submit', function (event) {
    event.preventDefault();
    deleteWrongText();

    var emailInput = document.getElementById('email-input');
    var email = emailInput.value;

    if (isValidEmail(email)) {
        fetch('/settings', {
            method: 'POST',
            body: JSON.stringify({email}),
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(response => response.json())
            .then(data => {
                if (data.isValid) {
                    var message = 'вы успешно изменили емейл';
                    successfulChange('wrong-email', message);
                } else {
                    var message = 'вы ввели некорректный емейл и/или пользователь с таким емейлом уже существует';
                    addWrong('wrong-email', message);
                }
            })

    } else {
        var message = 'вы ввели некорректный емейл и/или пользователь с таким емейлом уже существует';
        addWrong('wrong-email', message);
    }
});
document.getElementById('formPassword').addEventListener('submit', function (event) {
    event.preventDefault();
    deleteWrongText();

    var oldPasswordInput = document.getElementById('old-password');
    var oldPassword = oldPasswordInput.value;

    var newPasswordInput = document.getElementById('new-password');
    var newPassword = newPasswordInput.value;

    if (isStrongPassword(newPassword) && oldPassword) {
        fetch('/settings', {
            method: 'POST',
            body: JSON.stringify({oldPassword, newPassword}),
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(response => response.json())
            .then(data => {
                if (data.isValid) {
                    var message = 'вы успешно изменили пароль';
                    successfulChange('wrong-password', message);
                } else {
                    var message = 'вы ввели некорректный текущий и/или новый пароль, пароль должен сожержать цифры, символы верхнего и нижнего регистра';
                    addWrong('wrong-password', message);
                }
            })

    } else {
        var message = 'вы ввели некорректный текущий и/или новый пароль, пароль должен сожержать цифры, символы верхнего и нижнего регистра';
        addWrong('wrong-password', message);
    }
});

document.getElementById('deleteProfileButton').addEventListener('click', function () {
    var modal = document.createElement('div');
    modal.classList.add('modal');

    modal.innerHTML = `
      <div class="modal-content" style="width: 35%">
        <p>Действительно ли вы хотите удалить профиль?</p>
        <button id="confirmDeleteButton">Да</button>
        <button id="cancelDeleteButton">Отмена</button>
      </div>
    `;
    document.body.appendChild(modal);

    var confirmDeleteButton = document.getElementById('confirmDeleteButton');
    var cancelDeleteButton = document.getElementById('cancelDeleteButton');

    confirmDeleteButton.addEventListener('click', function () {
        fetch('/settings', {
            method: 'DELETE'
        })
            .then(response => {
                if (response.ok) {
                    window.location.href = '/';
                } else {
                    console.error('Ошибка при удалении');
                }
            })
            .catch(error => {
                console.error('Ошибка при отправке запроса:', error);
            });
    });

    cancelDeleteButton.addEventListener('click', function () {
        closeModal();
    });
});

function closeModal() {
    var modal = document.querySelector('.modal');
    if (modal) {
        modal.parentNode.removeChild(modal);
    }
}
function addWrong(elementId, message) {
    var wrongName = document.getElementById(elementId);
    var wrongText = document.createElement('p');
    wrongText.textContent = message;
    wrongText.style.color = 'red';

    wrongName.appendChild(wrongText);
}
function deleteWrongText() {
    var wrongName = document.getElementById('wrong-name');
    var wrongText = wrongName.querySelector('p');

    var wrongEmail = document.getElementById('wrong-email');
    var wrongEmailText = wrongEmail.querySelector('p');

    var wrongPassword = document.getElementById('wrong-password');
    var wrongPasswordText = wrongPassword.querySelector('p');

    if (wrongText){
        wrongName.removeChild(wrongText);
    }
    if (wrongEmailText) {
        wrongEmail.removeChild(wrongEmailText);
    }
    if (wrongPasswordText) {
        wrongPassword.removeChild(wrongPasswordText);
    }
}
function successfulChange(elementId, message) {
    var wrongName = document.getElementById(elementId);
    var wrongText = document.createElement('p');
    wrongText.textContent = message;
    wrongText.style.color = 'green';

    wrongName.appendChild(wrongText);
}

function isValidName(name) {
    const validName = name && name.trim().length > 1 && name.trim().length <= 30;
    return validName;
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
function isValidEmail(email) {
    if (email.trim().length > 30) return false;
    const emailRegex = /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i;
    return emailRegex.test(email);
}