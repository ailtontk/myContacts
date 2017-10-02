package net.artgamestudio.rgatest.util;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import net.artgamestudio.rgatest.R;

/**
 * Created by Ailton on 21/09/2017.<br>
 * Utils for views.
 */
public class UtilView {

    private static ProgressDialog mProgressDialog;

    /**
     * Altera a visibilidade de uma view baseado no tempo
     *
     * @param view         A view a ser alterada
     * @param tempo        O tempo a ser alterado em milisegundos. (ex. 1000 = 1 segundo)
     * @param visibilidade A visibilidade a ser alerada. (Ex. View.GONE)
     */
    public static void changeVisibilityAfter(final View view, long tempo, final int visibilidade) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setVisibility(visibilidade);
            }
        }, tempo);
    }

    /**
     *  Changes a textview, edittext or button text after a determinated time
     *
     * @param view         The view that will be changed
     * @param time        Time to change (ex. 1000 = 1 sec)
     * @param text text that will be placed on view.
     */
    public static void changeTextAfter(final TextView view, long time, final String text) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setText(text);
            }
        }, time);
    }

    /**
     * Animates a view on screen
     * @param context App context
     * @param view view to be animated
     * @param startDelay Animate after x milliseconds
     * @param animId The anim id that will be used (Ex. R.animator.anim). If it is empty, showing up will be used.
     * @throws Exception
     */
    public static void animateView(Context context, View view, long startDelay, int...animId) throws Exception {
        int id = animId != null && animId.length > 0 ? animId[0] : R.animator.anim_showing_up;
        AnimatorSet animator = (AnimatorSet) AnimatorInflater.loadAnimator(context, id);

        //set targets
        animator.setTarget(view);
        animator.setStartDelay(startDelay);
        animator.start();
    }

    /**
     * Changes the selected status of a view and its background
     * @param context The app context
     * @param view The view to change
     * @param selected True if is selected false otherwise
     */
    public static void setSelected(Context context, View view, boolean selected) {
        view.setSelected(selected);
        view.setBackgroundColor(selected ? ContextCompat.getColor(context, R.color.colorSelected) : ContextCompat.getColor(context, R.color.colorUnselected));
    }

    /**
     * Exibe um progress dialog na tela
     *
     * @param context  O contexto da tela
     * @param mensagem A mensagem a ser exibida, ou nada se desejar usar a padrão
     * @throws Exception
     */
    public static void showProgress(Context context, String... mensagem) throws Exception {

        //se nao existir dados, cria um
        if (mProgressDialog == null)
            mProgressDialog = new ProgressDialog(context);

        if (!mProgressDialog.isShowing())
            mProgressDialog = new ProgressDialog(context);

        //Se houve mensagem, adiciona
        if (mensagem != null && mensagem.length > 0)
            mProgressDialog.setMessage(mensagem[0]);
        else
            mProgressDialog.setMessage(context.getString(R.string.processing));

        //Exibe
        mProgressDialog.show();
    }

    /**
     * Remove o progress da tela
     *
     * @throws Exception
     */
    public static void hideProgress() {
        if (mProgressDialog == null)
            return;
        mProgressDialog.dismiss();
    }

    /**
     * Starts a circular reveal animation on the specific view.<bR>
     * The view must be under io.codetail.widget.RevealFrameLayout layout.
     *
     * @param view View under io.codetail.widget.RevealFrameLayout layout to be revealed.
     * @throws Exception
     */
    public static void circularRevealAnimation(View view, long duration) throws Exception {

        //if it's lolipop or higher
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return;
        }

        // get the center for the clipping circle
        int cx = (view.getLeft() + view.getRight()) / 2;
        int cy = (view.getTop() + view.getBottom()) / 2;

        // get the final radius for the clipping circle
        int dx = Math.max(cx, view.getWidth() - cx);
        int dy = Math.max(cy, view.getHeight() - cy);
        float finalRadius = (float) Math.hypot(dx, dy);

        // Android native animator
        Animator animator =
                ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(duration);
        animator.start();
    }

    /**
     * Changes the current font to the charadesApp font.
     *
     * @param textView The textview to send
     * @throws Exception
     */
    public static void changeFont(Context context, TextView textView) throws Exception {
        //Get the type face from text view
        Typeface typeface = textView.getTypeface();

        //Apply the font
        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/bold.ttf");
        textView.setTypeface(typeface);
    }

    /**
     * Returns the height of a status bar
     * @return Height of the status bar
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * Creates and show a new alertdialog with all texts inside it
     * @param context App context
     * @param listenerPositive O listener dos botões de OK e cancelar
     * @param titulo Dialog's title
     * @param message Dialog's message
     * @param buttonPositive Button yes text
     * @param buttonNegative Button no text
     */
    public static void createAlertDialog(Context context, DialogInterface.OnClickListener listenerPositive, DialogInterface.OnClickListener listenerNegative, String titulo,
                                         String message, String buttonPositive, String buttonNegative) throws Exception {
        new AlertDialog.Builder(context)
                .setTitle(titulo)
                .setMessage(message)
                .setPositiveButton(buttonPositive, listenerPositive)
                .setNegativeButton(buttonNegative, listenerNegative)
                .show();
    }

    public static boolean checkWhiteBackGroundInFrontOfIcons(ImageView imageView) {
        Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        int height = 2;
        int pixelCount = 0;

        int[] imagePixels = new int[bitmap.getWidth() * height];
        bitmap.getPixels(imagePixels, 0, bitmap.getWidth(), 0, 5, bitmap.getWidth(), height);

        for (int pixel : imagePixels) {
            //Check only on beggin and end of image
            if (pixel >= -1) {
                //Count all white pixel
                pixelCount++;
            }
        }

        //If has more than 270 white pixels, its a white image
        return pixelCount > 270;
    }
}
