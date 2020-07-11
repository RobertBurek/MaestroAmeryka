function przeslijShowButton() {
    $("#mojeZmiany").load('/amerykastrategie/find/-3&');
    console.log("Wysłano ALL/LESS: " + '/amerykastrategie/find/-3&');
};


function przeslijShow(id) {
    wiersz = id;
    let position = "#position" + wiersz;
    showElement = document.querySelector("#show" + id);
    let widok = showElement.getAttribute('value');
    console.log("Wysłano " + widok + " dla showAll/Mine dla wiersza: " + wiersz);
    $(position).load('/amerykaspolka/show/' + id + '&' + widok, function (response, status, http) {
        if (status == "success") {
            if (widok=="false" ) {
                showElement.setAttribute('value',true);
                showElement.classList.remove("fa-minus");
                showElement.classList.remove("myRed");
                showElement.classList.add("fa-plus");
                showElement.classList.add("myGreen");
            } else {
                showElement.setAttribute('value',false);
                showElement.classList.remove("fa-plus");
                showElement.classList.remove("myGreen");
                showElement.classList.add("fa-minus");
                showElement.classList.add("myRed");
            }
        }
    });
    location.href = "#";
};