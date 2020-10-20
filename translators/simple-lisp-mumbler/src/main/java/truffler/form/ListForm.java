package truffler.form;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import truffler.Fn;
import truffler.env.Environment;

public class ListForm extends Form implements Iterable<Form> {
    public static final ListForm EMPTY = new ListForm();

    public final Form car;
    public final ListForm cdr;
    private final int length;

    private ListForm() {
        this.car = null;
        this.cdr = null;
        this.length = 0;
    }

    private ListForm(Form car, ListForm cdr) {
        this.car = car;
        this.cdr = cdr;
        this.length = cdr.length + 1;
    }

    public ListForm cons(Form form) {
        return new ListForm(form, this);
    }

    public ListForm reverse() {
        ListForm r = EMPTY;
        ListForm l = this;

        while (l != EMPTY) {
            r = r.cons(l.car);
            l = l.cdr;
        }

        return r;
    }

    public long length() {
        return this.length;
    }

    public Iterator<Form> iterator() {
        return new Iterator<Form>() {
            private ListForm l = ListForm.this;

            @Override
            public boolean hasNext() {
                return this.l != EMPTY;
            }

            @Override
            public Form next() {
                if (this.l == EMPTY) {
                    throw new IllegalStateException("At the end of list");
                }
                Form car = this.l.car;
                this.l = this.l.cdr;
                return car;
            }

            @Override
            public void remove() {
                throw new IllegalStateException("Iterator is immutable");
            }
        };
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ListForm)) {
            return false;
        }
        if (this == EMPTY && other == EMPTY) {
            return true;
        }

        ListForm that = (ListForm) other;

        if (this.length() != ((ListForm) other).length()) {
            return false;
        }

        return this.car.equals(that.car) && this.cdr.equals(that.cdr);
    }

    @Override
    public String toString() {
        if (this == EMPTY) {
            return "()";
        }

        StringBuilder b = new StringBuilder();
        b.append('(');
        b.append(this.car);

        ListForm list = this.cdr;
        while (list != EMPTY) {
            b.append(" ");
            b.append(list.car);
            list = list.cdr;
        }

        b.append(")");

        return b.toString();
    }

    @Override
    public Object eval(Environment env) {
        Fn fn = (Fn) this.car.eval(env);

        if (fn == null) {
            throw new UnsupportedOperationException("Undefined value: " +
                    this.car);
        }

        List<Object> args = new ArrayList<Object>();
        for (Form form : this.cdr) {
            args.add(form.eval(env));
        }
        return fn.apply(args.toArray());
    }
}
