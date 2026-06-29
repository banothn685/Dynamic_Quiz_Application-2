function togglePassword() {

    let password = document.getElementById("password");
    let eye = document.getElementById("eye");

    if(password.type === "password"){

        password.type = "text";

        eye.classList.remove("fa-eye-slash");
        eye.classList.add("fa-eye");

    }else{

        password.type = "password";

        eye.classList.remove("fa-eye");
        eye.classList.add("fa-eye-slash");
    }
}

function toggleConfirmPassword() {
    let confirmPassword = document.getElementById("confirmPassword");

    if (confirmPassword.type === "password") {
        confirmPassword.type = "text";
    } else {
        confirmPassword.type = "password";
    }
}
function showAdminCode() {
    const role = document.getElementById("role").value;
    const adminCodeBox = document.getElementById("adminCodeBox");

    if (role === "ADMIN") {
        adminCodeBox.style.display = "block";
    } else {
        adminCodeBox.style.display = "none";
    }
}
function checkPassword() {

    let password = document.getElementById("password").value;
    let confirmPassword = document.getElementById("confirmPassword").value;
    let error = document.getElementById("passwordError");


	let rules = document.getElementById("passwordRules");

	if(password.length==0){
	    rules.style.display="none";
	}else{
	    rules.style.display="block";
	}

	
    // Password rules
    let upper = /[A-Z]/;
    let lower = /[a-z]/;
    let number = /[0-9]/;
    let symbol = /[!@#$%^&*(),.?":{}|<>]/;

    updateRule("upper", upper.test(password));
    updateRule("lower", lower.test(password));
    updateRule("number", number.test(password));
    updateRule("symbol", symbol.test(password));
    updateRule("length", password.length >= 8);

    // Confirm password check
    if (confirmPassword !== "" && password !== confirmPassword) {
        error.innerHTML = "❌ Password and Confirm Password must be the same.";
        error.style.color = "red";
        return false;
    } else {
        error.innerHTML = "";
        return true;
    }
}



function updateRule(id, valid){

    let rule = document.getElementById(id);

    if(valid){
        rule.style.display = "none";
    }else{
        rule.style.display = "block";
    }
}