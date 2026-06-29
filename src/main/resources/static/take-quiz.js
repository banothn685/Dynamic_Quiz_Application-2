function startQuiz() {

    // Hide Start Button
    document.getElementById("startSection").style.display = "none";

    // Show Quiz
    document.getElementById("quizForm").style.display = "block";

    // Start Timer
    startTimer();
}

let totalSeconds = 600; // 10 Minutes

function startTimer() {

    let timer = document.getElementById("timer");

    let countdown = setInterval(function () {

        let minutes = Math.floor(totalSeconds / 60);
        let seconds = totalSeconds % 60;

        timer.innerHTML =
            minutes + ":" + (seconds < 10 ? "0" + seconds : seconds);

        totalSeconds--;

        if (totalSeconds < 0) {

            clearInterval(countdown);

            alert("Time Over! Quiz will be submitted.");

            document.getElementById("quizForm").submit();
        }

    }, 1000);

}