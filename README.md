# Quantum Bookstore 📚

Welcome to **Quantum Bookstore**, a simple but extensible Java-based online bookstore system developed for the **Fawry N² Dev Slope Challenge #10**.

---

## 🚀 Features

Quantum Bookstore supports multiple types of books and provides a clean architecture for easy extensibility. All print/log outputs are prefixed with **"Quantum book store"**.

### 📘 Book Types
- **PaperBook**  
  - Has a stock quantity  
  - Can be shipped to a physical address  
- **EBook**  
  - Delivered via email  
  - Has a file type (e.g., PDF, EPUB)  
- **ShowcaseBook**  
  - Not for sale, used for display/demo purposes  

---

## 🛠️ Capabilities

- ✅ Add a new book to the inventory  
- ❌ Remove outdated books based on the publication year  
- 🛒 Buy a book using its ISBN  
  - Checks inventory availability  
  - Reduces stock (if applicable)  
  - Calculates total cost  
  - Sends:
    - Paper books to `ShippingService`  
    - EBooks to `MailService`  

> Note: `ShippingService` and `MailService` are stubbed with no real implementation.

---

## 🧪 Test Class

All features are demonstrated through the `QuantumBookstoreFullTest` class:
- Adding different types of books  
- Buying books with valid/invalid conditions  
- Removing old books from inventory  

---

## 📦 Extensibility

The system is designed using **object-oriented principles** and leverages **polymorphism** for book types.  
➡️ New types of books (e.g., Audiobooks) can be added by extending the base `Book` class without changing existing code.

--


![ecoomerce2](https://github.com/user-attachments/assets/450cb232-4b84-4556-9ae8-42ff7aa543ce)
![ecommerce4](https://github.com/user-attachments/assets/0e51bf2c-e5d2-4f11-a0aa-a3492ce65cfd)
![ecommerce3](https://github.com/user-attachments/assets/233dfc8a-12f3-405a-8cd3-6420bb146841)
![ecommerce1](https://github.com/user-attachments/assets/29466316-b47b-4dba-9146-e7f3fcdffb74)

