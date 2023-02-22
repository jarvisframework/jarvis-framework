package com.jarvis.framework.database.upgrade.convert.parser;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年4月8日
 */
public class GenericTokenParser {

    private final String openSymbol;

    private final String closeSymbol;

    private final TokenHandler handler;

    public GenericTokenParser(String openToken, String closeToken, TokenHandler handler) {
        this.openSymbol = openToken;
        this.closeSymbol = closeToken;
        this.handler = handler;
    }

    public String parse(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }
        // search open token
        int start = text.indexOf(openSymbol);
        if (start == -1) {
            return text;
        }
        final char[] src = text.toCharArray();
        int offset = 0;
        final StringBuilder builder = new StringBuilder();
        StringBuilder expression = null;
        do {
            if (start > 0 && src[start - 1] == '\\') {
                // this open token is escaped. remove the backslash and continue.
                builder.append(src, offset, start - offset - 1).append(openSymbol);
                offset = start + openSymbol.length();
            } else {
                // found open token. let's search close token.
                if (expression == null) {
                    expression = new StringBuilder();
                } else {
                    expression.setLength(0);
                }
                builder.append(src, offset, start - offset);
                offset = start + openSymbol.length();
                int end = text.indexOf(closeSymbol, offset);
                while (end > -1) {
                    if (end > offset && src[end - 1] == '\\') {
                        // this close token is escaped. remove the backslash and continue.
                        expression.append(src, offset, end - offset - 1).append(closeSymbol);
                        offset = end + closeSymbol.length();
                        end = text.indexOf(closeSymbol, offset);
                    } else {
                        expression.append(src, offset, end - offset);
                        break;
                    }
                }
                if (end == -1) {
                    // close token was not found.
                    builder.append(src, start, src.length - start);
                    offset = src.length;
                } else {
                    builder.append(handler.handle(expression.toString()));
                    offset = end + closeSymbol.length();
                }
            }
            start = text.indexOf(openSymbol, offset);
        } while (start > -1);
        if (offset < src.length) {
            builder.append(src, offset, src.length - offset);
        }
        return builder.toString();
    }
}
