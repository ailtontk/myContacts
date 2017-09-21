package net.artgamestudio.rgatest.util;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ailtonramos on 21/09/2017.<br>
 *
 * Utils for all classes
 */
public class Util {

    /**
     * Fecha o teclado
     */
    public static void closeKeyboard(Activity activity)
    {
        //Pega o input method do sistema
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus(); //Recupera a view que esta com o foco atual

        //Se for nulo o foco
        if (view == null) {
            view = new View(activity); //Instancia nova view
        }

        //Esconde o teclado
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * Verifica se um email é valido
     * @param email O email a ser validado
     * @return True se for valido, false caso não seja
     */
    public static boolean isEmailValid(String email) {
        if ((email == null) || (email.trim().length() == 0))
            return false;

        String emailPattern = "\\b(^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@([A-Za-z0-9-])+(\\.[A-Za-z0-9-]+)*((\\.[A-Za-z0-9]{2,})|(\\.[A-Za-z0-9]{2,}\\.[A-Za-z0-9]{2,}))$)\\b";
        Pattern pattern = Pattern.compile(emailPattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Cria um novo Alert dialog com os textos informados.
     * @param context O contexto da tela
     * @param dialogInterfaceListener O listener dos botões de OK e cancelar
     * @param titulo O titulo do dialog
     * @param mensagem A mensagem do dialog
     * @param botaoSim O texto do botão sim
     * @param botaoNao O texto do botão não
     * @return Um alerta dialog para uso
     * @throws Exception
     */
    public static void criarAlertDialog(Context context, DialogInterface.OnClickListener dialogInterfaceListener, String titulo,
                                        String mensagem, String botaoSim, String botaoNao) throws Exception {
        new AlertDialog.Builder(context)
                .setTitle(titulo)
                .setMessage(mensagem)
                .setPositiveButton(botaoSim, dialogInterfaceListener)
                .setNegativeButton(botaoNao, dialogInterfaceListener)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
