import org.antlr.v4.runtime.*;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

public class MyVisitorParser extends JavaParserBaseVisitor<String> {

    TokenStreamRewriter rewriter;
    int count = 1;
    int caseCount = 0;

    public MyVisitorParser(TokenStreamRewriter rewriter) {
        this.rewriter = rewriter;
    }

    void write(String string) throws Exception {
        String outputFileName = "outputs/output.java";
        FileOutputStream outputFile = new FileOutputStream(outputFileName);
        BufferedOutputStream buffer = new BufferedOutputStream(outputFile);
        byte[] bytes = string.getBytes();
        buffer.write(bytes);
        buffer.close();
    }

    @Override
    public String visitCompilationUnit(JavaParser.CompilationUnitContext ctx) {

        rewriter.insertBefore(ctx.getStart(), "import java.util.*;\nimport java.io.*;\n");
        return super.visitCompilationUnit(ctx);

    }

    @Override
    public String visitClassBody(JavaParser.ClassBodyContext ctx) {

        rewriter.insertAfter(ctx.getStart(), "\n\n\tstatic public String blocksVisited[] = {};");
        rewriter.insertAfter(ctx.getStart(), "\n\n\t\tstatic public Set<String> blocks = new HashSet<String>();");
        rewriter.insertAfter(ctx.getStart(), "\n\tstatic public ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(blocksVisited));\n");

        return super.visitClassBody(ctx);
    }

    @Override
    public String visitBlock(JavaParser.BlockContext ctx) {

        rewriter.insertAfter(ctx.getStart(), "\t\t// block number " + count + '\n');
        rewriter.insertAfter(ctx.getStart(), "\t\t\tarrayList.add(" + '"' + count+ '"' +");");
        ++count;

        return super.visitBlock(ctx);
    }

    @Override
    public String visitIfbranch(JavaParser.IfbranchContext ctx) {
        if (!ctx.statement().getStart().getText().equals("{")) {

            rewriter.insertBefore(ctx.statement().getStart(), "{" + "\t\t\t\t\t\t\t\t\t\t// block number " + count + '\n' + "\t\t\tarrayList.add(" +'"' +count + '"'+");\n\t\t\t");
            rewriter.insertAfter(ctx.statement().getStop(), "\n\t\t\t}");
            ++count;

        }
        return super.visitIfbranch(ctx);
    }

    @Override
    public String visitElseif(JavaParser.ElseifContext ctx) {
        if (!ctx.getChild(1).getText().toString().startsWith("if(")) {
            if(!ctx.statement().getStart().getText().equals("{")){
                // inside else not if else
                rewriter.insertBefore(ctx.statement().getStart(), "{" + "\t\t\t\t\t\t\t\t\t\t// block number " + count + '\n' + "\t\t\tarrayList.add(" + '"'+ count + '"'+");\n\t\t\t");
                rewriter.insertAfter(ctx.statement().getStop(), "\n\t\t\t}");
                ++count;
            }

        }
        return super.visitElseif(ctx);
    }

    @Override
    public String visitMethodDeclaration(JavaParser.MethodDeclarationContext ctx) {
        if (ctx.getChild(1).getText().equals("main")) {

            rewriter.insertBefore(ctx.getStop(),
                    "\n\t\tblocksVisited = arrayList.toArray(blocksVisited);\n\t\tString visitedBlocks = \"\";\n" +
                            "\t\tfor(String blockNums: blocksVisited)\n\t\t{\nblocks.add(blockNums);\n" +
                            "\t\t\t\n"+
                            "\n\t\t\t\tvisitedBlocks += \"Block #\"+blockNums+\" is visited\\n\";\n" +
                            "\t\t}\n\tvisitedBlocks = blocks + \"\\n\" + visitedBlocks;\n\twrite(\"outputs/blocksVisited.txt\",visitedBlocks);\n\t\t\tString Ss = \"\";\n" +
                            "\t\tfor(String elements : blocks) {\n" +
                            "\t\t\tSs = Ss + elements + \"\\n\";\n" +
                            "\t\t}\n" +
                            "\t\twrite(\"outputs/blocks.txt\",Ss);\n\n");

            rewriter.insertAfter(ctx.getStop(),"\n\tpublic static void write(String filename,String string)  {\n" +
                    "\t\ttry{\n" +
                    "\t\t\tString outputFileName = filename;\n" +
                    "\t\t\tFileOutputStream outputFile = new FileOutputStream(outputFileName);\n" +
                    "\t\t\tBufferedOutputStream buffer = new BufferedOutputStream(outputFile);\n" +
                    "\t\t\tbyte[] bytes = string.getBytes();\n" +
                    "\t\t\tbuffer.write(bytes);\n" +
                    "\t\t\tbuffer.close();\n" +
                    "\t\t}catch (Exception e) {\n" +
                    "\t\t\te.printStackTrace();\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t}\n");
        }
        return super.visitMethodDeclaration(ctx);
    }


    @Override public String visitSwitchLabel(JavaParser.SwitchLabelContext ctx) {
        rewriter.insertAfter(ctx.getStop(), "\n\t\t\t\tarrayList.add(" +'"' + "case" + caseCount + '"' +");");
        ++caseCount;
        return super.visitSwitchLabel(ctx);
    }

}
