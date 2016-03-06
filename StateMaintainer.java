package com.tinmegali.mvp_tutorial;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * ---------------------------------------------------
 * Created by Tin Megali on 26/02/16.
 * Project: MVP_Tutorial
 * ---------------------------------------------------
 * <a href="http://www.tinmegali.com">tinmegali.com</a>
 * <a href="http://www.github.com/tinmegali>github</a>
 * ---------------------------------------------------
 *
 */
public class StateMaintainer {
    protected final String TAG = getClass().getSimpleName();

    private final String mStateMaintenerTag;
    private final WeakReference<FragmentManager> mFragmentManager;
    private StateMngFragment mStateMaintainerFrag;

    /**
     * Constructor
     * @param fragmentManager       FragmentManager reference
     * @param stateMaintainerTAG    the TAG used to insert the state maintainer fragment
     *
     *
     * @param fragmentManager       repassa uma referência do FragmentManager
     * @param stateMaintainerTAG      a TAG utilizada para inserir o fragmento responsável
     *                              por manter os objetos "vivos"
     */
    public StateMaintainer(FragmentManager fragmentManager, String stateMaintainerTAG) {
        mFragmentManager = new WeakReference<>(fragmentManager);
        mStateMaintenerTag = stateMaintainerTAG;
    }

    /**
     * Create the state maintainer fragment
     * @return  true: the frag was created for the first time
     *          false: recovering the object
     *
     * cria o fragmento responsável por armazenar o objetos
     * @return  true: criou o framentos e rodou pela primeira vez
     *          false: o objeto já foi criado, portanto é apenas recuperado
     */
    public boolean firstTimeIn() {
        try {
            // Recovering the reference
            // Recuperando referência
            mStateMaintainerFrag = (StateMngFragment)
                    mFragmentManager.get().findFragmentByTag(mStateMaintenerTag);

            // Creating a new RetainedFragment
            // Criando novo RetainedFragment
            if (mStateMaintainerFrag == null) {
                Log.d(TAG, "Creating a new RetainedFragment " + mStateMaintenerTag);
                mStateMaintainerFrag = new StateMngFragment();
                mFragmentManager.get().beginTransaction()
                        .add(mStateMaintainerFrag, mStateMaintenerTag).commit();
                return true;
            } else {
                Log.d(TAG, "Returns a existent retained fragment existente " + mStateMaintenerTag);
                return false;
            }
        } catch (NullPointerException e) {
            Log.w(TAG, "Erro firstTimeIn()");
            return false;
        }
    }


    /**
     * Insert Object to be preserved during configuration change
     * @param key   Object's TAG reference
     * @param obj   Object to maintain
     *
     * Insere objeto a serem presenrvados durante mudanças de configuração
     */
    public void put(String key, Object obj) {
        mStateMaintainerFrag.put(key, obj);
    }

    /**
     * Insert Object to be preserved during configuration change
     * Uses the Object's class name as a TAG reference
     * Should only be used one time by type class
     * @param obj   Object to maintain
     *
     * Insere objeto a serem presenrvados durante mudanças de configuração.
     * Utiliza a classe do Objeto como referência futura.
     * Só deve ser utilizado somente uma vez por classe, caso contrário haverá
     * possíveis conflitos na recuperação dos dados
     */
    public void put(Object obj) {
        put(obj.getClass().getName(), obj);
    }


    /**
     * Recovers saved object
     * @param key   TAG reference
     * @param <T>   Class type
     * @return      Objects
     *
     * Recupera o objeto salvo
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String key)  {
        return mStateMaintainerFrag.get(key);

    }

    /**
     * Verify the object existence
     * @param key   Obj TAG
     *
     * Verifica a existência de um objeto com a chave fornecida
     */
    public boolean hasKey(String key) {
        return mStateMaintainerFrag.get(key) != null;
    }


    /**
     * Save and manages objects that show be preserved
     * during configuration changes.
     *
     * Armazena e administra os objetos que devem ser preservados
     * durante mudanças de configuração.
     */
    public static class StateMngFragment extends Fragment {
        private HashMap<String, Object> mData = new HashMap<>();

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // Grants that the frag will be preserved

            // Garante que o Fragmento será preservado
            // durante mudanças de configuração
            setRetainInstance(true);
        }

        /**
         * Insert objects
         * @param key   reference TAG
         * @param obj   Object to save
         *
         * Insere objetos no hashmap
         */
        public void put(String key, Object obj) {
            mData.put(key, obj);
        }

        /**
         * Insert obj using class name as TAG
         * @param object    obj to save
         *
         * Insere objeto utilizando o nome da classe como referência
         */
        public void put(Object object) {
            put(object.getClass().getName(), object);
        }

        /**
         * Recover obj
         * @param key   reference TAG
         * @param <T>   Class
         * @return      Obj saved
         *
         * Recupera objeto salvo no hashmap
         */
        @SuppressWarnings("unchecked")
        public <T> T get(String key) {
            return (T) mData.get(key);
        }
    }

}
