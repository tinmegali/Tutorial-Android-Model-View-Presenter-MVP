package com.tinmegali.mvp_tutorial;

import android.util.Log;

import com.tinmegali.mvp_tutorial.modelos.Nota;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * ---------------------------------------------------
 * Created by Tin Megali on 26/02/16.
 * Project: MVP_Tutorial
 * ---------------------------------------------------
 * <a href="http://www.tinmegali.com">tinmegali.com</a>
 * <a href="http://www.github.com/tinmegali>github</a>

 */
public class MainPresenter
        implements MainMVP.RequiredPresenterOps, MainMVP.PresenterOps {

    private String TAG = getClass().getSimpleName();

    // Referência para layer View
    private WeakReference<MainMVP.RequiredViewOps> mView;
    // Referência para o layer Model
    private MainMVP.ModelOps mModel;

    // Estado da mudança de configuração
    private boolean mIsChangingConfig;

    public MainPresenter(MainMVP.RequiredViewOps mView) {
        Log.d(TAG, "MainPresenter()");
        this.mView = new WeakReference<>(mView);
        this.mModel = new MainModel(this);
    }

    /**
     * Disparado por Activity após mudança de configuração
     * @param view  Referência para View
     */
    @Override
    public void onConfigurationChanged(MainMVP.RequiredViewOps view) {
        Log.d(TAG, "onConfigurationChanged()");

        mView = new WeakReference<>(view);
    }

    /**
     * Recebe evento {@link MainActivity#onDestroy()}
     * @param isChangingConfig  Se está mudando de config
     */
    @Override
    public void onDestroy(boolean isChangingConfig) {
        Log.d(TAG, "onDestroy(isChangingConfig:"+isChangingConfig+")");
        mView = null;
        mIsChangingConfig = isChangingConfig;
        if ( !isChangingConfig ) {
            mModel.onDestroy();
        }
    }


    /**
     * Chamado por {@link MainActivity} com a
     * interação do usuário de pedido para inserção de
     * nova nota
     */
    @Override
    public void novaNota(String textoNota) {
        Log.d(TAG, "novaNota()");
        Nota nota = new Nota();
        nota.setText(textoNota);
        nota.setDate(getDate());
        mModel.insereNota(nota);
    }

    /**
     * Chamado por {@link MainActivity}, pedido
     * para remoção de nota
     */
    @Override
    public void deletaNota(Nota nota) {
        Log.d(TAG, "deletaNota()");
        mModel.removeNota(nota);
    }

    /**
     * Recebe chamado de {@link MainModel} quando
     * Nota for inserida com sucesso no DB
     */
    @Override
    public void onNotaInserida(Nota novaNota) {
        Log.d(TAG, "onNotaInserida()");
        mView.get().showToast("Novo registro " + novaNota.getDate());
    }

    /**
     * Recebe chamado de {@link MainModel} quando
     * Nota for removida do DB
     */
    @Override
    public void onNotaRemovida(Nota notaRemovida) {
        Log.d(TAG, "onNotaRemovida()");
        mView.get().showToast("Nota de " + notaRemovida.getDate() + " removida");
    }

    /**
     * Recebe eventuais error de modelo,
     * e repassa mensagem para usuário
     */
    @Override
    public void onError(String errorMsg) {
        Log.d(TAG, "onError()");
        mView.get().showAlert(errorMsg);
    }


    /**
     * Retorna data atual
     */
    private String getDate(){
        Log.d(TAG, "getDate()");
        return new SimpleDateFormat("EEEE, dd/MM, kk:mm", Locale.getDefault()).format(new Date());
    }

    private Nota criaNota(String notaText){
        Log.d(TAG, "criaNota()");
        Nota nota = new Nota();
        nota.setText(notaText);
        nota.setDate( getDate() );
        return nota;
    }
}
