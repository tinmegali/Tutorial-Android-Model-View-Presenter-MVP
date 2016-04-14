package com.tinmegali.mvp_tutorial;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements MainMVP.RequiredViewOps {

    protected final String TAG = getClass().getSimpleName();

    // Responsible to maintain the Objects state
    // during changing configuration

    // Responsável por manter estado dos objetos inscritos
    // durante mudanças de configuração
    private final StateMaintainer mStateMaintainer =
            new StateMaintainer( this.getFragmentManager(), TAG );


    // Presenter operations

    // Operações no Presenter
    private MainMVP.PresenterOps mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startMVPOps();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final EditText notaEditText = (EditText) findViewById(R.id.notaEditText);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.newNote(notaEditText.getText().toString());
            }
        });
    }


    /**
     Initialize and restart the Presenter.
     * This method should be called after {@link Activity#onCreate(Bundle)}
     *
     * Inicia e reinicia o Presenter. Este método precisa ser chamado
     * após {@link Activity#onCreate(Bundle)}
     */
    public void startMVPOps() {
        Log.d(TAG, "startMVPOps()");
        try {
            if ( mStateMaintainer.firstTimeIn() ) {
                Log.d(TAG, "onCreate() called for the first time");
                initialize(this);
            } else {
                Log.d(TAG, "onCreate() called more than once");
                reinitialize(this);
            }
        } catch ( InstantiationException | IllegalAccessException e ) {
            Log.d(TAG, "onCreate() " + e );
            throw new RuntimeException( e );
        }
    }


    /**
     * Initialize relevant MVP Objects.
     * Creates a Presenter instance, saves the presenter in {@link StateMaintainer}
     *
     * Inicializa os objetos relevantes para o MVP.
     * Cria uma instância do Presenter, salva o presenter
     * no {@link StateMaintainer} e informa à instância do
     * presenter que objeto foi criado.
     * @param view      Operações no View exigidas pelo Presenter
     */
    private void initialize( MainMVP.RequiredViewOps view )
            throws InstantiationException, IllegalAccessException{
        Log.d(TAG, "initialize");
        mPresenter = new MainPresenter(view);
        mStateMaintainer.put(MainMVP.PresenterOps.class.getSimpleName(), mPresenter);
    }

    /**
     * Recovers Presenter and informs Presenter that occurred a config change.
     * If Presenter has been lost, recreates a instance
     *
     * Recupera o presenter e informa à instância que houve uma mudança
     * de configuração no View.
     * Caso o presenter tenha sido perdido, uma nova instância é criada
     */
    private void reinitialize( MainMVP.RequiredViewOps view)
            throws InstantiationException, IllegalAccessException {
        mPresenter = mStateMaintainer.get( MainMVP.PresenterOps.class.getSimpleName() );

        if ( mPresenter == null ) {
            Log.w(TAG, "recreating Presenter");
            initialize( view );
        } else {
            mPresenter.onConfigurationChanged(view);
        }
    }


    // Show AlertDialog
    // Exibe AlertDialog
    @Override
    public void showAlert(String msg) {
        // show alert Box
        Log.d(TAG, "showAlert()");
    }

    // Show Toast
    @Override
    public void showToast(String msg) {
        Log.d(TAG, "showToast()");
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
