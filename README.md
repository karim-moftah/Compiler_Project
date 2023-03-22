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

## 