package main.java.net.newtownia.ranksql.utils;

/**
 * Created by camdenorrb on 9/12/16.
 */
public interface Call<D> {

    void call(D data);

    default void onFail() {}

}

