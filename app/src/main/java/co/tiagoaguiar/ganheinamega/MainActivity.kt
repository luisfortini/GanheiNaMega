package co.tiagoaguiar.ganheinamega

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Setar layout nessa Atividade
        setContentView(R.layout.activity_main)

        //Buscar objetos e ter a referência deles
        val editText: EditText = findViewById(R.id.edit_number)
        val txtResult: TextView = findViewById(R.id.txt_result)
        val btnGenerate: Button = findViewById(R.id.btn_generate)

        //Definir SharedPreference
        prefs = getSharedPreferences("db", Context.MODE_PRIVATE)
        val result = prefs.getString("result", null)

        //Verificação de valor nulo de forma mais simples para substituir o If
        result?.let {
            txtResult.text = "Última Aposta: $result"
        }
        /*
        if(result!=null) {
             txtResult.text = "Última Aposta: $result"
        }
        */

        //Adicionar o evendo de click ao botão já declarando o bloco de código
        btnGenerate.setOnClickListener {

            val text = editText.text.toString()
            numberGenerator(text, txtResult)
        }

    }

    private fun numberGenerator(text: String, txtResult: TextView) {

        //Verificar a falha antes para reduzir if's

        //Verifica se input é vazio
        if (text.isEmpty()) {

            Toast.makeText(this, "Informe um número entre 6 e 15", Toast.LENGTH_LONG).show()
            return
        }

        //Verifica se quantidade digitada é valida
        val qtde = text.toInt()

        if (qtde < 6 || qtde > 15) {

            Toast.makeText(this, "Informe um número entre 6 e 15", Toast.LENGTH_LONG).show()
            return
        }

        //Execução caso não haja falha
        val numbers = mutableSetOf<Int>()
        val random = Random()

        while (true) {
            val number = random.nextInt(60) // Gera número inteiro de 0 à 59
            numbers.add(number + 1)

            if (numbers.size == qtde) {
                break
            }
        }

        txtResult.text = numbers.joinToString(" - ")

        //Definir a SharedPreferente
        val editor = prefs.edit()
        editor.putString("result", txtResult.text.toString())
        editor.apply()

        /*
        Outra forma de definir Shared
        prefs.edit().apply{
             putString("result",txtResult.text.toString())
             apply()
        }
        */

    }
}
/* codigo de validação (If /else) antes de refatorar
        //Validar quando o campo é vazio
        if(text.isNotEmpty()){

            val qtde = text.toInt()

            //Validar se o campo informado é entre 6 e 15
            if(qtde>= 6 && qtde <=15){

                val numbers = mutableSetOf<Int>()
                val random = Random()

                while(true){
                    val number = random.nextInt(60) // Gera número inteiro de 0 à 59
                    numbers.add(number + 1)

                    if(numbers.size == qtde){
                        break
                    }
                }

                txtResult.text = numbers.joinToString(" - ")

            } else{

                Toast.makeText(this,"Informe um número entre 6 e 15", Toast.LENGTH_LONG).show()
            }

        } else{
            Toast.makeText(this,"Informe um número entre 6 e 15", Toast.LENGTH_LONG).show()
        } */
