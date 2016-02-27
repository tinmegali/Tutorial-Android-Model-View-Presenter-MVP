package com.tinmegali.mvp_tutorial;

import android.util.Log;

import com.tinmegali.mvp_tutorial.modelos.Nota;

/**
 * ---------------------------------------------------
 * Created by Tin Megali on 26/02/16.
 * Project: MVP_Tutorial
 * ---------------------------------------------------
 * <a href="http://www.tinmegali.com">tinmegali.com</a>
 * <a href="http://www.github.com/tinmegali>github</a>

 */
public class MainModel
        implements MainMVP.ModelOps {

    private String TAG = getClass().getSimpleName();

    // Referência para layer Presenter
    private MainMVP.RequiredPresenterOps mPresenter;

    public MainModel(MainMVP.RequiredPresenterOps mPresenter) {
        this.mPresenter = mPresenter;
    }

    /**
     * Disparada por {@link MainPresenter#onDestroy(boolean)}
     * para as operações necessárias que eventualmente
     * estiverem executando no BG
     */
    @Override
    public void onDestroy() {
        Log.d(TAG, "startMVPOps()");
        // ações para destruir objeto
    }

    // insere Nota no DB
    @Override
    public void insereNota(Nota nota) {
        Log.d(TAG, "startMVPOps()");
        // lógica de inserção
        // ...
        mPresenter.onNotaInserida(nota);
    }

    // remove Nota do DB
    @Override
    public void removeNota(Nota nota) {
        Log.d(TAG, "startMVPOps()");
        // lógica de remoção
        // ...
        mPresenter.onNotaRemovida(nota);
    }
}
