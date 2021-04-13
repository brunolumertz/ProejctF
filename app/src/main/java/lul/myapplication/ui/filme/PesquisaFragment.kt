package lul.myapplication.ui.filme

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_pesquisa.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import lul.myapplication.MyApplication
import lul.myapplication.PESQUISA_DELAY
import lul.myapplication.R
import lul.myapplication.models.Filme
import lul.myapplication.ui.Resource
import lul.myapplication.ui.adapter.PesquisaAdapter
import lul.myapplication.ui.detalhes.DetalhesFilmeFragment

class PesquisaFragment : Fragment() {

    private val viewModel: FilmeViewModel by viewModels {
        FilmeViewModelFactory((activity?.application as MyApplication).repository)
    }

    private val adapter by lazy {
        context?.let {
            PesquisaAdapter(context = it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            R.layout.fragment_pesquisa,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configAdapterRecyclerView()

        //Delay com coroutines, no search
        var job: Job? = null
        pesquisa_filme.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(PESQUISA_DELAY)
                editable?.let {
                    if (editable.toString().isNotEmpty()) {
                        viewModel.getSearchMovie(editable.toString())
                    }
                }
            }
        }

        viewModel.fPesquisaResponse.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { filmes ->
                        filmes.filmes.let { it1 -> adapter?.append(it1) }
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Toast.makeText(
                            activity, "Erro!: $message", Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun hideProgressBar() {
        barra_progresso.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        barra_progresso.visibility = View.VISIBLE
    }

    private fun configAdapterRecyclerView() {
        adapter?.onItemClickListener = {
            goToMovieDetails(it)
        }
        rv_pesquisa.adapter = adapter
        rv_pesquisa.layoutManager = GridLayoutManager(context, 3)
    }

    private fun goToMovieDetails(filme: Filme) {
        val details = DetalhesFilmeFragment(filme)
        val fragmentManager = activity?.supportFragmentManager
        val transaction = fragmentManager?.beginTransaction()
        transaction?.replace(R.id.fragment_pesquisa_container, details)
        transaction?.addToBackStack(null)
        transaction?.commit()
    }

//    private val controller by lazy {
//        findNavController()
//    }
//
//    private fun goToMovieDetails(filme: Filme) {
//        val direction = PesquisaFragment.action_app_bar_search_to_filme_detalhes(filme)
//        controller.navigate(direction)
//    }
}