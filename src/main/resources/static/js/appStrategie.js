let sectorsList = document.querySelectorAll('.mySector');
let sectorsLabelList = document.querySelectorAll('.mySectorLabel');
let marketsList = document.querySelectorAll('.myMarket');
let myMarketLabel = document.querySelectorAll('.myMarketLabel');
let industriesList = document.querySelectorAll('.myIndustry');
let industryLabelList = document.querySelectorAll('.myIndustryLabel');
let wyszukiwanieLabelList = document.querySelectorAll('.myWyszukiwanieLabel');
let sortowanieLabelList = document.querySelectorAll('.mySortowanieLabel');
let marketButton = document.querySelector('#marketButton');
let sectorButton = document.querySelector('#sectorButton');
let industryButton = document.querySelector('#industryButton');
let spolkaNote = "";
let noteOld = "";
let wiersz = 0;
let noteNew = "";
let elementNoteTitle = "";
let countCheckedMarket = 0;
let countCheckedSector = 0;
let countCheckedIndustry = 0;


document.addEventListener('DOMContentLoaded', function () {

    // console.log(industryButton);
    // console.log(wyszukiwanieLabelList);
    selectSectorsList();
    selectMarketsList();
    selectIndustriesList();


    [].forEach.call(myMarketLabel, function (marketElement) {
        marketElement.addEventListener('click', function (event) {
            let index = 1;
            index = this.getAttribute('value');
            // console.log(index);
            $("#mojeZmiany").load('/amerykastrategie/find/' + index + '&' + 'market');
            console.log("Wysłano zapytanie: " + '/amerykastrategie/find/' + index + '&' + 'market');
            // let notatkaList = document.querySelectorAll('.myNote');
            // console.log(notatkaList);
            console.log(document.querySelector("#market" + index).checked);
            if (document.querySelector("#market" + index).checked) countCheckedMarket--;
            else countCheckedMarket++;
            console.log(countCheckedMarket);
            SprawdzZaznaczone("Market");
        })

    });

    [].forEach.call(sectorsLabelList, function (sectorElement) {
        sectorElement.addEventListener('click', function (event) {
            let index = 1;
            index = this.getAttribute('value');
            // console.log(index);
            $("#mojeZmiany").load('/amerykastrategie/find/' + index + '&' + 'sector');
            console.log("Wysłano zapytanie: " + '/amerykastrategie/find/' + index + '&' + 'sector');
            console.log(document.querySelector("#sector" + index).checked);
            if (document.querySelector("#sector" + index).checked) countCheckedSector--;
            else countCheckedSector++;
            console.log(countCheckedSector);
            SprawdzZaznaczone("Sector");
        })
    });

    [].forEach.call(industryLabelList, function (industryElement) {
        industryElement.addEventListener('click', function (event) {
            let index = 1;
            index = this.getAttribute('value');
            // console.log(index);
            $("#mojeZmiany").load('/amerykastrategie/find/' + index + '&' + 'industry');
            console.log("Wysłano zapytanie: " + '/amerykastrategie/find/' + index + '&' + 'industry');
            console.log(document.querySelector("#industry" + index).checked);
            if (document.querySelector("#industry" + index).checked) countCheckedIndustry--;
            else countCheckedIndustry++;
            console.log(countCheckedIndustry);
            SprawdzZaznaczone("Industry");
        })
    });

    [].forEach.call(wyszukiwanieLabelList, function (wyszukiwanieElement) {
        wyszukiwanieElement.addEventListener('click', function (event) {
            let val = "";
            val = this.getAttribute('value');
            // console.log(val);
            $("#mojeZmiany").load('/amerykastrategie/find/' + "-1" + '&' + val);
            console.log("Wysłano zapytanie: " + '/amerykastrategie/find/' + "-1" + '&' + val);
        })
    });

    [].forEach.call(sortowanieLabelList, function (sortowanieElement) {
        sortowanieElement.addEventListener('click', function (event) {
            let valueSortowanie = "";
            valueSortowanie = this.getAttribute('value');
            // console.log(valueSortowanie);
            $("#mojeZmiany").load('/amerykastrategie/find/' + "-2" + '&' + valueSortowanie);
            console.log("Wysłano zapytanie: " + '/amerykastrategie/find/' + "-2" + '&' + valueSortowanie);
        })
    });

})

function przeslijShowButton() {
    $("#mojeZmiany").load('/amerykastrategie/find/-3&');
    console.log("Wysłano zapytanie: " + '/amerykastrategie/find/-3&');
}


function startPopup(id) {
    wiersz = id;
    elementNoteTitle = document.querySelector('#note' + id);
    noteOld = elementNoteTitle.getAttribute("title");
    spolkaNote = document.querySelector('#note' + id).getAttribute("value");
    document.querySelector('#spolkaNote').innerText = spolkaNote;
    document.querySelector('#textNote').value = noteOld;
    // document.getElementById('textNote').autofocus = true;
    // document.getElementById('textNote').focus();
    location.href = "#popupNote";
}

function replaceAll(t, T, S) {
    var i;
    var newS;
    var dl = S.length;
    for (i = 0; i < dl; i++) {
        newS = S.replace(t, T);
        S = newS;
    }
    return newS;
}

function przeslijNote() {
    let tenWiersz = "#wierszTabeli" + wiersz;
    noteNew = document.formNote.textNote.value;
    console.log("Wysłano notatkę dla: " + spolkaNote + ", treści: " + noteNew);
    noteNew = replaceAll(" ", "QTTTQ", noteNew);
    if (noteNew === undefined) noteNew = "";
    $(tenWiersz).load('/amerykastrategie/note/' + wiersz + '&' + noteNew, function (response, status, http) {
        if (status == "success") {
            if (noteNew.length > 0 && !undefined) {
                elementNoteTitle.title = replaceAll("QTTTQ", " ", noteNew);
                elementNoteTitle.classList.remove("fa-comment");
                elementNoteTitle.classList.add("fa-comment-dots");
                elementNoteTitle.classList.add("isNote");
            } else {
                elementNoteTitle.title = "";
                elementNoteTitle.classList.remove("fa-comment-dots");
                elementNoteTitle.classList.remove("isNote");
                elementNoteTitle.classList.add("fa-comment");
            }
        }
    });
    location.href = "#";
}

// function selectSectorsListAuto() {
//     [].forEach.call(sectorsList, function (sectorElement) {
//         console.log("zrobiłem" + sectorElement);
//         sectorElement.classList.remove("hidden");
//     })
// };

function sendIndustry(index) {
    fetch("/amerykastrategie", {
        method: "put",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(industryList[index])
    }).then(res => {
        console.log(industryList[index]);
    })
};

function sendIndustry2(index) {
    fetch("/amerykastrategie/id?id=" + index)
        .then(res => {
            console.log(industryList[index]);
            console.log(res.body);
            // document.getElementById("mojeZmiany").innerHTML = res.prototype;
        });
};

function retrieveGuests(index) {
    // var url = '/amerykastrategie/id?id='+index;
    var url = '/amerykastrategie/id?id=' + index;

    if ($('#mojeZmiany').val() != '') {
        url = url + '/' + $('#mojeZmiany').val();
    }

    $("#mojeZmiany").load(url);
}

function updateEventCount(index) {
    $.get("/amerykastrategie/id?id=" + index, function (fragment) { // get from controller
        $("#mojeZmiany").replaceWith(fragment); // update snippet of page
    });
}

function loadData(index) {
    const xhr = new XMLHttpRequest();

    // xhr.addEventListener("load", function() {
    //     showUsers(xhr.response);
    //     showCars(xhr.response);
    //     showMovies(xhr.response);
    // });

    document.getElementById("mojeZmiany").innerHTML = xhr.open("GET", "/amerykastrategie/id?id=" + index, true);
    // xhr.send();
    ;

}

function Industry(id, name) {
    this.id = id;
    this.name = name;
};

function SprawdzZaznaczone(nameList) {
    if (nameList == "Market") {
        if (countCheckedMarket != 0) marketButton.innerHTML = nameList + "  (" + countCheckedMarket + ")";
        else marketButton.innerHTML = nameList;
    }
    if (nameList == "Sector") {
        if (countCheckedSector != 0) sectorButton.innerHTML = nameList + "  (" + countCheckedSector + ")";
        else sectorButton.innerHTML = nameList;
    }
    if (nameList == "Industry") {
        if (countCheckedIndustry != 0) industryButton.innerHTML = nameList + "  (" + countCheckedIndustry + ")";
        else industryButton.innerHTML = nameList;
    }
}

function selectMarketsList() {
    marketButton.addEventListener('click', function (event) {
        var ListInputMarket = document.querySelectorAll('.myMarket input');
        countCheckedMarket = 0;
        ListInputMarket.forEach(value => {
            if (value.checked) countCheckedMarket++;
        });
        console.log("Market zwybrane: " + countCheckedMarket);
        if (countCheckedMarket != 0) document.querySelector('#customSwitchMarket').checked = false;
        if (countCheckedMarket != 0) marketButton.innerHTML = "Market  (" + countCheckedMarket + ")";
        else marketButton.innerHTML = "Market";
        [].forEach.call(marketsList, function (marketElement) {
            if (marketElement.hidden == true) {
                marketElement.hidden = false;
            } else {
                marketElement.hidden = true;
            }
        });
        [].forEach.call(sectorsList, function (sectorElement) {
            sectorElement.hidden = true;
        });
        var ListInputSector = document.querySelectorAll('.mySector input');
        countCheckedSector = 0;
        ListInputSector.forEach(value => {
            if (value.checked) countCheckedSector++;
        });
        console.log("Sector zwybrane: " + countCheckedSector);
        if (countCheckedSector != 0) document.querySelector('#customSwitchSector').checked = true;
        if (countCheckedSector == 0) document.querySelector('#customSwitchSector').checked = false;
        if (countCheckedSector != 0) sectorButton.innerHTML = "Sector  (" + countCheckedSector + ")";
        else sectorButton.innerHTML = "Sector";

        [].forEach.call(industriesList, function (industryElement) {
            industryElement.hidden = true;
        });
        var ListInputIndustry = document.querySelectorAll('.myIndustry input');
        countCheckedIndustry = 0;
        ListInputIndustry.forEach(value => {
            if (value.checked) countCheckedIndustry++;
        });
        console.log("Industry zwybrane: " + countCheckedIndustry);
        if (countCheckedIndustry != 0) document.querySelector('#customSwitchIndustry').checked = true;
        if (countCheckedIndustry == 0) document.querySelector('#customSwitchIndustry').checked = false;
        if (countCheckedIndustry != 0) industryButton.innerHTML = "Industry  (" + countCheckedIndustry + ")";
        else industryButton.innerHTML = "Industry";
    });
};

function selectSectorsList() {
    sectorButton.addEventListener('click', function (event) {
        var ListInputSector = document.querySelectorAll('.mySector input');
        countCheckedSector = 0;
        ListInputSector.forEach(value => {
            if (value.checked) countCheckedSector++;
        });
        console.log("Sector zwybrane: " + countCheckedSector);
        if (countCheckedSector != 0) document.querySelector('#customSwitchSector').checked = false;
        if (countCheckedSector != 0) sectorButton.innerHTML = "Sector  (" + countCheckedSector + ")";
        else sectorButton.innerHTML = "Sector";
        [].forEach.call(sectorsList, function (sectorElement) {
            if (sectorElement.hidden == true) {
                sectorElement.hidden = false;
            } else {
                sectorElement.hidden = true;
            }
        });
        [].forEach.call(marketsList, function (marketElement) {
            marketElement.hidden = true;
        });
        var ListInputMarket = document.querySelectorAll('.myMarket input');
        countCheckedMarket = 0;
        ListInputMarket.forEach(value => {
            if (value.checked) countCheckedMarket++;
        });
        console.log("Market zwybrane: " + countCheckedMarket);
        if (countCheckedMarket != 0) document.querySelector('#customSwitchMarket').checked = true;
        if (countCheckedMarket == 0) document.querySelector('#customSwitchMarket').checked = false;
        if (countCheckedMarket != 0) marketButton.innerHTML = "Market  (" + countCheckedMarket + ")";
        else marketButton.innerHTML = "Market";

        [].forEach.call(industriesList, function (industryElement) {
            industryElement.hidden = true;
        });
        var ListInputIndustry = document.querySelectorAll('.myIndustry input');
        countCheckedIndustry = 0;
        ListInputIndustry.forEach(value => {
            if (value.checked) countCheckedIndustry++;
        });
        console.log("Industry zwybrane: " + countCheckedIndustry);
        if (countCheckedIndustry != 0) document.querySelector('#customSwitchIndustry').checked = true;
        if (countCheckedIndustry == 0) document.querySelector('#customSwitchIndustry').checked = false;
        if (countCheckedIndustry != 0) industryButton.innerHTML = "Industry  (" + countCheckedIndustry + ")";
        else industryButton.innerHTML = "Industry";
    });
};

function selectIndustriesList() {
    industryButton.addEventListener('click', function (event) {
        var ListInputIndustry = document.querySelectorAll('.myIndustry input');
        countCheckedIndustry = 0;
        ListInputIndustry.forEach(value => {
            if (value.checked) countCheckedIndustry++;
        });
        console.log("Industry zwybrane: " + countCheckedIndustry);
        if (countCheckedIndustry != 0) document.querySelector('#customSwitchIndustry').checked = false;
        if (countCheckedIndustry != 0) industryButton.innerHTML = "Industry  (" + countCheckedIndustry + ")";
        else industryButton.innerHTML = "Industry";
        [].forEach.call(industriesList, function (industryElement) {
            if (industryElement.hidden == true) {
                industryElement.hidden = false;
            } else {
                industryElement.hidden = true;
            }
        });
        [].forEach.call(marketsList, function (marketElement) {
            marketElement.hidden = true;
        });
        var ListInputMarket = document.querySelectorAll('.myMarket input');
        countCheckedMarket = 0;
        ListInputMarket.forEach(value => {
            if (value.checked) countCheckedMarket++;
        });
        console.log("Market zwybrane: " + countCheckedMarket);
        if (countCheckedMarket != 0) document.querySelector('#customSwitchMarket').checked = true;
        if (countCheckedMarket == 0) document.querySelector('#customSwitchMarket').checked = false;
        if (countCheckedMarket != 0) marketButton.innerHTML = "Market  (" + countCheckedMarket + ")";
        else marketButton.innerHTML = "Market";

        [].forEach.call(sectorsList, function (sectorElement) {
            sectorElement.hidden = true;
        });
        var ListInputSector = document.querySelectorAll('.mySector input');
        countCheckedSector = 0;
        ListInputSector.forEach(value => {
            if (value.checked) countCheckedSector++;
        });
        console.log("Sector zwybrane: " + countCheckedSector);
        if (countCheckedSector != 0) document.querySelector('#customSwitchSector').checked = true;
        if (countCheckedSector == 0) document.querySelector('#customSwitchSector').checked = false;
        if (countCheckedSector != 0) sectorButton.innerHTML = "Sector  (" + countCheckedSector + ")";
        else sectorButton.innerHTML = "Sector";
    });
};

// //    obsługa usuwania produktu
// [].forEach.call(buttonDeleteAll, function(buttonDelete) {
//     buttonDelete.addEventListener('click', function(event) {
//         let liToDelete = this.closest('li');
//         liToDelete.classList.toggle('hidden');
//         let index = toIndex(buttonDelete.getAttribute('value'));
//         changeProductList(index,'del');
//         sendProduct(index);
//     })
// });


// var selectProductBtn = product_Li.querySelector('.select-product-btn');
// selectProductBtn.addEventListener('click', function(){
//     if (products[index][2]=="")
//         products[index][2] = "btn-success";
//     else
//         products[index][2] = "";
//     this.classList.toggle('btn-success');
//     if (products[index][1]==0) additionButton();
// });

// [].forEach.call(buttonSelectAll, function(buttonSelec) {
//     buttonSelec.addEventListener('click', function(event) {
//         console.log(event);
//   })
// });

//
//     position = "#add333";
//
//     // runnig nav
//     $(document).ready(function() {
//         let NavY = $('.myNav').offset().top;
//         let stickyNav = function(){
//             let ScrollY = $(window).scrollTop();
//             if (ScrollY > NavY) {
//                 $('.myNav').addClass('sticky');
//             } else {
//                 $('.myNav').removeClass('sticky');
//             }
//         };
//         stickyNav();
//         $(window).scroll(function() {
//             stickyNav();
//         });
//     });
//
//     // Show products
//     if(shoppingList) {
//         showListProducts();
//         textH2="Lista zakupów";
//         if (categoryName=="Wszystkie"){
//             textH2="Lista zakupów";
//             h2.innerHTML = textH2;
//             document.querySelector('#making').innerHTML="Powrót do tworzenia listy";
//         } else {
//             textH2="Lista zakupów, kategria: ";
//             h2.innerHTML = textH2 + categoryName;
//         }
//     } else {
//         showProducts();
//     }
//
//     if(optionView) {
//         settingStyle.style.display="flex";
//     } else {
//         settingStyle.style.display="none";
//     }
//
//     // Show categories
//     showCategories();
//
//     addProductToList();
//     addCategoryToList();
//
//     saveListProducts();
//     makeListProducts();
//
//
//     [].forEach.call(swapAll, function(swap) {
//         swap.addEventListener('mouseover', () => {
//             swap.disabled = true;
//             setTimeout( () => {
//                 swap.disabled = false;
//             }, 2500 );
//         });
//     });
//
//     // czyszczenie listy zakupów
//     cleanListProducts();
//
//     // czyszczenie wszystkich danych w app
//     cleanAllApp();
//
//     // rozwijany header
//     upOptionList();
//     downOptionList();
//
//     // Listener dla elementu 'pozostałe'
//     //wyświetla produkty nie przypisane do katedorii
//     // document.querySelector("#other").addEventListener("click",function(event){
//     //     event.preventDefault();
//     //     showProductsOther();
//     // });
//
//     h2.innerHTML = textH2 + categoryName;
//     if (optionView) {
//         divDown.classList.add("hidden");
//         divUp.classList.remove("hidden");
//         headerStyle.classList.add("optionYes");
//         headerStyle.classList.remove("optionNo");
//         settingStyle.style.display = "flex";
//     }
//
//
//     // let buttonSelectAll = document.querySelectorAll(".lista button");//.querySelectorAll('.select-product-btn button');
//
//     // console.log(buttonSelectAll);
//     // console.log(downOptionAll);
//
//     // [].forEach.call(buttonSelectAll, function(buttonSelect) {
//     //     // console.log(buttonSelect);
//     //         buttonSelect.addEventListener('click', function(event) {
//     //             console.log(event.explicitOriginalTarget.offsetParent.attributes[1]);
//     //       })
//     //     });
//
//
// });
//
// function upOptionList(){
//     [].forEach.call(upOptionAll, function(upOption) {
//         upOption.addEventListener('click', function(event) {
//             divUp.classList.add("hidden");
//             divDown.classList.remove("hidden");
//             headerStyle.classList.add("optionNo");
//             headerStyle.classList.remove("optionYes");
//             settingStyle.style.display = "none";
//             optionView = false;
//         })
//     })
// };
//
// function downOptionList(){
//     [].forEach.call(downOptionAll, function(downOption) {
//         downOption.addEventListener('click', function(event) {
//             divDown.classList.add("hidden");
//             divUp.classList.remove("hidden");
//             headerStyle.classList.add("optionYes");
//             headerStyle.classList.remove("optionNo");
//             settingStyle.style.display = "flex";
//             divUp.style.display = "flex";
//             optionView = true;
//         })
//     })
// };


//
// function trim(text){
//     temp = "";
//     while(text.indexOf(" ")==0){
//         for (j=1; j<text.length; j++) temp += text[j];
//         text = temp;
//         temp = "";
//     }
//     text = text.toUpperCase().charAt(0) + text.substr(1, text.length);
//     return text;
// };