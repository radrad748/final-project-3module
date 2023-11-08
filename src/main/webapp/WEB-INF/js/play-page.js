var labels = document.querySelectorAll('label');
var button = document.querySelector('.div-btn input[type="submit"]');
var divAnswers = document.querySelector('.divAnswers');
var radioInputs = divAnswers.querySelectorAll('input[type="radio"]');
var url = '/accept-answer?selectedValue=';
var urlForm = '/next-question?selectedValue=';
var count = 0;
document.addEventListener('DOMContentLoaded', function () {
    var form = document.getElementById('myForm');

    form.addEventListener('submit', function (event) {
        event.preventDefault();
        includeInputs(radioInputs);
        var selectedValue = document.querySelector('input[name="group"]:checked').value;
        labels.forEach(function (label) {
            label.style.color = '';
        });
        button.value = 'ОТВЕТИТЬ';
        url += selectedValue;
        urlForm += selectedValue;
        deleteDivText(divAnswers);
        if (selectedValue !== '') {
            if (count == 0) {
                disableInputs(radioInputs);

                fetch(url)
                    .then(response => {
                        if (!response.ok) {
                            throw new Error('Ошибка сети: ' + response.status);
                        }
                        return response.json();
                    })
                    .then(date => {
                        if (date.answerIs) {
                            trueText(labels, date, button, divAnswers);
                            count = 1;
                        } else {
                            falseText(labels, date, button, divAnswers);
                            count = 1;
                        }
                    })
                    .catch(error => {
                        console.error('Ошибка: ' + error.message);
                    })
            } else {
                count = 0;
                var form = document.getElementById('myForm');
                form.method = 'get';
                form.action = urlForm;
                form.submit();
            }
        }
    });
});

/*
var url = '/next-question' + '?selectedValue=' + selectedValue;

                fetch(url)
                    .then(response => {
                        if (!response.ok) {
                            throw new Error('Ошибка сети: ' + response.status);
                        }
                        return response.text();
                    })
                    .then(date => {
                        count = 0;
                        location.reload(true);
                    })
                    .catch(error => {
                        console.error('Ошибка: ' + error.message);
                    })
 */

function trueText(labels, date, button, divAnswers) {
    labels[date.trueAnswer].style.color = "#64c25d";
    button.value = 'ДАЛЕЕ';

    var newDivAnswer = document.createElement('div');
    newDivAnswer.className = 'div-answer';

    var pElement = document.createElement('p');
    pElement.style.color = '#0b5705';
    pElement.textContent = date.response;

    newDivAnswer.appendChild(pElement);
    divAnswers.appendChild(newDivAnswer);
}
function falseText(labels, date, button, divAnswers) {
    labels[date.trueAnswer].style.color = "#64c25d";
    labels[date.wrongAnswer].style.color = "#da5656";
    button.value = 'ДАЛЕЕ';

    var newDivAnswer = document.createElement('div');
    newDivAnswer.className = 'div-answer';

    var pElement = document.createElement('p');
    pElement.style.color = 'red';
    pElement.textContent = date.response;

    newDivAnswer.appendChild(pElement);
    divAnswers.appendChild(newDivAnswer);
}
function deleteDivText(divAnswers) {
    var newDivAnswer = document.querySelector('.div-answer');
    if (newDivAnswer) divAnswers.removeChild(newDivAnswer);
}
function disableInputs(radioInputs) {
    radioInputs.forEach(function (radioInput) {
        radioInput.disabled = true;
    });
}
function includeInputs(radioInputs) {
    radioInputs.forEach(function (radioInput) {
        radioInput.disabled = false;
    });
}