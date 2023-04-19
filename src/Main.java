import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.awt.*;
import java.io.BufferedReader;
import java.io.*;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        String inputFileName = "Inputs/input.java";
        FileInputStream inputFile = new FileInputStream(new File(inputFileName));
        ANTLRInputStream input = new ANTLRInputStream(inputFile);

        JavaLexer lexer = new JavaLexer(input);
        CommonTokenStream token = new CommonTokenStream(lexer);

        JavaParser parser = new JavaParser(token);
        ParseTree tree = parser.compilationUnit();
        genIR(token,tree);

        // execute java output
        runIntermediateCode();
        genHTML(token,tree);

    }

    public static void genIR(CommonTokenStream token, ParseTree tree) throws Exception {
        TokenStreamRewriter rewriter = new TokenStreamRewriter(token);

        MyVisitorParser myparser = new MyVisitorParser(rewriter);
        myparser.visit(tree);
        myparser.write(rewriter.getText());
    }
}