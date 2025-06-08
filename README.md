Create a Spring Boot-based Bank Application with the following features:

1. **User Registration and Login:**
    - Implement user registration where a user provides a name and password.
    - Implement a login system where users can authenticate using their name and password.
    - Store user credentials securely, ensuring password hashing (use BCrypt or another method).
    
2. **Account Operations:**
    - Allow logged-in users to:
        - Deposit money into their account.
        - Withdraw money from their account.
        - Check their current bank balance.
        - Transfer money to another user's account using their unique user ID.
        
3. **Admin Operations:**
    - Create an admin role that has elevated privileges.
    - The admin should be able to:
        - View all user accounts.
        - View all user transactions (deposits, withdrawals, and transfers).

4. **Database Setup:**
    - Use an embedded database like H2 or MySQL to store user information, transaction details, and bank account balances.
    - Store transaction details (including deposit, withdrawal, and transfer) with timestamps.
    
5. **API Endpoints:**
    - POST `/api/register`: Register a new user with a name and password.
    - POST `/api/login`: Authenticate a user by name and password and return a JWT token for further requests.
    - GET `/api/balance`: Retrieve the logged-in user's current bank balance.
    - POST `/api/deposit`: Deposit money into the logged-in user's account.
    - POST `/api/withdraw`: Withdraw money from the logged-in user's account.
    - POST `/api/transfer`: Transfer money from the logged-in user's account to another account.
    - GET `/api/admin/accounts`: Admin can view all accounts.
    - GET `/api/admin/transactions`: Admin can view all transactions.

6. **Security:**
    - Implement JWT authentication for secure user login and admin roles.
    - Protect endpoints that require login (e.g., `/api/deposit`, `/api/withdraw`, `/api/transfer`) with authentication and authorization.
    - Allow only the admin to access `/api/admin/accounts` and `/api/admin/transactions`.

7. **Unit Testing:**
    - Provide unit tests to validate the user registration, login, deposit, withdrawal, transfer, and admin functionalities.


# To run
mvn spring-boot:run
# website
http://localhost:8080