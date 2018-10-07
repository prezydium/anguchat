var app = angular.module('anguchatApp', ['ui.router']);

angular.module('anguchatApp').component('chatroom', {
    templateUrl: 'chat.html',

    controller: function ($scope, $interval, $http) {

        $scope.autoScroll = {isOn: true};

        var stompClient = null;

        var chatRoom = document.getElementById('chat-room');

        function connect() {
            var socket = new SockJS('/chatsoc');
            stompClient = Stomp.over(socket);
            stompClient.connect({}, function (frame) {
                console.log('Connected: ' + frame);
                stompClient.subscribe('/topic/messages', function (messageOutput) {
                    showMessageOutput(JSON.parse(messageOutput.body));
                });
            });
        }

        this.$onDestroy = function disconnect() {
            if (stompClient != null) {
                stompClient.disconnect();
            }
            setConnected(false);
            console.log("Disconnected");
        };

        function sendMessage() {
            if ($scope.nick == null || $scope.nick === "") {
                $scope.errorMsg = "Please enter nick";
                document.getElementById("errors").style.display = 'block';
            } else if ($scope.msgText == null || $scope.msgText === "") {
                $scope.errorMsg = "Message cannot be empty";
                document.getElementById("errors").style.display = 'block';
            } else {
                stompClient.send("/app/chatsoc", {},
                    JSON.stringify({'nick': $scope.nick, 'msgText': $scope.msgText}));
                document.getElementById("errors").style.display = 'none';
                $scope.msgText = "";

            }
        }

        function showMessageOutput(messageOutput) {
            var chat = document.getElementById('chat-room');
            var p = document.createElement('p');
            p.appendChild(document.createTextNode(messageOutput.nick + ": "
                + messageOutput.msgText + " (" + messageOutput.time + ")"));
            chat.appendChild(p);
        }

        function sendCat() {
            if ($scope.nick == null || $scope.nick === "") {
                $scope.errorMsg = "Please enter nick";
                document.getElementById("errors").style.display = 'block';
            } else {
                stompClient.send("/app/chatsoc", {},
                    JSON.stringify({'nick': $scope.nick, 'msgText': '🐱'}));
                document.getElementById("errors").style.display = 'none';
                $scope.msgText = "";
            }
        }

        function getHistory() {
            $http({
                method: "GET",
                url: "history",
                data: {}
            }).then(function ok(value) {
                    var chat = document.getElementById('chat-room');
                    while(chat.firstChild){
                        chat.removeChild(chat.firstChild);
                    }
                    var messages = value.data;
                    for (var i = 0; i < messages.length; i++) {
                        var p = document.createElement('p');
                        p.appendChild(document.createTextNode(messages[i].nick + ": "
                            + messages[i].msgText + " (" + messages[i].time + ")"));
                        chat.appendChild(p)
                    }
                },
                function notOK(reason) {
                    $scope.result = "Error";
                })
        };


        var input = document.getElementById("msgTextfield");
        input.addEventListener("keyup", function (event) {
            event.preventDefault();
            if (event.keyCode === 13) {
                sendMessage();
            }
        });
        connect();
        document.getElementById("historyButton").addEventListener("click", getHistory, false);
        document.getElementById("msgButton").addEventListener("click", sendMessage, false);
        document.getElementById("catButton").addEventListener("click", sendCat, false);

        $interval(function () {
            var elem = document.getElementById('chat-room');
            if ($scope.autoScroll.isOn === true && elem !== null) {
                elem.scrollTop = elem.scrollHeight;
            }
        }, 500)
    }
});

angular.module('anguchatApp').component('kitty', {
    templateUrl: 'kitty.html',
    controller: function ($scope, $http) {
    }
});
angular.module('anguchatApp').component('about', {
    templateUrl: 'about.html',
    controller: function ($scope, $interval) {
    }
});

app.config(function ($stateProvider) {
    var chatState = {
        name: 'chat',
        url: '/chat',
        component: 'chatroom'
    };

    var kittyState = {
        name: 'kitty',
        url: '/kitty',
        component: 'kitty'
    };

    var aboutState = {
        name: 'about',
        url: '/about',
        component: 'about'
    };

    $stateProvider.state(chatState);
    $stateProvider.state(kittyState);
    $stateProvider.state(aboutState);
});

function miau() {
    var a = document.getElementById("cat");
    a.innerText = "MIAUUUU";
    setTimeout(function () {
        a.innerText = "🐱";
    }, 2000);
};
