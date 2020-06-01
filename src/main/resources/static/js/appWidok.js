function przeslijShowButton() {
    $("#mojeZmiany").load('/amerykastrategie/find/-3&');
    console.log("Wysłano zapytanie: " + '/amerykastrategie/find/-3&');
};


function przeslijShow(id, widok) {
    wiersz = id;
    let position = "#position" + wiersz;
    showElement = document.querySelector("#show" + id);
    console.log("Wysłano " + widok + " dla showAll/Mine dla wiersza: " + wiersz);
    console.log(showElement);
    $(position).load('/amerykaspolka/show/' + id + '&' + widok, function (response, status, http) {
        if (status == "success") {
            if (widok) {
                showElement.classList.remove("fa-minus");
                showElement.classList.remove("myRed");
                showElement.classList.add("fa-plus");
                showElement.classList.add("myGreen");
            } else {
                showElement.classList.remove("fa-plus");
                showElement.classList.remove("myGreen");
                showElement.classList.add("fa-minus");
                showElement.classList.add("myRed");
            }
        }
    });
    location.href = "#";
};