app.directive("directiveMenu", function () {
    return {
        restrict: 'C',
        templateUrl: 'views/menu.html'
    }
});

app.directive("directiveLoginPage", function() {
    return {
        restrict: 'C',
        templateUrl: 'views/login-page.html'
    }
});