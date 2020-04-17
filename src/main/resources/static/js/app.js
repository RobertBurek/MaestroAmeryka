let sectorsList = document.querySelectorAll('.mySector');
let sectorsLabelList = document.querySelectorAll('.mySectorLabel');
let marketsList = document.querySelectorAll('.myMarket');
let myMarketLabel = document.querySelectorAll('.myMarketLabel');
let industriesList = document.querySelectorAll('.myIndustry');
let industryLabelList = document.querySelectorAll('.myIndustryLabel');
let wyszukiwanieLabelList = document.querySelectorAll('.myWyszukiwanieLabel');
let sortowanieLabelList = document.querySelectorAll('.mySortowanieLabel');
let sectorButton = document.querySelector('#sectorButton');
let marketButton = document.querySelector('#marketButton');
let industryButton = document.querySelector('#industryButton');


document.addEventListener('DOMContentLoaded', function () {

    console.log(sectorsList);
    console.log(sectorButton);
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
        })
    });

    [].forEach.call(sectorsLabelList, function (sectorElement) {
        sectorElement.addEventListener('click', function (event) {
            let index = 1;
            index = this.getAttribute('value');
            // console.log(index);
            $("#mojeZmiany").load('/amerykastrategie/find/' + index + '&' + 'sector');
            console.log("Wysłano zapytanie: "+'/amerykastrategie/find/' + index + '&' + 'sector');
        })
    });

    [].forEach.call(industryLabelList, function (industryElement) {
        industryElement.addEventListener('click', function (event) {
            let index = 1;
            index = this.getAttribute('value');
            // console.log(index);
            $("#mojeZmiany").load('/amerykastrategie/find/' + index + '&' + 'industry');
            console.log("Wysłano zapytanie: "+'/amerykastrategie/find/' + index + '&' + 'industry');
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

function selectSectorsList() {
    sectorButton.addEventListener('click', function (event) {
        [].forEach.call(sectorsList, function (sectorElement) {
            if (sectorElement.hidden == true) {
                sectorElement.hidden = false;
            } else {
                sectorElement.hidden = true;
            }
        })
    })
};

function selectMarketsList() {
    marketButton.addEventListener('click', function (event) {
        [].forEach.call(marketsList, function (marketElement) {
            if (marketElement.hidden == true) {
                marketElement.hidden = false;
            } else {
                marketElement.hidden = true;
            }
        })
    })
};

function selectIndustriesList() {
    industryButton.addEventListener('click', function (event) {
        [].forEach.call(industriesList, function (industryElement) {
            if (industryElement.hidden == true) {
                industryElement.hidden = false;
            } else {
                industryElement.hidden = true;
            }
        })
    })
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