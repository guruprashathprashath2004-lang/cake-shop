# 🧁 Sweet Cupcake Shop

A Java Swing desktop application for managing a cupcake shop: cake inventory, cashier accounts, cart / sales and transaction history. Built with a clean **MVC** structure and file-based storage (no database needed).

> University project: HND Computing & Software Engineering, ICBT Campus Kandy (Cardiff Metropolitan University).

## Features

**Manager**
- Secure manager login
- Add / view / delete cashier accounts
- View and manage cake (cupcake) details: add, search by category, delete by flavor

**Cashier**
- Cashier login (accounts stored in `Cashiers_Details.txt`)
- Browse cupcake menu and categories
- Add items to a cart for a customer and complete the sale
- View all transactions and total sales

## Tech Stack
- Java 8+ (Swing GUI)
- NetBeans / Apache Ant project
- Plain text file storage (`.txt`)

## OOP Concepts Used
- **Abstraction**: abstract class `Functions` (`searchCupcakeByCategory`, `deleteCupcake`)
- **Inheritance**: `Abstract extends Functions`, `Manager_login` / `Cashier_login` extend `User_login`
- **Encapsulation**: private fields with getters/setters in the model classes
- **MVC**: `model/`, `view/`, `controller/` packages

## Project Structure
```
src/
 ├── model/        Cakes, Cart, User_login, Manager_login, Cashier_login
 ├── view/         Login, Managerchoice, Add_Cashier, Menu (Swing forms)
 └── controller/   Functions (abstract), Abstract, Manager, Cashier, Transection
Cake_Details.txt       cake inventory  (flavor|category|size|qty|price)
Cashiers_Details.txt   cashier accounts (username|password)
Transactions.txt       completed sales  (sample data)
```

## How to Run

**NetBeans (recommended)**
1. Clone the repo
2. NetBeans → *File → Open Project* → select the cloned folder
3. Press **Run** (main class: `view.Login`)

**Command line**
```bash
git clone https://github.com/<your-username>/cake-shop.git
cd cake-shop
javac -d build/classes src/*/*.java
java -cp build/classes view.Login
```
> Run from the project root so the app can find the `.txt` data files.

## Demo Logins
| Role | Username | Password |
|------|----------|----------|
| Manager | `admin` | `admin123` |
| Cashier | `cashier1` | `1234` |

*These are demo credentials for testing only.*

## Author
**Guruprashath**, HND Computing & Software Engineering, ICBT Campus Kandy
