package truffler;

import truffler.env.Environment;

public interface Evaluatable {
    Object eval(Environment env);
}
