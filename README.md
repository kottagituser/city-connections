# Code Challenge Application
	*A REST HTTP Service  that can determine if two cities are connected through a series of roads:
	The service returns 'yes' if two cities are connected and 'no' otherwise
	
	Two cities are considered connected if there’s a series of roads that can be traveled from one city to another.
	List of roads is available in a file (city.txt). The file contains a list of city pairs (one pair per line, comma separated), 
	which indicates that there’s a road between those cities.
	
	*For Example:
	
	city.txt content is:
	Boston, New York
	Philadelphia, Newark
	Newark, Boston
	Trenton, Albany
	
	http://localhost:8080/connected?origin=Boston&destination=Newark
	will return yes
	http://localhost:8080/connected?origin=Boston&destination=Philadelphia
	will return yes
	http://localhost:8080/connected?origin=Philadelphia&destination=Albany
	will return no
	
	 
## Java 8 + SpringBoot Solution
	The key, value pairs in City.txt file are loaded into a Graph data structure at application startup.
	(Guava library is used to provide Graph implementation)
	
	*Response is in plain text format.
	*Basic Authentication and unit tests included.
	
	```
	Swagger:
	http://localhost:8080/swagger-ui.html
	username: mastercard
	password: mastercard
	
	
	URL:
	GET http://localhost:8080/connected?origin=Boston&destination=Newark
	
	Authorization header:
	Basic bWFzdGVyY2FyZDptYXN0ZXJjYXJk 
	
	Additional urls:
	GET http://localhost:8080/city
	GET http://localhost:8080/city/pairs

	```
```
*Please check the city.txt file. (The lines starting with a # are comments that will not be processed into the graph)
	It includes connected cities as well as disconnected cities
	It includes pairs of cities not connected to other cities (Virginia & Hawaii)
	Also, Nantucket is an island not connected to any other city
```
