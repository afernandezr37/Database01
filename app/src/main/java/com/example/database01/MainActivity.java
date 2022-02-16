package com.example.database01;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener,AdapterView.OnItemSelectedListener {
    private Button btnCrear;
    private Button btnVer;
    private Button btnEliminar;
    private EditText editNombre;
    private EditText editComentario;
    private EditText txtNombre;
    private EditText txtComentario;

    private Spinner spinComentarios;
    private ArrayAdapter spinnerAdapter;

    private ArrayList<Comentario> lista;
    private Comentario c;

    private MyOpenHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editNombre=(EditText) findViewById(R.id.editNombre);
        editComentario=(EditText)findViewById(R.id.editComentario);
        txtNombre=(EditText) findViewById(R.id.txtNombre);
        txtComentario=(EditText)findViewById(R.id.txtComentario);

        txtNombre.setEnabled(false);
        txtComentario.setEnabled(false);

        btnCrear=(Button)findViewById(R.id.btnCrear);
        btnVer=(Button)findViewById(R.id.btnVer);
        btnEliminar=(Button)findViewById(R.id.btnEliminar);

        btnCrear.setOnClickListener(this);
        btnVer.setOnClickListener(this);
        btnEliminar.setOnClickListener(this);

        db=new MyOpenHelper(this);

        spinComentarios=(Spinner) findViewById(R.id.spinComentarios);
        lista=db.getComments();

        spinnerAdapter=new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,lista);
        spinComentarios.setAdapter(spinnerAdapter);
        spinComentarios.setOnItemSelectedListener(this);


    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnCrear:
                db.insertar(editNombre.getText().toString(),editComentario.getText().toString());
                lista=db.getComments();
                spinnerAdapter=new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,lista);
                spinComentarios.setAdapter(spinnerAdapter);
                editNombre.setText("");
                editComentario.setText("");

                break;
            case R.id.btnVer:
                if(c!=null) {
                    txtNombre.setText(c.getNombre());
                    txtComentario.setText(c.getComentario());
                }
                break;
            case R.id.btnEliminar:
                    if(c!=null) {
                    db.borrar(c.getId());
                    lista = db.getComments();
                    spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, lista);
                    spinComentarios.setAdapter(spinnerAdapter);
                    txtNombre.setText("");
                    txtComentario.setText("");
                    c=null;
                }
                break;

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.spinComentarios) {
            if(lista.size()>0) {
                c = lista.get(position);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
