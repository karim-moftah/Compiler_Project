import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.io.File;
import java.io.FileInputStream;

public class Main {
    public static void main(String[] args) throws Exception{

        String inputFileName = "Inputs/input.java";
        FileInputStream inputFile = new FileInputStream(new File(inputFileName));
        ANTLRInputStream input = new ANTLRInputStream(inputFile);

        JavaLexer lexer = new JavaLexer(input);
        CommonTokenStream token = new CommonTokenStream(lexer);

        JavaParser parser = new JavaParser(token);
        ParseTree tree = parser.compilationUnit();
        TokenStreamRewriter rewriter = new TokenStreamRewriter(token);

        MyVisitorParser myparser = new MyVisitorParser(rewriter);
        myparser.visit(tree);
        myparser.write(rewriter.getText());

    }
}