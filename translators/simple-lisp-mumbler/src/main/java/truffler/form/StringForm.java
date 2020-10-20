package truffler.form;

import truffler.env.Environment;

public class StringForm extends Form {

    private final String str;

    public StringForm(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return String.format("\"%s\"", str);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof StringForm &&
                this.str == ((StringForm) other).str;
    }

    @Override
    public Object eval(Environment env) {
        return this.str;
    }
}
