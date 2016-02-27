package com.tinmegali.mvp_tutorial;

import com.tinmegali.mvp_tutorial.modelos.Nota;

/**
 * ---------------------------------------------------
 * Created by Tin Megali on 26/02/16.
 * Project: MVP_Tutorial
 * ---------------------------------------------------
 * <a href="http://www.tinmegali.com">tinmegali.com</a>
 * <a href="http://www.github.com/tinmegali>github</a>
 * ---------------------------------------------------
 * Based on <a href="https://github.com/douglascraigschmidt/POSA-15/tree/master/ex/AcronymExpander/src/vandy/mooc">
 * framework MVP</a> developed by
 * <a href="https://github.com/douglascraigschmidt">
 * Dr. Douglas Schmidth</a>
 * ---------------------------------------------------
 *
 * Interface MVP guarda-chuva, contém todos as operações necessárias
 * para funcionamento do padrão
 */
public interface MainMVP {

    /**
     * Métodos obrigatórios em View, disponíveis para Presenter
     *      Presenter -> View
     */
    interface RequiredViewOps {
        void showToast(String msg);
        void showAlert(String msg);
        // qualquer outra operação na UI
    }

    /**
     * operações oferecidas ao layer View para comunicação com Presenter
     *      View -> Presenter
     */
    interface PresenterOps{
        void onConfigurationChanged(RequiredViewOps view);
        void onDestroy(boolean isChangingConfig);
        void novaNota(String textoNota);
        void deletaNota(Nota nota);
        // qualquer outra operação a ser chamada pelo View
    }

    /**
     * operações oferecidas pelo layer Presenter para comunicações com Model
     *      Model -> Presenter
     */
    interface RequiredPresenterOps {
        void onNotaInserida(Nota novaNota);
        void onNotaRemovida(Nota notaRemovida);
        void onError(String errorMsg);
        // qualquer operação de retorno Model -> Presenter
    }

    /**
     * operações oferecidos pelo layer Model para comunicações com Presenter
     *      Presenter -> Model
     */
    interface ModelOps {
        void insereNota(Nota nota);
        void removeNota(Nota nota);
        void onDestroy();
        // Qualquer operação referente à dados a ser chamado pelo Presenter
    }
}
