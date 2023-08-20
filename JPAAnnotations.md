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




