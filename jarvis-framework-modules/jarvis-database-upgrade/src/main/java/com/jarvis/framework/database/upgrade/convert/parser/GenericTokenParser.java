package com.jarvis.framework.database.upgrade.convert.parser;

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
        if (text != null && !text.isEmpty()) {
            int start = text.indexOf(this.openSymbol);
            if (start == -1) {
                return text;
            } else {
                char[] src = text.toCharArray();
                int offset = 0;
                StringBuilder builder = new StringBuilder();
                StringBuilder expression = null;

                do {
                    if (start > 0 && src[start - 1] == '\\') {
                        builder.append(src, offset, start - offset - 1).append(this.openSymbol);
                        offset = start + this.openSymbol.length();
                    } else {
                        if (expression == null) {
                            expression = new StringBuilder();
                        } else {
                            expression.setLength(0);
                        }

                        builder.append(src, offset, start - offset);
                        offset = start + this.openSymbol.length();

                        int end;
                        for(end = text.indexOf(this.closeSymbol, offset); end > -1; end = text.indexOf(this.closeSymbol, offset)) {
                            if (end <= offset || src[end - 1] != '\\') {
                                expression.append(src, offset, end - offset);
                                break;
                            }

                            expression.append(src, offset, end - offset - 1).append(this.closeSymbol);
                            offset = end + this.closeSymbol.length();
                        }

                        if (end == -1) {
                            builder.append(src, start, src.length - start);
                            offset = src.length;
                        } else {
                            builder.append(this.handler.handle(expression.toString()));
                            offset = end + this.closeSymbol.length();
                        }
                    }

                    start = text.indexOf(this.openSymbol, offset);
                } while(start > -1);

                if (offset < src.length) {
                    builder.append(src, offset, src.length - offset);
                }

                return builder.toString();
            }
        } else {
            return "";
        }
    }
}
