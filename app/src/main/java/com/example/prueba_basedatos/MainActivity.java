package com.example.prueba_basedatos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText et_nombre, et_puntuacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_nombre = (EditText)findViewById(R.id.txt_nombre);
        et_puntuacion = (EditText)findViewById(R.id.txt_puntuacion);

    }

    //metodo para dar de alta
    public void Registrar (View view){
        AdminSQLiteOpenHelper BBDD = new AdminSQLiteOpenHelper(this, "RankingJugadores", null, 1);
        //abrir la base de datos modo escritura y lectura
        SQLiteDatabase BaseDeDatos = BBDD.getWritableDatabase();

        String nombre = et_nombre.getText().toString();
        String puntuacion = et_puntuacion.getText().toString();

        if ( !nombre.isEmpty() && !puntuacion.isEmpty() ){
            ContentValues registro = new ContentValues();
            //guardar en la base de datos los valores
            registro.put("nombre", nombre);
            //registro.put("puntuacion" score);

            //insertar valores en la tabla ranking
            BaseDeDatos.insert("ranking", null, registro);

            BaseDeDatos.close();

            et_nombre.setText("");
            et_puntuacion.setText("");

            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(this, "Debes rellenar el nombre", Toast.LENGTH_SHORT).show();
        }
    }

    //metodo para consultar
    public void Buscar (View view){
        AdminSQLiteOpenHelper BBDD = new AdminSQLiteOpenHelper(this, "ranking", null,1);

        //apertura modo lectura escritura
        SQLiteDatabase BaseDeDatos = BBDD.getWritableDatabase();

        String nombre = et_nombre.getText().toString();

        if (!nombre.isEmpty()){
            //aplicar select
            Cursor fila = BaseDeDatos.rawQuery("select nombre, puntuacion from ranking where nombre =" + nombre, null);

            //revisar si la consulta contiene valores y mostrarlos
            if (fila.moveToFirst()){
                et_nombre.setText(fila.getString(0));
                et_puntuacion.setText(fila.getString(1));
                BaseDeDatos.close();
            }else{
                Toast.makeText(this, "no existe el jugador", Toast.LENGTH_SHORT).show();
                BaseDeDatos.close();
            }

        }else {
            Toast.makeText(this, "Debes rellenar el nombre", Toast.LENGTH_SHORT).show();
        }

    }

    //metodo para eliminar un jugador

    public void Eliminar(View view){
        AdminSQLiteOpenHelper BBDD = new AdminSQLiteOpenHelper(this, "ranking", null,1);

        //apertura modo lectura escritura
        SQLiteDatabase BaseDeDatos = BBDD.getWritableDatabase();

        //recuperar el nombre del jugador que se va a eliminar
        String nombre = et_nombre.getText().toString();

        if (!nombre.isEmpty()){

            int cantidad = BaseDeDatos.delete("ranking", "nombre=" + nombre,null);
            BaseDeDatos.close();

            //limpiar variables
            et_nombre.setText("");
            et_puntuacion.setText("");

            if (cantidad == 1){
                Toast.makeText(this, "Jugador eliminado correctamente", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "El jugador no existe", Toast.LENGTH_SHORT).show();
            }



        }else{
            Toast.makeText(this, "Debes rellenar el nombre", Toast.LENGTH_SHORT).show();
        }
    }

    //metodo para modificar un jugador
    public void Modificar (View view){
        AdminSQLiteOpenHelper BBDD = new AdminSQLiteOpenHelper(this, "ranking", null,1);

        //apertura modo lectura escritura
        SQLiteDatabase BaseDeDatos = BBDD.getWritableDatabase();

        //recuperar el nombre del jugador que se va a modificar
        String nombre = et_nombre.getText().toString();
        String puntuacion = et_puntuacion.getText().toString();

        if (!nombre.isEmpty() && !puntuacion.isEmpty()){
            ContentValues registro = new ContentValues();
            registro.put("nombre",nombre);
            registro.put("puntuacion",puntuacion);

            int cantidad = BaseDeDatos.update("ranking", registro, "nombre=" + nombre, null);
            BaseDeDatos.close();

            if (cantidad == 1){
                Toast.makeText(this, "Jugador modificado correctamente", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "El jugador no existe", Toast.LENGTH_SHORT).show();
            }


        }else{
            Toast.makeText(this, "Debes rellenar todos los campos", Toast.LENGTH_SHORT).show();
        }

    }


}
