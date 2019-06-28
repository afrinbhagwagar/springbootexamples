'use strict'
var module = angular.module('stock.controllers', []);
module.controller("stockcontroller", [ "$scope", "stockservice",
		function($scope, stockservice) {
			$scope.singleStockSelected = false;
			$scope.addButtonView = false;
			$scope.selectedAll = false;
			stockservice.getAllStocks().then(function(value) {
				$scope.allStocks = value.data;
			}, function(reason) {
				console.log("error occured");
			});
			
			$scope.checkAll = function () {
				var toggle = !!$scope.selectedAll;
			    angular.forEach($scope.allStocks, function(stock){
			      stock.selected = toggle;
			    });
			    $scope.selectedAll = toggle;
			  }
			
			$scope.saveStock = function() {
				stockservice.saveStock($scope.selectedStock).then(function() {
					$scope.selectedStock.stockId = '';
					$scope.selectedStock.stockName = '';
					$scope.selectedStock.stockPrice = '';
					$scope.selectedStock.stockTimestamp = '';
					stockservice.getAllStocks().then(function(value) {
						$scope.allStocks = value.data;
					}, function(reason) {
						console.log("error occured");
					});
				}, function(reason) {
					console.log("error occured");
				});
			}
			
			$scope.updateStock = function() {
				stockservice.updateStock($scope.selectedStock).then(function() {
					$scope.selectedStock.stockId = '';
					$scope.selectedStock.stockName = '';
					$scope.selectedStock.stockPrice = '';
					$scope.selectedStock.stockTimestamp = '';
					$scope.singleStockSelected = false;
					stockservice.getAllStocks().then(function(value) {
						$scope.allStocks = value.data;
					}, function(reason) {
						console.log("error occured");
					});
				}, function(reason) {
					console.log("error occured");
				});
			}
			
			$scope.deleteStock = function () {
			    angular.forEach($scope.allStocks, function(stock) {
			     if(stock.selected){
				stockservice.deleteStock(stock).then(function() {
					stockservice.getAllStocks().then(function(value) {
						$scope.allStocks = value.data;
					}, function(reason) {
						console.log("error occured");
					});
				}, function(reason) {
					console.log("error occured");
				})}
			});
			    $scope.selectedStock = null;
			    $scope.singleStockSelected = false;
			    $scope.selectedAll = false;
			    $scope.addButtonView = false;
			}
			
			  $scope.setSelectedStock = function (stock){
			    if($scope.allStocks.filter(x => x.selected).length > 1){
			      $scope.selectedStock = null;
			      $scope.singleStockSelected = false;
			      $scope.addButtonView = true;
			    } else {
			      $scope.selectedStock = angular.copy($scope.allStocks.find(x => x.selected));
			      $scope.singleStockSelected = !!$scope.selectedStock;
			      $scope.addButtonView = false;
			    }
			  }
		} ]);