function verify() {
    const password1 = document.forms['form']['password'].value;
    const password2 = document.forms['form']['verifyPassword'].value;
    const errorElement = document.getElementById("error");

    if (password1.length < 7) {
        errorElement.innerHTML = "Password must be at least 7 characters long";
        return false;
    }

    if (password2 === null) {
        errorElement.innerHTML = "Please enter a password";
        return false;
    }
    
    if (password1 !== password2) {
        errorElement.innerHTML = "Please check your passwords";
        return false;
    }
}