# GameBase  

GameBase is a web-based e-commerce application focused on selling gaming supplies. It was built with Java and Spring Boot and extends the functionality of an earlier console-based project (Gaming Supplies). The platform allows users to browse gaming supplies, add them to a shopping cart, and view inventory.  

## Features  

- View available gaming accessories with details like name, price, and stock.  
- Users can add items to their cart.
- Total price of all cart items is displayed for reference.
- Product stock increases when an item is removed from the cart.
- Product stock decreases when an item is added to the cart.  

## Future Enhancements  

- Implement a checkout system for purchasing items.  
- Add more general UI improvements.

## Technologies Used  

- **Backend**: Java, Spring Boot, Hibernate 
- **Database**: MySQL  
- **Frontend**: Thymeleaf  
- **Build Tool**: Maven  

# Setup Instructions  

Please update the `application.properties` file with your database credentials:

```
spring.datasource.url=jdbc:mysql://localhost:3306/gamebase
spring.datasource.username=yourusername
spring.datasource.password=yourpassword
```

To build and run the application, please use the following command:

```
mvn spring-boot:run
```

## API Endpoints

| Method | Endpoint      | Description                          |
|--------|---------------|--------------------------------------|
| GET    | /products     | Retrieve all gaming supplies         |
| POST   | /products     | Add a new gaming product             |
| POST   | /cart         | Add a product to the cart            |
| GET    | /cart         | View cart contents                   |
