'use strict'
var stockApp = angular.module('stockindex', ['ui.bootstrap', 'stock.controllers',
    'stock.services'
]);
stockApp.constant("CONSTANTS", {
	getAllStocks: "/api/stocks",
    getStockById: "/api/stocks/",
    saveStock: "/api/stocks",
    updateStock: "/api/stocks/",
    deleteAllStocks: "/api/stocks",
    deleteStockById:"/api/stocks/"
});