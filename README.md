## STOCK MANAGEMENT

# Overview
-> Application provides facilities to maintain stocks.</br>
-> Have various operations like GET, POST, PUT, DELETE.</br>
-> In memory H2 database used. Initial default values are present in DB on application startup.</br>
-> Default logging from Spring in use.</br>

# Implementation

A) Back-end controller Calls :

1) Get all stocks :
	
	URL : /api/stocks
	DESCRIPTION : Gets all stocks that is present in in-memory database.

2) Get stock by stock-id :

	URL (GET) : /api/stocks/<stockId>
	DESCRIPTION : get a stock by its Id present in in-memory db. Needs stockId as an input.
	
3) Save a stock : 

	URL (POST) : /api/stocks
	DESCRIPTION : Save a stock if StockId is not already present. Need Stock as an input in body.

4) Update a stock : 

	URL (PUT) : /apo/stocks/<stockId>
	DESCRIPTION : Update a stock if it is present. Takes an input as STockDto and StockId.
	
5) Delete all stocks :
	
	URL (DELETE) : /api/stocks
	DESCRIPTION : Delete all stocks when it is called. Needs no input.
	
	
B) Access with UI :
	
1) On starting up the application, you can visit /home to get the UI of this application.
	
	Eg : http://localhost:8080/home