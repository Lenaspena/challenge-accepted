app.factory('userService', function ($http) {

    var baseUrl = "https://localhost:63342/challengeaccepted/api/";

    return {
        createNewUser: function (data) {
            return $http({
                url: baseUrl + 'user/',
                method: 'POST',
                data: data,
                header: {'Content-Type': 'application/json'}
            })
        },

        getUserById: function (id) {
            return $http({
                url: baseUrl + 'user/' + id,
                method: 'GET',
                header: {'Content-Type': 'application/json'}
            })
        },

        getUserByEmail: function (email) {
            return $http({
                url: baseUrl + 'user/find-by-email?email=' + email + '',
                method: 'GET',
                header: {'Content-Type': 'application/json'}
            })
        },

        getListOfAllUsers: function () {
            return $http({
                url: baseUrl + 'users/',
                method: 'GET',
                header: {'Content-Type': 'application/json'}
            })
        }
    };

});