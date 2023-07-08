package com.example.listadetarefas.activity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.listadetarefas.R;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listadetarefas.adapter.TarefaAdapter;
import com.example.listadetarefas.databinding.ActivityMainBinding;
import com.example.listadetarefas.helper.DbHelper;
import com.example.listadetarefas.helper.RecyclerItemClickListener;
import com.example.listadetarefas.helper.TarefaDAO;
import com.example.listadetarefas.model.Tarefa;

import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    private RecyclerView recyclerView;
    private TarefaAdapter tarefaAdapter;
    private List<Tarefa> listaTarefa = new ArrayList<>();
    private Tarefa tarefaSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        //Configurar o recycler
        recyclerView = findViewById(R.id.recyclerView);

        /*
        DbHelper db = new DbHelper(getApplicationContext());
        ContentValues cv = new ContentValues();
        cv.put("nome", "TesteTeste");
        db.getWritableDatabase().insert("tarefas", null, cv);*/

        //Evento de click
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(),
                recyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        //Recuperar tarefa para edição
                        Tarefa tarefaSelecionada = listaTarefa.get(position);

                        //Enviar tarefa para tela adicionar tarefa
                        Intent intent = new Intent(MainActivity.this, AdicionarTarefaActivity.class);
                        intent.putExtra("tarefaSelecionada", tarefaSelecionada);

                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                        //Recupera tarefa para deletar
                        tarefaSelecionada = listaTarefa.get(position);

                        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);

                        //Configura título e mensagem
                        dialog.setTitle("Confirmar exclusão");
                        dialog.setMessage("Deseja excluir a tarefa: " + tarefaSelecionada.getNomeTarefa() + "?");

                        //Configura os button
                        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());
                                if (tarefaDAO.deletar(tarefaSelecionada)){
                                    carregarListaTarefas();
                                    Toast.makeText(getApplicationContext(), "Sucesso ao excluir tarefa!", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "Erro ao excluir Tarefa!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        dialog.setNegativeButton("Não", null);

                        //Exibir dialog
                        dialog.create();
                        dialog.show();

                    }

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    }
                }));


        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent  = new Intent(getApplicationContext(), AdicionarTarefaActivity.class);
                startActivity(intent);

            }
        });
    }

    public void carregarListaTarefas(){

        //Listar tarefas
        TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());
        listaTarefa = tarefaDAO.listar();


        //Configurar um adapter
        tarefaAdapter = new TarefaAdapter(listaTarefa);

        //configurar Recyclerview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        recyclerView.setAdapter(tarefaAdapter);

    }

    @Override
    protected void onStart() {
        carregarListaTarefas();
        super.onStart();
    }
}