package inference.interfaces;


/**
 * Created by valentin on 25/10/2016.
 */
public interface Inferable<Context, Return> {
    Return infer(Context context);
    Return infer();
}
