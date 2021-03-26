package lul.myapplication.ui.filme

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import kotlinx.android.synthetic.main.fragment_filme.*
import lul.myapplication.ui.adapter.FilmeAdapter
import lul.myapplication.MyApplication
import lul.myapplication.R

class FilmeFragment : Fragment() {

    private val viewModel: FilmeViewModel by viewModels {
        FilmeViewModelFactory((activity?.application as MyApplication).repository)
    }

    private val adapter by lazy {
        context?.let {
            FilmeAdapter(context = it)
        } ?: throw IllegalArgumentException("Contexto inválido")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getFilmes()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            R.layout.fragment_filme,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configuraRecyclerView()
//        configuraLista()


    }

    private fun getFilmes() {
        viewModel.getFilmes()
        viewModel.fResponse.observe(this, { resposta ->
            if (resposta.isSuccessful) {
                resposta.body()?.let { filmes ->
                    adapter?.add(filmes.filmes)
                    //Log.i("Response", pokemons.pokemons[0].nome)
                }
            } else {
                Log.i("Response", resposta.errorBody().toString())

            }
        })
    }

    private fun configuraRecyclerView() {
        val divisor = DividerItemDecoration(context, LinearLayout.VERTICAL)
        lista_filmes_rv.addItemDecoration(divisor)
        lista_filmes_rv.adapter = adapter
    }

}





