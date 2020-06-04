function validate() {
    if (document.formRejestracja.username.value == "" && document.formRejestracja.password.value == "") {
        alert("Username and password are required");
        document.formRejestracja.username.focus();
        return false;
    }
    if (document.formRejestracja.username.value == "") {
        alert("Username is required");
        document.formRejestracja.username.focus();
        return false;
    }
    if (document.formRejestracja.password.value == "") {
        alert("Password is required");
        document.formRejestracja.password.focus();
        return false;
    }
}