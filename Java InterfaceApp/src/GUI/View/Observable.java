package GUI.View;

import event.Event;

public interface Observable<E extends Event, F extends Event, G extends Event> {
    void addObserverE(Observer<E> e);
    void removeObserverE(Observer<E> e);
    void notifyObserversE(E e);

    void addObserverF(Observer<F> e);
    void removeObserverF(Observer<F> e);
    void notifyObserversF(F e);

    void addObserverG(Observer<G> e);
    void removeObserverG(Observer<G> e);
    void notifyObserversG(G e);
}
