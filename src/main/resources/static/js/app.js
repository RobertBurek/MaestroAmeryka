// let productsContainer = document.querySelector('.products-container ul');
// let categoriesContainer = document.querySelector('.family-container ul');
// let saveListProductsForm = document.querySelector('.save-new-product-container form');
// let makeListProductsForm = document.querySelector('.make-new-product-container form');
// let newProductForm = document.querySelector('#addProduct form');
// let newCategoryForm = document.querySelector('#addCategory form');
// let upOptionAll = document.querySelectorAll('.up button');
// let divUp = document.querySelector('.up');
// let downOptionAll = document.querySelectorAll('.down button');
// let divDown = document.querySelector('.down');
// let headerStyle = document.querySelector('header');
// let settingStyle = document.querySelector('.setting');
// let h2 = document.querySelector('h2');
// let textH2 = 'Lista towarów, kategoria: ';
// let swapAll = document.querySelectorAll('.btn--swap');
// let cleanList = document.querySelector('#cleanList');
// let cleanAll = document.querySelector('#cleanAll');
let sectorsList = document.querySelectorAll('.mySector');
let marketsList = document.querySelectorAll('.myMarket');
let industriesList = document.querySelectorAll('.myIndustry');
let industryLabelList = document.querySelectorAll('.myIndustryLabel');
let sectorButton = document.querySelector('#sectorButton');
let marketButton = document.querySelector('#marketButton');
let industryButton = document.querySelector('#industryButton');


document.addEventListener('DOMContentLoaded', function () {

    console.log(sectorsList);
    console.log(sectorButton);
    selectSectorsList();
    selectMarketsList();
    selectIndustriesList();

    [].forEach.call(sectorsList, function (sectorElement) {
        sectorElement.addEventListener('click', function (event) {
            let index = 1;
            index = this.getAttribute('value');
            $("#mojeZmiany").load('/amerykastrategie/sector/' + index);
        })
    });

    [].forEach.call(industryLabelList, function (industryElement) {
        industryElement.addEventListener('click', function (event) {
            // sendIndustry2(17);
            // loadData(17);
            // loca/tion.href="/amerykastrategie/17";
            // document.getElementById("mojeZmiany").innerHTML=
            //     updateEventCount(17);
            let index = 1;
            // console.log(this.getAttribute('value'));
            // console.log(event.value);
            // console.log(event);
            // console.log(industryElement.value());
            index = this.getAttribute('value');
            // console.log(index);
            // let url = '/amerykastrategie/id?id='+index;
            // url = url + '/' + $('#mojeZmiany').val();
            // $("#mojeZmiany").load(url);
            $("#mojeZmiany").load('/amerykastrategie/id?id=' + index);

            // retrieveGuests(17);
        })
    });

    // [].forEach.call(sectorButton, function(swap) {
    //     swap.addEventListener('mouseover', () => {
    //         // selectSectorsListAuto();
    //         console.log("no i co?");
    //         // swap.disabled = true;
    //         // setTimeout( () => {
    //         //     swap.disabled = false;
    //         // }, 2500 );
    //     });
    // });

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