var FRAME_WIDTH = 1280;
var FRAME_HEIGHT = 720;
var buttonElement = document.getElementById("buttons");
var textField = document.getElementById("textField");
var scoreField = document.getElementById("scoreField");
var imageElement = document.getElementById("imageDiv");
var enterNameElement = document.getElementById("submit");
var nameField = document.getElementById("nameField");
var highScoreElement = document.getElementById("highScore");
var submitButton = document.getElementById("submitButton");
var titleElement = document.getElementById("title");
var answer1btn = document.getElementById("answer1");
var answer2btn = document.getElementById("answer2");
var answer3btn = document.getElementById("answer3");
var answer4btn = document.getElementById("answer4");
var againbtn = document.getElementById("againButton");
var image1Element = document.getElementById("image1");
var image2Element = document.getElementById("image2");
var aboutText = document.getElementById("aboutText");
var wrongAnswers;
var change;
var playerName;
var image;
var httpRequest;
var swapto1;
var time;
var started;
var timeBonus;
var footerLogo = document.getElementById("footerLogo");
var githubLogo = document.getElementById("githubLogo");
var swap;
var httpReq;

answer1btn.addEventListener("click", answer1);
answer2btn.addEventListener("click", answer2);
answer3btn.addEventListener("click", answer3);
answer4btn.addEventListener("click", answer4);
submitButton.addEventListener("click", submit);
againbtn.addEventListener("click", restart);
nameField.addEventListener("keypress", function (e) {
    var key = e.which || e.keyCode;
    if (key === 13) { // Enter key
        submit();
    }
});
githubLogo.addEventListener("click", github);
footerLogo.addEventListener("click", swapImage);
function restart() {
    location.reload();
}
function github() {
    window.open("https://github.com/joonsson/Name-Memory");
}

window.onload = init;
console.ward = function () { }; // what warnings?

function init() {
    footerLogo.src = "../images/java.jpg";
    swap = false;
    timeBonus = 0;
    highScoreElement.classList.remove("hidden");
    againbtn.classList.add("hidden");
    enterNameElement.classList.remove("hidden");
    buttonElement.classList.add("hidden");
    textField.classList.add("hidden");
    scoreField.classList.add("hidden");
    imageElement.classList.add("hidden");
    wrongAnswers = 0;
}
function submit() {
    playerName = nameField.value;
    httpRequest = new XMLHttpRequest();
    if (!httpRequest) {
        alert('Giving up :( Cannot create an XMLHTTP instance');
        return false;
    }
    httpRequest.onreadystatechange = startController;
    httpRequest.open('POST', '');
    httpRequest.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    httpRequest.send("name=" + encodeURIComponent(playerName));
}
function startController() {
    if (httpRequest.readyState === XMLHttpRequest.DONE) {
        if (httpRequest.status === 200) {
            aboutText.classList.add("hidden");
            textField.classList.remove("hidden");
            scoreField.classList.remove("hidden");
            highScoreElement.classList.add("hidden");
            startGame();
        } else {
            alert('There was a problem with the request.');
        }
    }
}
function answer1() {
    httpRequest.onreadystatechange = answerController;
    httpRequest.open('POST', '');
    httpRequest.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    httpRequest.send("answer=" + encodeURIComponent(questionArray[0]));
}
function answer2() {
    httpRequest.onreadystatechange = answerController;
    httpRequest.open('POST', '');
    httpRequest.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    httpRequest.send("answer=" + encodeURIComponent(questionArray[1]));
}
function answer3() {
    httpRequest.onreadystatechange = answerController;
    httpRequest.open('POST', '');
    httpRequest.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    httpRequest.send("answer=" + encodeURIComponent(questionArray[2]));
}
function answer4() {
    httpRequest.onreadystatechange = answerController;
    httpRequest.open('POST', '');
    httpRequest.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    httpRequest.send("answer=" + encodeURIComponent(questionArray[3]));
}
function answerController() {
    if (httpRequest.readyState === XMLHttpRequest.DONE) {
        if (httpRequest.status === 200) {
            questionString = httpRequest.responseText;
            questionArray = new Array;
            questionArray = questionString.split(":");
            if (questionArray[5] == "y") {
                titleElement.classList.remove("incorrect");
                titleElement.classList.add("correct");
                titleElement.textContent = "Correct!";
                textField.textContent = questionArray[6] + " was correct";
                newQuestion(questionArray[0], questionArray[1], questionArray[2], questionArray[3], questionArray[4]);
            } else if (questionArray[5] == "n") {
                titleElement.classList.remove("correct");
                titleElement.classList.add("incorrect");
                titleElement.textContent = "Bad " + playerName + ", that's not their name!";
                timeBonus += 10000;
                wrongAnswers++;
                textField.textContent = questionArray[6] + " was incorrect, the correct answer was " + questionArray[7];
                newQuestion(questionArray[0], questionArray[1], questionArray[2], questionArray[3], questionArray[4]);
            } else if (questionArray[5] == "g") {
                titleElement.textContent = "Good job!";
                titleElement.classList.add("correct");
                titleElement.classList.remove("incorrect");
                againbtn.classList.remove("hidden");
                buttonElement.classList.add("hidden");
                scoreField.classList.add("hidden");
                var min = Math.floor(questionArray[7] / (1000 * 60));
                var sec = Math.floor((questionArray[7] % (1000 * 60)) / 1000);
                if (questionArray[6] == "y") {
                    textField.textContent = "New Highscore! You finished with " + min + "min and " + sec + "sec.";
                } else {
                    textField.textContent = "You finished with " + min + "min and " + sec + "sec.";
                }
            }
        } else {
            alert('There was a problem with the request.');
        }
    }
}

function startGame() {
    titleElement.textContent = "You can do it!";
    enterNameElement.classList.add("hidden");
    buttonElement.classList.remove("hidden");
    imageElement.classList.remove("hidden");
    questionString = httpRequest.responseText;
    questionArray = questionString.split(":");
    started = new Date().getTime();
    newQuestion(questionArray[0], questionArray[1], questionArray[2], questionArray[3], questionArray[4]);
}

function newQuestion(a1, a2, a3, a4, pic) {
    change = true;
    answer1btn.textContent = a1;
    answer2btn.textContent = a2;
    answer3btn.textContent = a3;
    answer4btn.textContent = a4;
    if (swapto1) {
        image1Element.src = pic;
        image2Element.classList.remove("opaque");
        image1Element.classList.add("opaque");
        swapto1 = !swapto1;
    } else {
        image2Element.src = pic;
        image1Element.classList.remove("opaque");
        image2Element.classList.add("opaque");
        swapto1 = !swapto1;
    }
}

setInterval(function () {
    time = new Date().getTime();
    var distance = (time - started) + timeBonus;
    var minutes = Math.floor(distance / (1000 * 60));
    var seconds = Math.floor((distance % (1000 * 60)) / 1000);
    scoreField.textContent = minutes + "m " + seconds + "s ";
}, 1000);

function swapImage() {
    if (swap) {
        swap = !swap;
        footerLogo.src = "../images/java.jpg";
    } else {
        swap = !swap;
        footerLogo.src = "../images/chocstraw.png";
    }
}