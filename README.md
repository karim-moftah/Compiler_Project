# Compiler Project

## Project description

The objective of this project is to take a java file and then generate an intermediate java file with injected java statements, when running this file it prints out which blocks are executed and which are not, another output is an HTML for the input java file with highlighted green parts indicating the executed code and highlighted red parts indicating the code that does not execute.

## Starting rule

```java
compilationUnit
: packageDeclaration? importDeclaration* typeDeclaration*
| moduleDeclaration EOF
;
```

## Tasks

- For Task 1 please refer to [Task 1 Docs](./Docs/task1.md)

- For Task 2 please refer to [Task 2 Docs](./Docs/task2.md)
