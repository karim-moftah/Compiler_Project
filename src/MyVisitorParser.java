import org.antlr.v4.runtime.*;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

public class MyVisitorParser extends JavaParserBaseVisitor<String> {

    TokenStreamRewriter rewriter;
    int count = 0;

    public MyVisitorParser(TokenStreamRewriter rewriter){
        this.rewriter = rewriter;
    }
    void write(String string) throws Exception {
        String outputFileName = "outputs/task_01.java";
        FileOutputStream outputFile = new FileOutputStream(outputFileName);
        BufferedOutputStream buffer = new BufferedOutputStream(outputFile);
        byte[] bytes = string.getBytes();
        buffer.write(bytes);
        buffer.close();
    }


    @Override
    public String visitBlock(JavaParser.BlockContext ctx) {
        try {
            rewriter.insertAfter(ctx.getStart(),"  // block number"+count);
            ++count;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return super.visitBlock(ctx);
    }

    @Override
    public String visitClassBody(JavaParser.ClassBodyContext ctx) {
        try {
            rewriter.insertAfter(ctx.getStart(),"  // block number"+count);
            ++count;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return super.visitClassBody(ctx);
    }
}
