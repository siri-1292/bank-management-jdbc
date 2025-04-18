🏦 Banking Management System
Tech Stack: Java | JDBC | MySQL | DAO Pattern | OOP

A console-based Banking Management System developed in Java to simulate real-world banking operations with secure, modular, and maintainable code structure. The application utilizes JDBC for database connectivity, adheres to the DAO (Data Access Object) design pattern, and follows Object-Oriented Programming principles to ensure clean and scalable architecture.

🔑 Key Features
👨‍💼 Admin Module
-> Secure Login: Authenticated access using email and password.

-> Customer Management:
    - View, approve, or delete pending customer registration requests.
    - Access the list of all registered users with masked sensitive information.

👤 Customer Module
-> Account Registration: Sign up for a new bank account.
-> Login: Access account securely using account number and PIN.
-> Banking Operations:
    - Balance Enquiry.
    - Deposit and withdraw funds.
    - View transaction history.
    - Change Password.
    - Mobile number to Mobile number transaction. 

🗄️ Database Integration
 -> JDBC Connectivity: Robust interaction with a MySQL database.
 -> DAO Pattern: Clean separation of database logic for better maintainability and testability.

🔐 Security & Privacy
-> Data Masking: Sensitive fields like mobile numbers and Aadhaar numbers are partially masked in admin views.
-> Secure Generation: Unique account numbers and secure PINs are generated randomly for each user.

