const signUpButton = document.getElementById('signUp');
const signInButton = document.getElementById('signIn');
const container = document.getElementById('container');

signUpButton.addEventListener('click', () => {
    container.classList.add("right-panel-active");
});

signInButton.addEventListener('click', () => {
    container.classList.remove("right-panel-active");
});

function validEmailAddressSignIn() {
    // Regular expression for email validation
    // Source: https://emailregex.com/

    const email = document.getElementById('input-email-login');
    const emailRegex = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

    if (emailRegex.test(email.value) || email.value === "") {
        email.classList.remove('invalid-email');
    }
    else {
        email.classList.add('invalid-email');
    }

}

function validEmailAddressSignUp() {
    // Regular expression for email validation
    // Source: https://emailregex.com/

    const email = document.getElementById('input-email-register');
    const emailRegex = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

    if (emailRegex.test(email.value) || email.value === "") {
        email.classList.remove('invalid-email');
    }
    else {
        email.classList.add('invalid-email');
    }
}