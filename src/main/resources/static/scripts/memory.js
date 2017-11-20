var buttonElement = document.getElementById("buttons");
var textField = document.getElementById("textField");
var scoreField = document.getElementById("scoreField");
var score = document.getElementById("score");
var correctField = document.getElementById("correct");
var imageElement = document.getElementById("imageDiv");
var enterNameElement = document.getElementById("submitField");
var submitSection = document.getElementById("submit");
var nameField = document.getElementById("nameField");
var highScoreElement = document.getElementById("highScore");
var bothBtn = document.getElementById("bothButton");
var javaBtn = document.getElementById("javaButton");
var dotnetBtn = document.getElementById("dotnetButton");
var answer1btn = document.getElementById("answer1");
var answer2btn = document.getElementById("answer2");
var answer3btn = document.getElementById("answer3");
var answer4btn = document.getElementById("answer4");
var againbtn = document.getElementById("againButton");
var again = document.getElementById("again");
var image1Element = document.getElementById("image1");
var image2Element = document.getElementById("image2");
var aboutText = document.getElementById("textBox");
var wrongAnswers;
var change;
var playerName;
var httpRequest;
var swapto1;
var started;
var timeBonus;
var githubLogo = document.getElementById("githubLogo");
var klass;

answer1btn.addEventListener("click", answer1);
answer2btn.addEventListener("click", answer2);
answer3btn.addEventListener("click", answer3);
answer4btn.addEventListener("click", answer4);
bothBtn.addEventListener("click", submitBoth);
javaBtn.addEventListener("click", submitJava);
dotnetBtn.addEventListener("click", submitCsharp);
againbtn.addEventListener("click", restart);
githubLogo.addEventListener("click", github);
function restart() {
    location.reload();
}
function github() {
    window.open("https://github.com/joonsson/Name-Memory");
}

window.onload = init;
console.ward = function () { }; // what warnings?

function init() {
    timeBonus = 0;
    highScoreElement.classList.remove("hidden");
    again.classList.add("hidden");
    submitSection.classList.remove("hidden");
    enterNameElement.classList.add("hidden");
    buttonElement.classList.add("hidden");
    textField.classList.add("hidden");
    scoreField.classList.add("hidden");
    imageElement.classList.add("hidden");
    wrongAnswers = 0;
}
function submitBoth() {
    klass = "both";
    submit();
}
function submitJava() {
    klass = "java";
    submit();
}
function submitCsharp() {
    klass = "csharp";
    submit();
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
    httpRequest.send("name=" + encodeURIComponent(playerName) + "&klass=" + encodeURIComponent(klass));
}
function startController() {
    if (httpRequest.readyState === XMLHttpRequest.DONE) {
        if (httpRequest.status === 200) {
            aboutText.classList.add("hidden");
            textField.classList.remove("hidden");
            scoreField.classList.remove("hidden");
            highScoreElement.classList.add("hidden");
            submitSection.classList.add("hidden");
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
                correctField.classList.remove("incorrect");
                correctField.classList.add("correct");
                correctField.innerHTML = "Correct!";
                textField.innerHTML = questionArray[6] + " was correct";
                newQuestion(questionArray[0], questionArray[1], questionArray[2], questionArray[3], questionArray[4]);
            } else if (questionArray[5] == "n") {
                wrongAnswers++;
                score.innerHTML = wrongAnswers + " wrong answers";
                correctField.classList.remove("correct");
                correctField.classList.add("incorrect");
                correctField.innerHTML = "No, that's not their name!";
                timeBonus += 10000;
                textField.innerHTML = questionArray[6] + " was incorrect, the correct answer was " + questionArray[7];
                newQuestion(questionArray[0], questionArray[1], questionArray[2], questionArray[3], questionArray[4]);
            } else if (questionArray[5] == "g") {
                correctField.innerHTML = "Excellent!";
                correctField.classList.add("correct");
                correctField.classList.remove("incorrect");
                again.classList.remove("hidden");
                buttonElement.classList.add("hidden");
                score.innerHTML="";
                if (questionArray[7] < 10) {
                    textField.innerHTML = "Well done! You finished with " + questionArray[7] + " wrong answers.";
                } else if (questionArray[7] < 20) {
                    textField.innerHTML = "Good job.. I guess.. You finished with " + questionArray[7] + " wrong answers.";
                } else {
                    textField.innerHTML = "Do you even know these people? You finished with " + questionArray[7] + " wrong answers.";
                }
            }
        } else {
            alert('There was a problem with the request.');
        }
    }
}

function startGame() {
    score.innerHTML = wrongAnswers + " wrong answers";
    correctField.innerHTML = "You can do it!";
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

/*setInterval(function () {
    time = new Date().getTime();
    var distance = (time - started) + timeBonus;
    var minutes = Math.floor(distance / (1000 * 60));
    var seconds = Math.floor((distance % (1000 * 60)) / 1000);
    scoreField.textContent = minutes + "m " + seconds + "s ";
}, 1000);*/