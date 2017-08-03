var myApp = angular.module('jiraApp');

myApp.service('jiraDetailsService', ['$http', function ($http) {
    this.getJiraDetails = function(sprintId, projectName, miscQuery, restUrl){
    	var parameters = {
    			params:{
    				'sprintId': sprintId, 
    				'projectName': projectName, 
    				'miscQuery': miscQuery
    				}
    	}
        return $http.get(restUrl, parameters);
    }
}]);