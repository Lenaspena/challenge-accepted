app.factory('notificationService', function ($http) {

    var baseUrl = "https://localhost:63342/challengeaccepted/api/";

    return {
        getAllNotifications: function (id) {
            return $http({
                url: baseUrl + 'user/'+ id +'/notifications/',
                method: 'GET',
                header: {'Content-Type': 'application/json'}
            })
        }
    }
});