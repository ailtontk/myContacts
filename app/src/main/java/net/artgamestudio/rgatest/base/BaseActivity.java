package net.artgamestudio.rgatest.base;

import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import net.artgamestudio.rgatest.base.interfaces.IComponentContact;

import butterknife.ButterKnife;


/**
 * Created by Ailton on 26/01/2016.<br><br>
 *
 * A classe base das Activities da aplicação.<br>
 * Utilize essa classe para prover um fluxo de dados automático para as activities criadas.
 * Ao extender desta classe, basta sobreescrever o método <b>setupData()</b> e chamar a super classe
 * do método onCreate <b>após</b> a chamada do método <b>setContentView()</b>.<br><br>
 *
 * Utilize o método onReceiveData para tratar chamadas de outros componentes. Nunca passe a instancia de
 * uma activity para um componente externo (tais como Tasks, services, Fragmentos e outros).<br>
 *
 * Ao criar um desses componentes internos, utilize o construtor para obter o IComponentContact da activity.
 * Ao criar o componente, utilize o <b>this</b> para entregar o IComponentContact para o componente. Sempre que
 * o componente externo chamar por contactComponent(), o método onReceivedata será chamado.
 */
public abstract class BaseActivity extends AppCompatActivity implements IComponentContact {

    /**
     * You must return the layout id that will be setted at OnCreate.<br>
     * If you have a fullscreen activity, <b>Call setFullscreen before returns!</b><br><br>
     * e.g:<br>
     * public int setView() throws Exception {<br>
     *      return R.layout.activity_main;<br>
     * }<br>
     *
     * <br><br>
     * Or e.g with fullscreen:
     * <br>
     * public int setView() throws Exception {<br>
     *      setFullscreen();<br>
     *      return R.layout.activity_main;<br>
     * }<br>
     *
     * @throws Exception
     */
    public abstract int setView() throws Exception;

    /**
     * Keeps screen on
     * @throws Exception
     */
    public void keepScreenOn() throws Exception {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            int layoutId = setView();
            setContentView(layoutId);
            ButterKnife.bind(this);

            //get parameters on intent
            this.getParam();
            //if it's lolipop or higher
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                this.setSharedElements();
            }
            //Set br.com.shopper.shopperapp.ui.data on views
            this.setupData();
            //get toolbar
            this.setupToolbar();
        } catch (Exception error) {
            Log.e("Error","Error at OnCreate() at BaseActivity. Origin: " + getClass().getName() + ". Happened: " + error.getMessage());
        }
    }

    /**
     * Called at OnCreate() to define br.com.shopper.shopperapp.ui.data for each view.
     * Put texts, images, colors or listeners in views just at this method.
     * If you have to implement a listener for a view, may you should create a new method and
     * call it here.
     * @throws NullPointerException
     */
    public abstract void setupData() throws Exception;

    /**
     * Configures toolbar here, if this activity has one.
     * @throws NullPointerException
     */
    public void setupToolbar() throws Exception {}

    /**
     * Called at OnCreate() to get intent
     * @throws NullPointerException
     */
    public void getParam() throws Exception {}

    /**
     * If the activity has a shared elements, set up transition name here.
     * @throws Exception
     */
    protected void setSharedElements() throws Exception {}

    /**
     * Add a fragment in some container of this acativity
     * @param containerViewId R.id.container? Your container id
     * @param fragment A new instance of the fragment
     * @param fragmentTag A tag to identify the fragment
     */
    protected void addFragment(@IdRes int containerViewId,
                               @NonNull Fragment fragment,
                               @NonNull String fragmentTag) throws Exception {
        getSupportFragmentManager()
                .beginTransaction()
                .add(containerViewId, fragment, fragmentTag)
                .disallowAddToBackStack()
                .commit();
    }

    /**
     * Change the current fragment for a new one.
     * @param containerViewId R.id.container? Your container id (Use one that already have a fragment)
     * @param fragment A new instance of the fragment
     * @param fragmentTag A tag to identify the new fragment
     * @param backStackStateName A identify for the stack
     */
    protected void replaceFragment(@IdRes int containerViewId,
                                   @NonNull Fragment fragment,
                                   @NonNull String fragmentTag,
                                   @Nullable String backStackStateName) throws Exception {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(containerViewId, fragment, fragmentTag)
                .addToBackStack(backStackStateName)
                .commit();
    }

    /**
     * Must be called at <b>setView before its returns</b> otherwise
     * it won't work.
     */
    public void setFullScreen() {
        try {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
            if (Build.VERSION.SDK_INT < 19) { //19 or above api
                View v = this.getWindow().getDecorView();
                v.setSystemUiVisibility(View.GONE);
            } else {
                //for lower api versions.
                View decorView = getWindow().getDecorView();
                int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
                decorView.setSystemUiVisibility(uiOptions);
            }
        } catch (Exception error) {
            Log.e("Error", "Error at setFullScreen in " + getClass().getName() + ". " + error.getMessage());
        }
    }

    /**
     * Open the popup menu for the option menu at the toolbar.<br><br>
     * @param viewId The mnuOption id, usually the item that users it clicking in.
     * @param mnuId The menu layout from menu resource.
     * @throws Exception
     * @return The popupmenu to set listeners and show or null if mnuItem doesn't exists.
     */
    protected PopupMenu createPopupMenu(int viewId, int mnuId) throws Exception {
        View mnuItem = findViewById(viewId);

        //If mnuItem doesn't exists
        if (mnuItem == null)
            return null;

        //creates the popup menu
        PopupMenu popupMenu = new PopupMenu(this, mnuItem);
        popupMenu.inflate(mnuId);

        return popupMenu;
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
    public void contactComponent(final Class fromClass, final int id, final boolean result, final Object... objects)
    {
        try {
            //Se não estiver na thread principal, cria um runnable para rodar na principal
            if (Looper.myLooper() != Looper.getMainLooper()){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            onReceiveData(fromClass, id, result, objects);
                        } catch (Exception error) {
                            Log.e("Error","Erro ao receber componente em " + getClass().getName() + ". " + error.getMessage());
                        }
                    }
                });
                return;
            }

            //Se ja está na principal, executa sem precisar criar um runnable
            this.onReceiveData(fromClass, id, result, objects);
        } catch (Exception error) {
            Log.e("Error","Erro ao receber componente em " + getClass().getName() + ". " + error.getMessage());
        }
    }
}
