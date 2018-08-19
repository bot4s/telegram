function TelegramApi () {
    this.tProxy = 'TelegramGameProxy' in window;
    this.setup();
};

TelegramApi.prototype.setup = function () {
    this.payload = null;

    if ('search' in document.location) {
        var search = document.location.search.split(/payload=([a-zA-Z0-9\.\-]+)/);
        var token = search.length > 1 ? search[1] : null;

        if (token !== null) {
            try {
                this.payload = JSON.parse( atob(token) );
                this.user = this.payload.user.id;
                this.im_id = this.payload.chat_id +
                    ":" + this.payload.message_id +
                    ":" + this.payload.inline_message_id;
                this.endpoint = this.payload.game_manager_host + "/games/api/";
            } catch (e) {
                return console.error('Error parsing token: ' + e.message);
            };

            this.token = token;
        };
    };
};

TelegramApi.prototype.request = function (method, uri, data, callback_ok, callback_error) {
    var xhr = new window.XMLHttpRequest();
    var data = JSON.stringify(data) || '{}';

    if (typeof callback_ok != 'undefined') {
        xhr.onreadystatechange = function () {
            if (xhr.readyState == 4 && xhr.status >= 200 && xhr.status < 300) {
                return callback_ok(JSON.parse(xhr.responseText));
            };

            if (typeof callback_error !== 'undefined') {
                return callback_error(xhr);
            };
        };
    };

    xhr.open(method, this.endpoint + uri, true);
    xhr.setRequestHeader('Content-Type', 'application/json; charset=UTF-8');
    xhr.send(data);
};

TelegramApi.prototype.updateScore = function (score) {
    if (parseInt(score, 10) < 1) {
        return console.log('Invalid score.');
    };

    return this.request('POST', 'setScore?score=' + score, {});
};

TelegramApi.prototype.getHighScores = function (cb_ok) {
    return this.request('GET', 'getScores', {}, cb_ok);
};

TelegramApi.prototype.share = function () {
    if (! this.tProxy) {
        return console.error('TelegramGameProxy library is not loaded.');
    };

    return window.TelegramGameProxy.shareScore();
};
