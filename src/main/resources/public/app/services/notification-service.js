app.factory('notificationService', function ($http) {

    var baseUrl = "https://afternoon-atoll-64085.herokuapp.com/api/";

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