package inference.interfaces;

import inference.Substitution;
import types.Type;

/**
 * Created by valentin on 25/10/2016.
 */
public interface Unifyable<Context> {
    Substitution unifyWith(Context context, Type type);
}
