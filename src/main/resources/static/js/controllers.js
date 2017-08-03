var myApp = angular.module('jiraApp');

myApp.controller("jiraDetailsController",['$scope','$http', 'jiraDetailsService', function($scope, $http, jiraDetailsService){
	$scope.miscQuery  = "";
	$scope.projectName  = "";
	$scope.loadingCrawler = undefined;
	$scope.getJiraDetails = function(){
		   $scope.loadingCrawler = true;
           jiraDetailsService.getJiraDetails($scope.sprintId, $scope.projectName, $scope.miscQuery, "/rest/agile/1.0").then(
        		   function(serverResponse){
        			   $scope.serverResponse = serverResponse;  
        		   },
        		   function(errorResponse){
        			   $scope.serverResponse = errorResponse;  
        		   }
           ).finally( function () {
        	   $scope.loadingCrawler = false;
           });           
	};
}]);