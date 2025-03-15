# Mega City Cab Management System - Project Overview

![Mega City Cab Logo](docs/images/logo.png) <!-- Placeholder for logo -->

**Repository**: [https://github.com/Lakshan-Chamoditha-Perera/Mega-City-Cab](https://github.com/Lakshan-Chamoditha-Perera/Mega-City-Cab)

---

### Overview of Mega City Cab Management System

The **Mega City Cab Management System** is a web-based application modernizing operations for Mega City Cabs in Colombo City, serving thousands of customers monthly. Replacing inefficient manual processes, it automates customer orders, booking management, driver assignments, and vehicle tracking. The Cashier/Renter accesses a dashboard with KPIs (total customers, vehicles, drivers, revenue) and navigates via options like Vehicle Management and Sales & Reports, improving operational efficiency and data reliability.

---

### Project Goals and Assumptions

#### Project Goals
- **Operational Efficiency**: Automate tasks to minimize errors.
- **User-Centric Design**: Intuitive UI with real-time feedback.
- **Data Safety**: Secure authentication and ethical data handling.
- **Scalability**: Modular architecture for growth.
- **Business Intelligence**: Sales reports for strategic insights.

#### Assumptions
- One customer per booking; multiple bookings per customer.
- Vehicles locked daily (Pending/Confirmed); immutable unless cancelled.
- Pricing: Fixed rate/km × vehicles.
- Single user (Cashier/Renter); unique identifiers (mobile, email, NIC, license).

---


### Overview
Testing ensures reliable management of customers, drivers, vehicles, and bookings via a Test-Driven Development (TDD) approach using JUnit 5.

#### Test Plan
- **Rationale**: Validate core functionality and error handling.
- **Data**: Dynamic setup 

#### TDD Approach
- **Unit Testing**: Service Layer, Repository Layer validates logic.
- **Integration Testing**: Repository Layer tests DB interactions.

#### Results
- **Outcomes**: Passes CRUD operations.
- **Effectiveness**: High coverage; real DB slows tests—mocking could enhance speed.

---

## Git/GitHub Repository and Version Control

### Overview
A Git/GitHub strategy ensures effective code management and collaboration.

#### Repository Setup
- **Public Repo**: [Mega-City-Cab](https://github.com/Lakshan-Chamoditha-Perera/Mega-City-Cab) with README, `.gitignore`, and MIT License.

#### Techniques
- **Branches**: `dev` (development), `staging` (QA), `prod` (production) with protection.
- **Commits**: Daily updates (e.g., “feat: booking status”).

#### Workflow
- **Features**: Developed in `dev`, merged to `staging` via PRs, then `prod` post-QA.
- **PRs**: Reviewed for quality.

---

### Technology Stack

- **Java**: JSP/Servlets for UI, Java EE Security (planned) for authentication.
- **MySQL**: Transactions, stored procedures, and indexes for scalability.
- **Tools**: IntelliJ IDEA (IDE), Git (version control), Maven (build).

---


### Summary of Contributions
- **System Design**: Layered architecture (JSP/Servlet, Service, Repository, Database) with UML diagrams.
- **Patterns**: Singleton, Factory for reusability and flexibility.
- **Testing**: Rigorous TDD with JUnit for robust functionality.
- **Version Control**: Disciplined Git workflow (dev/staging/prod) with PRs and documentation.
- **Security/Ethics**: Java EE Security Filter ensures data protection.

---

## Installation

### Prerequisites
- Java JDK 11+
- Apache Tomcat 9.x
- MySQL 8.x
- Maven
- Git

### Steps
1. **Clone**
   ```bash
   git clone https://github.com/Lakshan-Chamoditha-Perera/Mega-City-Cab.git
   cd Mega-City-Cab