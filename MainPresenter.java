package com.tinmegali.mvp_tutorial;

import android.util.Log;

import com.tinmegali.mvp_tutorial.modelos.Note;

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

    // Layer View reference
    // Referência para layer View
    private WeakReference<MainMVP.RequiredViewOps> mView;

    // Layer Model reference
    // Referência para o layer Model
    private MainMVP.ModelOps mModel;

    // Configuration change state
    // Estado da mudança de configuração
    private boolean mIsChangingConfig;

    public MainPresenter(MainMVP.RequiredViewOps mView) {
        Log.d(TAG, "MainPresenter()");
        this.mView = new WeakReference<>(mView);
        this.mModel = new MainModel(this);
    }

    /**
     * Sent from Activity after a configuration changes
     *
     * Disparado por Activity após mudança de configuração
     *
     * @param view  View reference
     */
    @Override
    public void onConfigurationChanged(MainMVP.RequiredViewOps view) {
        Log.d(TAG, "onConfigurationChanged()");

        mView = new WeakReference<>(view);
    }

    /**
     * Receives {@link MainActivity#onDestroy()} event
     * @param isChangingConfig  Config change state
     *
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
     * Called by user interaction from {@link MainActivity}
     * creates a new Note
     *
     * Chamado por {@link MainActivity} com a
     * interação do usuário de pedido para inserção de
     * nova nota
     */
    @Override
    public void newNote(String noteText) {
        Log.d(TAG, "newNote()");
        Note note = new Note();
        note.setText(noteText);
        note.setDate(getDate());
        mModel.insertNote(note);
    }

    /**
     * Called from {@link MainActivity},
     * Removes a Note
     *
     * Chamado por {@link MainActivity}, pedido
     * para remoção de nota
     */
    @Override
    public void deleteNote(Note note) {
        Log.d(TAG, "deleteNote()");
        mModel.removeNote(note);
    }

    /**
     * Called from {@link MainModel}
     * when a Note is inserted successfully
     *
     * Recebe chamado de {@link MainModel} quando
     * Nota for inserida com sucesso no DB
     */
    @Override
    public void onNoteInserted(Note novaNote) {
        Log.d(TAG, "onNoteInserted()");
        mView.get().showToast("New " + novaNote.getDate());
    }

    /**
     * Receives call from {@link MainModel}
     * when Note is removed
     *
     * Recebe chamado de {@link MainModel} quando
     * Nota for removida do DB
     */
    @Override
    public void onNoteRemoved(Note removedNote) {
        Log.d(TAG, "onNoteRemoved()");
        mView.get().showToast("Note " + removedNote.getDate() + " removed");
    }

    /**
     * receive errors
     *
     * Recebe erros
     */
    @Override
    public void onError(String errorMsg) {
        Log.d(TAG, "onError()");
        mView.get().showAlert(errorMsg);
    }


    /**
     * Returns current date
     *
     * Retorna data atual
     */
    private String getDate(){
        Log.d(TAG, "getDate()");
        return new SimpleDateFormat("EEEE, dd/MM, kk:mm", Locale.getDefault()).format(new Date());
    }

    private Note getNote(String noteText){
        Log.d(TAG, "getNote()");
        Note note = new Note();
        note.setText(noteText);
        note.setDate( getDate() );
        return note;
    }
}
