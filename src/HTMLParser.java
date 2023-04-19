
import org.antlr.v4.runtime.*;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class HTMLParser extends JavaParserBaseVisitor<String> {

    TokenStreamRewriter rewriter;
    ArrayList<String> visitedBlocks;

    int count = 1;
    int caseCount = 0;

    public HTMLParser(TokenStreamRewriter rewriter , ArrayList<String> visitedBlocks) {
        this.rewriter = rewriter;
        this.visitedBlocks = visitedBlocks;
    }


    public void write_html(String string) throws Exception {
        String outputFileName = "outputs/index.html";
        FileOutputStream outputFile = new FileOutputStream(outputFileName, false);
        BufferedOutputStream buffer = new BufferedOutputStream(outputFile);
        // buffer can only write with characters
        byte[] bytes = string.getBytes();
        buffer.write(bytes);
        buffer.close();
    }

    @Override
    public String visitCompilationUnit(JavaParser.CompilationUnitContext ctx) {

        rewriter.insertBefore(ctx.getStart(), "\nimport java.util.*;\nimport java.io.*;\n");
            rewriter.insertBefore(ctx.getStart(), " <!DOCTYPE html>\n" +
                    "           <html>\n" +
                    "               <head>\n" +
                    "                   <title>Compilers Project</title>\n" +
                    "                   <meta charset = \"UTF-8\">\n" +
                    "                      <meta name = \"description\" content = \"Compilers Project: Which Blocks and visited?!\">\n" +
                    "                      <style>\n" +
                    "                       .green {\n" +
                    "                           background-color: #90ee90;\n" +
                    "                       }\n" +
                    "                       .red {\n" +
                    "                           background-color: #DE3163;\n" +
                    "                       }\n" +
                    "                   </style>\n" +
                    "                      <script></script>\n" +
                    "               </head>\n" +
                    "                 <body style=\"background:#90ee90;\">\n" +
                    "                   <pre>\n");


            rewriter.insertAfter(ctx.getStop(), "" +
                    "                    </pre>\n" +
                    "                </body>\n" +
                    "            </html>\n");




        return super.visitCompilationUnit(ctx);

    }

    @Override
    public String visitClassBody(JavaParser.ClassBodyContext ctx) {

            rewriter.insertAfter(ctx.getStart(), "\n\n\tstatic public Integer blocksVisited[] = {};");
            rewriter.insertAfter(ctx.getStart(), "\n\tstatic public ArrayList&lt;Integer&gt; arrayList = new ArrayList&lt;Integer&gt;(Arrays.asList(blocksVisited));\n");

        return super.visitClassBody(ctx);
    }

    @Override
    public String visitBlock(JavaParser.BlockContext ctx) {

        String color = this.visitedBlocks.contains(String.valueOf(count)) ? "green" : "red";
        String injectedMessage = "<pre class='" + color + "' id='" + count + "'>";
            rewriter.insertAfter(ctx.getStart(), "\t\t"+injectedMessage +"// block number " + count + '\n');
            rewriter.insertAfter(ctx.getStart(), "\t\t\tarrayList.add(" + count + ");");
            rewriter.insertBefore(ctx.getStop(), "</pre>");
            ++count;

        return super.visitBlock(ctx);
    }

    @Override
    public String visitIfbranch(JavaParser.IfbranchContext ctx) {
        String color = this.visitedBlocks.contains(String.valueOf(count)) ? "green" : "red";

        String injectedMessage = "<pre class='" + color + "' id='" + count + "'>";

            if (!ctx.statement().getStart().getText().equals("{")) {
                rewriter.insertBefore(ctx.statement().getStart(), "{" + injectedMessage + "\t\t\t\t\t\t\t\t\t\t// block number " + count + '\n' + "\t\t\tarrayList.add(" + count + ");\n\t\t\t");
                rewriter.insertAfter(ctx.statement().getStop(), "\n\t\t\t</pre>\n\t\t\t}");
                ++count;
            }


        return super.visitIfbranch(ctx);
    }

    @Override
    public String visitElseif(JavaParser.ElseifContext ctx) {

            if (!ctx.getChild(1).getText().toString().startsWith("if(")) {
                if(!ctx.statement().getStart().getText().equals("{")) {
                    // inside else not if else
                    String color = this.visitedBlocks.contains(count) ? "green" : "red";
                    String injectedMessage = "<pre class='" + color + "' id='" + count + "'>";

                    rewriter.insertBefore(ctx.statement().getStart(), "{" + injectedMessage + "\t\t\t\t\t\t\t\t\t\t// block number " + count + '\n' + "\t\t\tarrayList.add(" + count + ");\n\t\t\t");
                    rewriter.insertAfter(ctx.statement().getStop(), "\n\t\t\t</pre>\n\t\t}");
                    ++count;
                }
            }

        return super.visitElseif(ctx);
    }

    @Override
    public String visitSwitchBlockStatementGroup(JavaParser.SwitchBlockStatementGroupContext ctx) {
        rewriter.insertAfter(ctx.getStop(), "</pre>");
        return super.visitSwitchBlockStatementGroup(ctx);
    }

    @Override public String visitSwitchLabel(JavaParser.SwitchLabelContext ctx) {
        String caseNum = "case"+caseCount;
        String color = this.visitedBlocks.contains(caseNum) ? "green" : "red";
        String injectedMessage = "<pre class='" + color + "'>";
        rewriter.insertBefore(ctx.getStart(), injectedMessage);
        ++caseCount;
        return super.visitSwitchLabel(ctx);
    }
}