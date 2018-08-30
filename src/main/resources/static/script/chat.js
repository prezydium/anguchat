var app = angular.module('anguchatApp', ['ui.router']);

angular.module('anguchatApp').component('chatroom', {
    templateUrl: 'chat.html',

    controller: function ($scope, $http, $interval) {
        $scope.autoScroll = {isOn: true};
        var stompClient = null;

        function connect() {
            var socket = new SockJS('/chatsoc');
            stompClient = Stomp.over(socket);
            stompClient.connect({}, function(frame) {
                console.log('Connected: ' + frame);
                stompClient.subscribe('/topic/messages', function(messageOutput) {
                    showMessageOutput(JSON.parse(messageOutput.body));
                });
            });
        }

        function disconnect() {
            if(stompClient != null) {
                stompClient.disconnect();
            }
            setConnected(false);
            console.log("Disconnected");
        }

        function sendMessage() {

            stompClient.send("/app/chatsoc", {},
                JSON.stringify({'nick':$scope.nick, 'msgText':$scope.msgText}));
        }

        function showMessageOutput(messageOutput) {
            var chat = document.getElementById('chat-room');
            var p = document.createElement('p');
            p.appendChild(document.createTextNode(messageOutput.nick + ": "
                + messageOutput.msgText + " (" + messageOutput.time + ")"));
            chat.appendChild(p);
        }
        var input = document.getElementById("msgTextfield");
        input.addEventListener("keyup", function (event) {
            event.preventDefault();
            if (event.keyCode === 13) {
                $scope.sendMsg();
            }
        });
        connect();
        document.getElementById ("msgButton").addEventListener ("click", sendMessage, false);
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
