
package cz.it4i.parallel.runners.logging.ui;

public interface RedirectedOutputService {

	public void register(Object object);

	public void unregister(Object object);

	public void post(Object event);
}
