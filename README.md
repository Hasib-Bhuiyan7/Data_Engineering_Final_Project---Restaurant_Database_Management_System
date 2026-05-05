# Data_Engineering_Final_Project---Restaurant_Database_Management_System

Restaurant Database Management System:

This project focuses on the step-by-step design and implementation of a restaurant-based database system, developed across multiple labs. The work begins from an initial conceptual design and gradually builds into a fully functional system that integrates SQL, Java, and XML. Each stage of the project directly builds on the previous one, allowing the database to evolve from an ER model into a complete application with data interaction and transformation capabilities.

The initial phase of the project involved identifying a realistic problem domain and constructing an Entity-Relationship (ER) diagram. The system models a restaurant environment, including core entities such as Restaurant, Customer, Staff, DiningTable, MenuItem, Orders, and Reservation. These entities were chosen to reflect real-world operations like reservations, dine-in orders, and staff responsibilities. Relationships between entities were carefully defined, including one-to-many relationships (such as Restaurant to Staff) and many-to-many relationships (such as Orders and MenuItems), which were later resolved through associative entities like OrderContainsItem and OrderHandledBy.

Following the ER design, the system was translated into a relational database using SQLite. Each entity was mapped to a table, with primary keys, foreign keys, and composite keys implemented to preserve the structure defined in the ER diagram. Special attention was given to weak entities and dependencies, particularly in cases like DiningTable where a composite primary key (restaurant_id, table_number) was required. The schema was normalized and structured to avoid redundancy while still maintaining meaningful relationships across all tables.

Data insertion was performed in a controlled and consistent manner, creating a set of entries that simulate realistic restaurant scenarios. Each table contains approximately ten entries, ensuring that relationships such as reservations, orders, and staff assignments are properly linked. The dataset was designed to be simple but still varied enough to support meaningful queries and testing.

A series of SQL queries were then developed to interact with the database and retrieve useful information. These queries include filtering restaurants by capacity, identifying menu items based on keywords, checking table availability, and determining maximum values such as highest-priced items or highest-paid staff. In several cases, self-joins were used to compute these results without relying on aggregate functions, reinforcing understanding of relational operations and comparison logic.

The project was extended further by integrating the database into a Java application using JDBC. A menu-driven command-line interface was implemented to allow users to execute queries, insert new records, update existing data, and delete entries from lower-dependency tables. The program was structured to handle user input clearly, and output formatting was improved to display results in a more readable “column : value” format. This stage demonstrates how a database system can be connected to an application layer for real-time interaction.

In the final stage, the relational database was converted into an XML-based representation. Each table was mapped into a structured XML format under a single root element, preserving the organization of the original schema. An XML Schema Definition (XSD) was created to enforce constraints such as primary keys, foreign keys, and composite keys using xs:key and xs:keyref. Additionally, XSLT transformations were developed to extract and display specific parts of the data, such as restaurant listings, filtered menu items, and reservation details.

Overall, this project follows a complete database development workflow, starting from conceptual modeling and continuing through implementation, querying, application integration, and data transformation. It demonstrates a clear understanding of database design principles, relational modeling, and the ability to work across multiple technologies while maintaining consistency in structure and logic.

---

## Key Features:

* ER diagram design with clear entity relationships
* Fully normalized relational schema (9 tables)
* Use of primary, foreign, and composite keys
* Realistic dataset with consistent relationships
* SQL queries including joins, filtering, and self-joins
* Java (JDBC) integration with menu-driven interface
* XML conversion of relational database
* XSD schema enforcing structural constraints
* XSLT queries for simple data transformation and display

---

## Technologies Used:

* SQLite3
* Java (JDBC)
* SQL
* XML / XSD / XSLT
