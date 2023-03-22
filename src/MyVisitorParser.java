import org.antlr.v4.runtime.*;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

public class MyVisitorParser extends JavaParserBaseVisitor<String> {

    TokenStreamRewriter rewriter;
    int count = 0;

    public MyVisitorParser(TokenStreamRewriter rewriter){
        this.rewriter = rewriter;
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
