# mongodb-springboot
The project demonstrates basic CRUD examples using MongoDB and SpringBoot

For this [tutorial](https://www.mongodb.com/compatibility/spring-boot), we need to create a Spring Boot project, which can be done easily using Spring Initialzr. It is advantageous to use an IDE (Integrated Development Environment) like Eclipse for this tutorial.

# Project Setup
Once the project is setup, we need to setup mongo connection uri and database which we will use in this project in the application.properties file

	spring.data.mongodb.uri=mongodb+srv://<username>:<pwd>@<cluster>.mongodb.net/mygrocerylist
	spring.data.mongodb.database=mygrocerylist

and then, create Java class and fields that will be mapped into MongoDB collection and fields.

For the API implementation, we use two approaches:
1. MongoRepository - We demonstrate all the CRUD operations using this approach.
2. MongoTemplate - It is used for more control over filters, and also a good choice for aggregations. We will demonstrate update operation using this approach.

# CRUD Examples using MongoRepository
### To create a document (item) in MongoDB, use the save() method:

  `groceryItemRepo.save(new GroceryItem("Dried Red Chilli", "Dried Whole Red Chilli", 2, "spices"));`
  
### To Read all the documents, use findAll() method:
  `groceryItemRepo.findAll().forEach(item-> item.getName(),item.getQuantity(),item.getCategory());`
  
### To Read documents based on a particular field, like name or category, by specifying the query parameters:
  	@Query("{name:'?0'}")
	GroceryItem findItemByName(String name);
	
	@Query(value="{category:'?0'}", fields="{'name' : 1, 'quantity' : 1}")
	List<GroceryItem> findAll(String category);
  
### To update a document using MongoRepository, we should retrieve the specific documents and save the new values. For example, to update a category, do the following:
  	List<GroceryItem> list = groceryItemRepo.findAll(category);
		 
	list.forEach(item -> {
	   // Update the category in each document
		 item.setCategory(newCategory);
	});
		 
	// Save all the items in database
	List<GroceryItem> itemsUpdated = groceryItemRepo.saveAll(list);
  
 ### To delete a document, use the delete method. For example, delete an item by ID using the deleteById() method:
  `groceryItemRepo.deleteById(id);`
	
 # Update using MongoTemplate 
 Updates using MongoTemplate are much easier with the out-of-box classes provided by MongoTemplate.
 Let us update the quantity of a particular item: 
  	
	Query query = new Query(Criteria.where("name").is(name));
   	Update update = new Update();
   	update.set("quantity", newQuantity);		
   	UpdateResult result = mongoTemplate.updateFirst(query, update, GroceryItem.class);
  	
# Disclaimer
This software is not supported by MongoDB, Inc under any of their commercial support subscriptions or otherwise. Any usage is at your own risk.

