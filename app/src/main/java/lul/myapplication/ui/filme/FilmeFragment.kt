package lul.myapplication.ui.filme

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_filme.*
import lul.myapplication.util.MyApplication
import lul.myapplication.R
import lul.myapplication.models.Filme
import lul.myapplication.ui.adapter.FilmeAdapter
import lul.myapplication.ui.detalhes.DetalhesFilmeFragment

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
        configuraLista()
    }

    private fun configuraLista() {
        adapter?.onItemClickListener = {
            goToDetails(it)
        }
        lista_filmes_rv.adapter = adapter
        lista_filmes_rv.layoutManager = LinearLayoutManager(context)
    }

    private fun goToDetails(filme: Filme) {
        val details = DetalhesFilmeFragment(filme)
        val fragmentManager = activity?.supportFragmentManager
        val transaction = fragmentManager?.beginTransaction()
        transaction?.replace(R.id.fragment_discover_container, details)
        transaction?.addToBackStack(null)
        transaction?.commit()
    }

    private fun getFilmes() {
        viewModel.getFilmes()
        viewModel.fResponse.observe(this, { resposta ->
            if (resposta.isSuccessful) {
                resposta.body()?.let { filmes ->
                    adapter?.add(filmes.filmes)
                }
            } else {
                Log.i("Response", resposta.errorBody().toString())

            }
        })
    }

}
