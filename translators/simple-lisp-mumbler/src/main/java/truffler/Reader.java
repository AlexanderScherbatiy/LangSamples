package truffler;

import truffler.form.BooleanForm;
import truffler.form.Form;
import truffler.form.ListForm;
import truffler.form.NumberForm;
import truffler.form.SpecialForm;
import truffler.form.StringForm;
import truffler.form.SymbolForm;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PushbackReader;


public class Reader {
    public static ListForm read(InputStream istream) throws IOException {
        return read(new PushbackReader(new InputStreamReader(istream)));
    }

    private static ListForm read(PushbackReader pstream) throws IOException {
        ListForm forms = ListForm.EMPTY;

        readWhitespace(pstream);
        char c = (char) pstream.read();
        while ((byte) c != -1) {
            pstream.unread(c);
            forms = forms.cons(readForm(pstream));
            readWhitespace(pstream);
            c = (char) pstream.read();
        }
        return forms;
    }

    public static Form readForm(PushbackReader pstream) throws IOException {
        char c = (char) pstream.read();
        if (c == '(') {
            return readList(pstream);
        } else if (Character.isDigit(c)) {
            pstream.unread(c);
            return readNumber(pstream);
        } else if (c == '"') {
            return readString(pstream);
        } else if (c == '#') {
            return readBoolean(pstream);
        } else if (c == ')') {
            throw new IllegalArgumentException("Unmatched close paren");
        } else {
            pstream.unread(c);
            return readSymbol(pstream);
        }
    }

    private static void readWhitespace(PushbackReader pstream)
            throws IOException {
        char c = (char) pstream.read();
        while (Character.isWhitespace(c)) {
            c = (char) pstream.read();
        }
        pstream.unread(c);
    }

    private static SymbolForm readSymbol(PushbackReader pstream)
            throws IOException {
        StringBuilder b = new StringBuilder();
        char c = (char) pstream.read();
        while (!(Character.isWhitespace(c) || (byte) c == -1
                || c == '(' || c == ')')) {
            b.append(c);
            c = (char) pstream.read();
        }
        pstream.unread(c);
        return new SymbolForm(b.toString());
    }

    private static Form readList(PushbackReader pstream)
            throws IOException {
        // open paren is already read
        ListForm list = ListForm.EMPTY;
        readWhitespace(pstream);
        char c = (char) pstream.read();
        while (true) {
            if (Character.isWhitespace(c)) {
                // pass
            } else if (c == ')') {
                // end of list
                break;
            } else if ((byte) c == -1) {
                throw new EOFException("EOF reached before closing of list");
            } else {
                pstream.unread(c);
                list = list.cons(readForm(pstream));
            }
            c = (char) pstream.read();
        }
        return SpecialForm.check(list.reverse());
    }

    private static NumberForm readNumber(PushbackReader pstream)
            throws IOException {
        StringBuilder b = new StringBuilder();
        char c = (char) pstream.read();
        while (Character.isDigit(c)) {
            b.append(c);
            c = (char) pstream.read();
        }
        pstream.unread(c);
        return new NumberForm(Long.valueOf(b.toString(), 10));
    }

    private static StringForm readString(PushbackReader pstream)
            throws IOException {
        StringBuilder b = new StringBuilder();
        char c = (char) pstream.read();
        while (c != '"') {
            b.append(c);
            c = (char) pstream.read();
        }
        return new StringForm(b.toString());
    }

    private static final SymbolForm TRUE_SYM = new SymbolForm("t");
    private static final SymbolForm FALSE_SYM = new SymbolForm("f");

    private static BooleanForm readBoolean(PushbackReader pstream)
            throws IOException {
        // '#' already read
        SymbolForm sym = readSymbol(pstream);
        if (TRUE_SYM.equals(sym)) {
            return BooleanForm.TRUE;
        } else if (FALSE_SYM.equals(sym)) {
            return BooleanForm.FALSE;
        } else {
            throw new IllegalArgumentException("Unknown value: #" + sym.name);
        }
    }
}
