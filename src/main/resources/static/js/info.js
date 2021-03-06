document.addEventListener('DOMContentLoaded', function () {
    $.getJSON('http://www.geoplugin.net/json.gp?jsoncallback=?', function (data) {
        // console.log(JSON.stringify(data, null, 2));
        infoJSON = JSON.stringify(data, null, 2);
        const info = JSON.parse(infoJSON);
        const infoCITY = info.geoplugin_city;
        const infoIP = info.geoplugin_request;
        const infoCountry = info.geoplugin_countryName;
        $.ajax({
            url: '/info/' + infoIP + "&" + infoCITY + "&" + infoCountry,
            type: 'PUT'
        });
    });
});