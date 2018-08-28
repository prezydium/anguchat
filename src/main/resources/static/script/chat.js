var app = angular.module('anguchatApp', []);
app.controller('anguchatCtrl', function ($scope, $http, $interval) {
    $scope.maxId = -1;
    function askForChatRoom() {
        $http({
            method: "POST",
            url: "chat",
            data: {lastId: $scope.maxId}
        }).then(function ok(value) {
                if (value.status === 200){
                    $scope.chatRoom = value.data;
                    var keys = Object.keys($scope.chatRoom);
                    $scope.maxId = Math.max.apply(Math, keys);
                }
            },
            function notOK(reason) {
                $scope.result = "Error";
            });
    };
    $scope.sendMsg = function () {
        $http({
            method: "POST",
            url: "message",
            data: {msg: $scope.msg}
        }).then(function ok(value) {
                $scope.msg = "";
            },
            function notOK(reason) {
                $scope.result = "Error";
            })
    };
    $interval(function () {
        askForChatRoom()
    }, 5000);

});