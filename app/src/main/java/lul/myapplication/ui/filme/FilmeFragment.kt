package lul.myapplication.ui.filme

import android.app.SearchManager
import android.content.Context.SEARCH_SERVICE
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_filme.*
import lul.myapplication.MyApplication
import lul.myapplication.QUERY_LENGTH
import lul.myapplication.R
import lul.myapplication.SEARCH_HINT
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


//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        super.onCreateOptionsMenu(menu, inflater)
//        inflater.inflate(R.menu.menu_botoes, menu)
//        val searchManager = activity?.getSystemService(SEARCH_SERVICE) as SearchManager
//        val searchView: SearchView = menu.findItem(R.id.app_bar_search).actionView as SearchView
//        val searchMenuItem = menu.findItem(R.id.app_bar_search)
//
//        configuraPesquisaView(searchManager, searchView, searchMenuItem)
//    }
//
//    private fun configuraPesquisaView(searchManager: SearchManager, searchView: SearchView, searchMenuItem: MenuItem){
//
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
//        searchView.queryHint = SEARCH_HINT
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                pesquisa(query)
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                pesquisa(newText)
//                return true
//            }
//        })
//        searchMenuItem.icon.setVisible(false, false)
//    }
//
//    private fun pesquisa(query : String?){
//        if (query?.length!! >= QUERY_LENGTH) {
//            viewModel.getPesquisa(query)
//            viewModel.fPesquisaResponse.observe(this@FilmeFragment, {
//                if (it.isSuccessful) {
//                    it.body()?.let { result ->
//                        lista_filmes_rv.scrollToPosition(0)
//                        adapter?.addPesquisa(result.filmes)
//                    }
//                }
//            })
//        }
//    }


}
