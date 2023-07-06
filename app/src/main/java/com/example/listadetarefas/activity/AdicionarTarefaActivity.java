package com.example.listadetarefas.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.listadetarefas.R;
import com.example.listadetarefas.databinding.ActivityAdicionarTarefaBinding;
import com.example.listadetarefas.databinding.ActivityMainBinding;

public class AdicionarTarefaActivity extends AppCompatActivity {

    private ActivityAdicionarTarefaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_tarefa);

        binding = ActivityAdicionarTarefaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_adicionar_tarefa, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.buttonSalvar){
            Toast.makeText(AdicionarTarefaActivity.this,
                           "Botão salvar",
                           Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}