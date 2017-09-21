package net.artgamestudio.rgatest.base;

import android.content.Context;
import android.util.Log;

import net.artgamestudio.rgatest.base.interfaces.IComponentContact;


/**
 * Created by Ailton on 20/05/2017.<br>
 * Base RN class
 */
public class BaseRN implements IComponentContact {

    protected Context mContext; //contexto da aplicação
    protected IComponentContact mContact; //Interface para contatar a classe que chamou

    /**
     * O construtor base da classe.
     * @param context O contexto da aplicação.
     * @param contact A interface de comunicação base das activities e fragmentos.
     */
    public BaseRN(Context context, IComponentContact contact) {
        mContext = context;
        mContact = contact;
    }

    /**
     * It will be called whenever a method contacts the activity by IComponentContact interface.<br>
     * Treat all events or messages from another component using a really clean and effective method.<br>
     * @param fromClass The class that have contacted this activity. Use if (fromClass == SomeClass.class){} to check what class it is.
     * @param id If have multiples callings from sender class, use ID to identify it.
     * @param result If have a result
     * @param objects if have extra br.com.shopper.shopperapp.ui.data to use
     * @throws Exception
     */
    protected void onReceiveData(Class fromClass, int id, boolean result, Object... objects) throws Exception {};

    //------------------------------------ FINAL METHODS ------------------------------------
    @Override
    public void contactComponent(Class fromClass, int id, boolean result, Object... objects)
    {
        try {
            //Call the event to receive br.com.shopper.shopperapp.ui.data
            this.onReceiveData(fromClass, id, result, objects);
        } catch (Exception error) {
            Log.e("Error","Erro ao receber componente em " + getClass().getName() + ". " + error.getMessage());
        }
    }
}
