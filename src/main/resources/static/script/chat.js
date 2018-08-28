var app = angular.module('anguchatApp', ['ui.router']);

angular.module('anguchatApp').component('chatroom', {
    templateUrl: 'chat.html',

    controller: function ($scope, $http, $interval) {
        $scope.maxId = -1;

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

        $scope.sendMsg = function () {
            $http({
                method: "POST",
                url: "message",
                data: {msg: this.msg}
            }).then(function ok(value) {
                    $scope.msg = "";

                },
                function notOK(reason) {
                    $scope.result = "Error" + reason.toString();
                })
        };
        $interval(function () {
            askForChatRoom()
        }, 5000);
        askForChatRoom();
    }
});
angular.module('anguchatApp').component('kitty', {
    templateUrl: 'kitty.html',

    controller: function ($scope, $http) {

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
        template: '<h3>TODO</h3>'
    };

    $stateProvider.state(chatState);
    $stateProvider.state(kittyState);
    $stateProvider.state(aboutState);
});