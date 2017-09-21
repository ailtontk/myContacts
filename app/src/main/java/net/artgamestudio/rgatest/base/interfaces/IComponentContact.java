package net.artgamestudio.rgatest.base.interfaces;

/**
 * Created by Ailton on 31/10/2016.<br>
 * Used to provide contact between components
 */
public interface IComponentContact {

    /**
     * Whenever a component needs to contact a fragment, this method should be called.<br>
     *
     * onReceiveData() will be called in base fragment and all contacts must be treated there.
     * @param fromClass The class you currently are (usually use this.getClass())
     * @param id If you have multiple methods calling this method, pass an id to identify it.
     * @param result If you have a result for some operation
     * @param objects If you have extra objects or br.com.shopper.shopperapp.ui.data to pass
     */
    void contactComponent(Class fromClass, int id, boolean result, Object... objects);

}
