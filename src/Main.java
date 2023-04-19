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
    
    public static void runIntermediateCode() throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("bash", "-c","java outputs/output.java");
        try {

            Process process = processBuilder.start();

            StringBuilder output = new StringBuilder();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

            int exitVal = process.waitFor();
            if (exitVal == 0) {
                System.out.println("Success!");
                process.destroy();
            } else {
                System.out.println("Error!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void genHTML(CommonTokenStream token, ParseTree tree) throws Exception {
        // generate HTML file
        TokenStreamRewriter HTMLrewriter = new TokenStreamRewriter(token);
        HTMLParser myHTMLparser = new HTMLParser(HTMLrewriter,readBlocksFile());
        myHTMLparser.visit(tree);
        myHTMLparser.write_html(HTMLrewriter.getText());

        // open the HTML file automatically
        File htmlFile = new File("outputs/index.html");
        Desktop.getDesktop().browse(htmlFile.toURI());
    }

}
