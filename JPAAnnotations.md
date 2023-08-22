# Java Persistence API (JPA) Annotations

This repository contains explanations and examples for commonly used annotations from the `javax.persistence` package in the Java Persistence API (JPA).

## Table of Contents

- [Entity](#entity)
- [Table](#table)
- [Id](#id)
- [GeneratedValue](#generatedvalue)
- [Column](#column)
- [Basic](#basic)
- [Transient](#transient)
- [OneToMany](#onetomany)
- [ManyToOne](#manytoone)
- [ManyToMany](#manytomany)
- [OneToOne](#onetoone)
- [JoinColumn](#joincolumn)
- [JoinTable](#jointable)
- [MappedSuperclass](#mappedsuperclass)
- [Inheritance](#inheritance)
- [DiscriminatorColumn](#discriminatorcolumn)
- [DiscriminatorValue](#discriminatorvalue)
- [NamedQuery](#namedquery)
- [NamedQueries](#namedqueries)
- [NamedNativeQuery](#namednativequery)
- [NamedNativeQueries](#namednativequeries)
- [Version](#version)
- [Temporal](#temporal)
- [Enumerated](#enumerated)
- [Lob](#lob)
- [PrePersist](#prepersist)
- [PostPersist](#postpersist)
- [PreUpdate](#preupdate)
- [PostUpdate](#postupdate)
- [PreRemove](#preremove)
- [PostRemove](#postremove)
- [Access](#access)
- [Embedded](#embedded)
- [Embeddable](#embeddable)
- [ElementCollection](#elementcollection)
- [CollectionTable](#collectiontable)
- [MapKey](#mapkey)
- [MapKeyClass](#mapkeyclass)
- [MapKeyColumn](#mapkeycolumn)
- [Convert](#convert)
- [Converts](#converts)
- [AttributeOverride](#attributeoverride)
- [AttributeOverrides](#attributeoverrides)
- [AssociationOverride](#associationoverride)
- [AssociationOverrides](#associationoverrides)
- [NamedAttributeNode](#namedattributenode)
- [NamedAttributeNodes](#namedattributenodes)
- [EntityGraph](#entitygraph)
- [NamedEntityGraphs](#namedentitygraphs)


## Entity

The `@Entity` annotation is used to specify that a class is an entity and is mapped to a database table.

### Explanation

In JPA, an entity is a Java class that represents a persistent object and is mapped to a database table. The `@Entity` annotation marks a class as an entity, enabling it to be managed by JPA and allowing you to perform database operations on instances of this class.

### Example

Suppose you have a class named `Student` that you want to map to a database table named `students`. You can use the `@Entity` annotation to mark the class as an entity:

```java
@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    // Other entity fields, getters, setters, etc.
}
```

## Table

The `@Table` annotation is used to specify the details of the database table to which an entity is mapped.

### Explanation

When you map an entity to a database table, you can use the `@Table` annotation to customize the table's name, schema, indexes, and more. This annotation is particularly useful when the default table name generated from the entity class name needs to be changed or when you want to configure other table-related properties.

### Example

Suppose you have an entity named `Employee` that you want to map to a database table named `employees`. You can use the `@Table` annotation to specify the table name:

```java
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    // Other entity fields, getters, setters, etc.
}
```

## Id

The `@Id` annotation is used to specify the primary key field or property of an entity.

### Explanation

In a JPA entity, the primary key uniquely identifies each record in the database table. The `@Id` annotation marks a field or property as the primary key of the entity. It can be applied to numeric types, string types, or any user-defined type that can be converted to a database primary key.

### Example

```java
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double price;

    // Getters, setters, etc.
}
```

## GeneratedValue

The @GeneratedValue annotation is used to specify the strategy for generating primary key values.

### Explanation

When a new entity instance is persisted, JPA needs to assign a unique primary key value to it. The @GeneratedValue annotation specifies how this value is generated. Common strategies include identity columns, sequence generators, and table generators.

### Example

```java
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String orderNumber;
    private double totalAmount;

    // Getters, setters, etc.
}
```

## Column

The @Column annotation specifies the details of a database column that corresponds to an entity field or property.

### Explanation

When you map an entity to a database table, the @Column annotation allows you to customize the mapping of an entity field or property to a table column. You can specify column name, length, nullable, unique, and more.

### Example

```java
@Entity
public class Customer {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

    @Column(name = "full_name", length = 100, nullable = false)
    private String fullName;

    private String email;

    // Getters, setters, etc.
}
```

## @Embeddable

The `@Embeddable` annotation is used to define a class as an embeddable component that can be reused as part of other entities.

### Explanation

An embeddable component is a non-entity class whose instances are stored as part of the owning entity's table. It allows you to group related fields together and reuse them in multiple entities without creating separate tables.

### Example

```java
@Embeddable
public class Address {
    private String street;
    private String city;
    private String zipCode;

    // Getters, setters, etc.
}

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Embedded
    private Address address;

    // Getters, setters, etc.
}
```

## Embedded

The @Embedded annotation is used to specify an embeddable component as part of an entity.

### Explanation

When you want to include an embeddable component inside an entity, you use the @Embedded annotation. This annotation allows you to treat the fields of the embeddable component as if they were part of the entity itself.

### Example

```java
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Embedded
    private Address address;

    // Getters, setters, etc.
}
```

## ElementCollection

The @ElementCollection annotation is used to define a collection of basic types or embeddable components.

### Explanation

When you want to store a collection of values (basic types or embeddable components) as part of an entity, you can use the @ElementCollection annotation. The collection values are stored in a separate table associated with the owning entity.

### Example

```java
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ElementCollection
    private List<String> phoneNumbers;

    // Getters, setters, etc.
}
```

## Transient

The @Transient annotation in JPA is used to indicate that a field should not be persisted to the database.

### Explanation

When working with JPA entities, you might have fields that are not meant to be stored in the database, such as calculated values, temporary variables, or fields used for internal processing. The @Transient annotation allows you to exclude such fields from being persisted to the database. Fields marked as @Transient will not be part of the entity's database representation and will not be stored in the corresponding database table.

### Example

```java
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double price;

    @Transient
    private double discountedPrice; // This field won't be persisted

    // Constructors, getters, setters, etc.
}
```

### OneToOne

The @OneToOne annotation is used to define a one-to-one relationship between two entities.

### Explanation

When you have a scenario where one entity is associated with exactly one instance of another entity, you can use the @OneToOne annotation. This establishes a single relationship between the two entities. It's important to note that in a one-to-one relationship, the primary key of one entity is usually used as a foreign key in the other entity.

### Example

```java
@Entity
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @OneToOne(mappedBy = "profile")
    private User user;

    // Getters, setters, etc.
}

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @OneToOne
    @JoinColumn(name = "profile_id")
    private UserProfile profile;

    // Getters, setters, etc.
}
```

### OneToMany

The @OneToMany annotation is used to define a one-to-many relationship between two entities.

### Explanation

When you have a scenario where one entity is associated with multiple instances of another entity, you can use the @OneToMany annotation. This establishes a collection-based relationship between the two entities. The collection of related entities is typically represented as a List, Set, or another collection type in the owning entity.

### Example

```java
@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "department")
    private List<Employee> employees;

    // Getters, setters, etc.
}

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    // Getters, setters, etc.
}
```

## ManyToOne

The @ManyToOne annotation is used to define a many-to-one relationship between two entities.

### Explanation

When you have a scenario where multiple instances of one entity are associated with a single instance of another entity, you can use the @ManyToOne annotation. This establishes a many-to-one relationship, where multiple instances of one entity share the same instance of the other entity.

### Example

```java
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    // Getters, setters, etc.
}

@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // Getters, setters, etc.
}
```

## ManyToMany

The @ManyToMany annotation is used to define a many-to-many relationship between two entities.

### Explanation

When you have a scenario where multiple instances of one entity are associated with multiple instances of another entity, you can use the @ManyToMany annotation. This establishes a many-to-many relationship, which is typically implemented using an intermediate join table.

### Example

```java
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "student_course",
               joinColumns = @JoinColumn(name = "student_id"),
               inverseJoinColumns = @JoinColumn(name = "course_id"))
    private List<Course> courses;

    // Getters, setters, etc.
}

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "courses")
    private List<Student> students;

    // Getters, setters, etc.
}
```

## JoinColumn

The @JoinColumn annotation is used to customize the mapping of a foreign key column in an association.

### Explanation

In situations where you have an entity that is associated with another entity through a foreign key column, you can use the @JoinColumn annotation to tailor the mapping of that foreign key column. This annotation provides control over the name of the foreign key column, its referencing, and more.

### Example

```java
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    // Getters, setters, etc.
}

@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "department")
    private List<Employee> employees;

    // Getters, setters, etc.
}
```

## JoinTable

The @JoinTable annotation is used to define an intermediate table for a many-to-many relationship.

### Explanation

When dealing with a many-to-many relationship between two entities, you often need an intermediate table to manage the association. The @JoinTable annotation allows you to explicitly define the intermediate table's characteristics, including its name, foreign key columns, and more.

### Example

```java
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "student_course",
               joinColumns = @JoinColumn(name = "student_id"),
               inverseJoinColumns = @JoinColumn(name = "course_id"))
    private List<Course> courses;

    // Getters, setters, etc.
}

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "courses")
    private List<Student> students;

    // Getters, setters, etc.
}
```







