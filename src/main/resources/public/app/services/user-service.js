app.factory('userService', function ($http) {

    var baseUrl = "https://afternoon-atoll-64085.herokuapp.com/api/";

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

        updateUser: function (data, id) {
            return $http({
                url: baseUrl + 'user/' + id,
                method: 'PUT',
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