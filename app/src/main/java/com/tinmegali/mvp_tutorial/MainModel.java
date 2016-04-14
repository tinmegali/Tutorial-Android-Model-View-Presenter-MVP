package com.tinmegali.mvp_tutorial;

import android.util.Log;

import com.tinmegali.mvp_tutorial.modelos.Note;

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

    // Presenter reference
    // Referência para layer Presenter
    private MainMVP.RequiredPresenterOps mPresenter;

    public MainModel(MainMVP.RequiredPresenterOps mPresenter) {
        this.mPresenter = mPresenter;
    }

    /**
     * Sent from {@link MainPresenter#onDestroy(boolean)}
     * Should stop/kill operations that could be running
     * and aren't needed anymore
     *
     * Disparada por {@link MainPresenter#onDestroy(boolean)}
     * para as operações necessárias que eventualmente
     * estiverem executando no BG
     */
    @Override
    public void onDestroy() {
        Log.d(TAG, "startMVPOps()");
        // destroying actions
    }

    // Insert Note in DB
    // insere Nota no DB
    @Override
    public void insertNote(Note note) {
        Log.d(TAG, "startMVPOps()");
        // data business logic
        // ...
        mPresenter.onNoteInserted(note);
    }

    // Removes Note from DB
    // remove Nota do DB
    @Override
    public void removeNote(Note note) {
        Log.d(TAG, "startMVPOps()");
        // data business logic
        // ...
        mPresenter.onNoteRemoved(note);
    }
}
