'use strict'
angular.module('stock.services', []).factory('stockservice', ["$http", "CONSTANTS", function($http, CONSTANTS) {
    var service = {};
    service.getAllStocks = function() {
        return $http.get(CONSTANTS.getAllStocks);
    }
    service.getStockById = function(stockId) {
        var url = CONSTANTS.getStockById + stockId;
        return $http.get(url);
    }
    service.saveStock = function(stockDto) {
        return $http.post(CONSTANTS.saveStock, stockDto);
    }
    service.updateStock = function(stockDto) {
    	var url = CONSTANTS.updateStock + stockDto.stockId;
        return $http.put(url, stockDto);
    }
    service.deleteAllStocks = function() {
        return $http.delete(CONSTANTS.deleteAllStocks);
    }
    service.deleteStock = function(stockDto) {
    	var url = CONSTANTS.deleteStockById + stockDto.stockId;
        return $http.delete(url, stockDto);
    }
    return service;
}]);