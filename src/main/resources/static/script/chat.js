var app = angular.module('anguchatApp', ['ui.router']);

angular.module('anguchatApp').component('chatroom', {
    templateUrl: 'chat.html',

    controller: function ($scope, $http, $interval) {
        $scope.maxId = -1;
        $scope.autoScroll = {isOn: true};

        function askForChatRoom() {
            $http({
                method: "POST",
                url: "chat",
                data: {lastId: $scope.maxId}
            }).then(function ok(value) {
                    if (value.status === 200) {
                        $scope.chatRoom = value.data;
                        var keys = Object.keys($scope.chatRoom);
                        $scope.maxId = Math.max.apply(Math, keys);
                    }
                },
                function notOK(reason) {
                    $scope.result = "Error";
                });
        }

        $scope.sendMsg = function (special) {
            if ($scope.nick == null || $scope.nick === "") {
                $scope.result = "Enter nick!";
                return false;
            }
            if (special != null){
                $scope.msg = special;
            }
            $http({
                method: "POST",
                url: "message",
                data: {
                    msg: this.msg,
                    nick: this.nick
                }
            }).then(function ok(value) {
                    $scope.msg = "";
                },
                function notOK(reason) {
                    $scope.result = "Error" + reason.toString();
                })
        };
        $scope.sendCat = function () {
            $scope.sendMsg("🐱");
        };
        $interval(function () {
            askForChatRoom();
            var elem = document.getElementById('chat-room');
            if ($scope.autoScroll.isOn && elem !== null) {
                elem.scrollTop = elem.scrollHeight;
            }
        }, 1000);
        var input = document.getElementById("msgTextfield");

        input.addEventListener("keyup", function (event) {
            event.preventDefault();
            if (event.keyCode === 13) {
                $scope.sendMsg();
            }
        });
        askForChatRoom();
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
