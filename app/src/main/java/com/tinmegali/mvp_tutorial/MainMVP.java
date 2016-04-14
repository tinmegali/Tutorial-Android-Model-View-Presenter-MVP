package com.tinmegali.mvp_tutorial;

import com.tinmegali.mvp_tutorial.modelos.Note;

/**
 * ---------------------------------------------------
 * Created by Tin Megali on 26/02/16.
 * Project: MVP_Tutorial
 * ---------------------------------------------------
 * <a href="http://www.tinmegali.com">tinmegali.com</a>
 * <a href="http://www.github.com/tinmegali>github</a>
 *
 * Aggregates all communication operations between MVP pattern layer:
 * Model, View and Presenter
 *
 *
 * Interface MVP guarda-chuva, contém todos as operações necessárias
 * para funcionamento do padrão
 */
public interface MainMVP {

    /**
     * View mandatory methods. Available to Presenter
     *      Presenter -> View
     *
     * Métodos obrigatórios em View, disponíveis para Presenter
     *      Presenter -> View
     */
    interface RequiredViewOps {
        void showToast(String msg);
        void showAlert(String msg);
        // any other ops
        // qualquer outra operação na UI
    }

    /**
     * Operations offered from Presenter to View
     *      View -> Presenter
     * operações oferecidas ao layer View para comunicação com Presenter
     *      View -> Presenter
     */
    interface PresenterOps{
        void onConfigurationChanged(RequiredViewOps view);
        void onDestroy(boolean isChangingConfig);
        void newNote(String textoNota);
        void deleteNote(Note note);
        // any other ops to be called from View
        // qualquer outra operação a ser chamada pelo View
    }

    /**
     * Operations offered from Presenter to Model
     *      Model -> Presenter
     *
     * operações oferecidas pelo layer Presenter para comunicações com Model
     *      Model -> Presenter
     */
    interface RequiredPresenterOps {
        void onNoteInserted(Note novaNote);
        void onNoteRemoved(Note noteRemovida);
        void onError(String errorMsg);

        // Any other returning operation Model -> Presenter
        // qualquer operação de retorno Model -> Presenter
    }

    /**
     * Model operations offered to Presenter
     *      Presenter -> Model
     *
     * operações oferecidos pelo layer Model para comunicações com Presenter
     *      Presenter -> Model
     */
    interface ModelOps {
        void insertNote(Note note);
        void removeNote(Note note);
        void onDestroy();
        // Any other data operation
        // Qualquer operação referente à dados a ser chamado pelo Presenter
    }
}
