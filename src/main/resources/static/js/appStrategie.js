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

    selectSectorsList();
    selectMarketsList();
    selectIndustriesList();

    [].forEach.call(myMarketLabel, function (marketElement) {
        marketElement.addEventListener('click', function (event) {
            let index = 1;
            index = this.getAttribute('value');
            $("#mojeZmiany").load('/amerykastrategie/find/' + index + '&' + 'market');
            console.log("Wysłano zapytanie: " + '/amerykastrategie/find/' + index + '&' + 'market');
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
            $("#mojeZmiany").load('/amerykastrategie/find/' + "-1" + '&' + val);
            console.log("Wysłano zapytanie: " + '/amerykastrategie/find/' + "-1" + '&' + val);
        })
    });

    [].forEach.call(sortowanieLabelList, function (sortowanieElement) {
        sortowanieElement.addEventListener('click', function (event) {
            let valueSortowanie = "";
            valueSortowanie = this.getAttribute('value');
            $("#mojeZmiany").load('/amerykastrategie/find/' + "-2" + '&' + valueSortowanie);
            console.log("Wysłano zapytanie: " + '/amerykastrategie/find/' + "-2" + '&' + valueSortowanie);
        })
    });
});


function przeslijShowButton() {
    $("#mojeZmiany").load('/amerykastrategie/find/-3&');
    console.log("Wysłano zapytanie: " + '/amerykastrategie/find/-3&');
};


function startPopup(id) {
    wiersz = id;
    elementNoteTitle = document.querySelector('#note' + id);
    noteOld = elementNoteTitle.getAttribute("title");
    spolkaNote = document.querySelector('#note' + id).getAttribute("value");
    document.querySelector('#spolkaNote').innerText = spolkaNote;
    document.querySelector('#textNote').value = noteOld;
    location.href = "#popupNote";
};


function replaceAll(t, T, S) {
    var i;
    var newS;
    var dl = S.length;
    for (i = 0; i < dl; i++) {
        newS = S.replace(t, T);
        S = newS;
    }
    return newS;
};


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
};


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
};


function selectMarketsList() {
    marketButton.addEventListener('click', function (event) {
        var ListInputMarket = document.querySelectorAll('.myMarket input');
        countCheckedMarket = 0;
        ListInputMarket.forEach(value => {
            if (value.checked) countCheckedMarket++;
        });
        console.log("Market wybrane: " + countCheckedMarket);
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
        console.log("Sector wybrane: " + countCheckedSector);
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
        console.log("Industry wybrane: " + countCheckedIndustry);
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
        console.log("Sector wybrane: " + countCheckedSector);
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
        console.log("Market wybrane: " + countCheckedMarket);
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
        console.log("Industry wybrane: " + countCheckedIndustry);
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
        console.log("Industry wybrane: " + countCheckedIndustry);
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
        console.log("Market wybrane: " + countCheckedMarket);
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
        console.log("Sector wybrane: " + countCheckedSector);
        if (countCheckedSector != 0) document.querySelector('#customSwitchSector').checked = true;
        if (countCheckedSector == 0) document.querySelector('#customSwitchSector').checked = false;
        if (countCheckedSector != 0) sectorButton.innerHTML = "Sector  (" + countCheckedSector + ")";
        else sectorButton.innerHTML = "Sector";
    });
};