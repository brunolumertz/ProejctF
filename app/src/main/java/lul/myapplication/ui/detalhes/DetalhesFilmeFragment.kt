package lul.myapplication.ui.detalhes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_filme_detalhes.*
import lul.myapplication.*
import lul.myapplication.util.Constantes.Companion.ADD_FILME_MENSAGEM_JAVI
import lul.myapplication.util.Constantes.Companion.ADD_FILME_MENSAGEM_QUEROVER
import lul.myapplication.util.Constantes.Companion.CANCELAR
import lul.myapplication.util.Constantes.Companion.INITIAL_INDEX
import lul.myapplication.util.Constantes.Companion.JAVI
import lul.myapplication.util.Constantes.Companion.QUEROVER
import lul.myapplication.util.Constantes.Companion.SALVAR
import lul.myapplication.models.Filme
import lul.myapplication.models.FilmeDetalhes
import lul.myapplication.util.MyApplication
import retrofit2.Response


class DetalhesFilmeFragment(val filme: Filme) : Fragment() {

    var index = INITIAL_INDEX
    private val lista = arrayOf(
        JAVI,
        QUEROVER
    )

    private val viewModel: DetalhesFilmeViewModel by viewModels {
        DetalhesFilmeViewModelFactory((activity?.application as MyApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_filme_detalhes,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configuraDetalhes()
        configuraBarraAdd(view)
    }

    private fun configuraDetalhes() {
        viewModel.getDetalhes(filme.id)
        viewModel.fResponse.observe(viewLifecycleOwner, {
            if (it.isSuccessful) {
                nome_filme_detalhes.text = it.body()?.tittle
                data_lancamento_detalhes.text = it.body()?.release
                descricao_filme_detalhes.text = it.body()?.overview
                rate_filme_detalhes.text = it.body()?.vote
                configuraPoster(it)
            }
        })
    }

    private fun configuraPoster(filme: Response<FilmeDetalhes>) {
        Glide.with(requireContext())
            .load("https://image.tmdb.org/t/p/w500" + filme.body()?.poster)
            .transform(CenterCrop())
            .into(filme_poster_detalhes)
    }

    private fun configuraDialogFilme(){         // configura pra abrir o dialog
        var selecionaItem = lista[index]

        MaterialAlertDialogBuilder(requireContext(),R.style.MaterialAlertDialog_Theme)
            .setTitle(R.string.adiciona_filme).setSingleChoiceItems(lista, index){ _, which ->
                index = which
                selecionaItem = lista[which]
            }
            .setPositiveButton(SALVAR) { dialog, _ ->
                if (selecionaItem == lista[0]) {
                    adFilmeJaVi()
                } else {
                    addFilmeQueroVer()
                }
                dialog.dismiss()
            }
            .setNeutralButton(CANCELAR) { dialog, _ ->
                dialog.dismiss()
            }.show()

    }

    private fun configuraBarraAdd(view: View){
        val toolbar = view.findViewById<MaterialToolbar>(R.id.topAppBar)
        val navHostFragment = NavHostFragment.findNavController(this)

        NavigationUI.setupWithNavController(toolbar, navHostFragment)
        topAppBar.title = filme.tittle
        topAppBar.setOnMenuItemClickListener{
            when (it.itemId){
                R.id.adiciona_filme ->{
                    configuraDialogFilme()
                    true
                }
                else -> false
            }
        }
    }

    private fun adFilmeJaVi() {
        //verificar se filme já está na lista ou se está na outra lista
        viewModel.salvaListaJaVi(filme)
        Toast.makeText(
            requireContext(),
            ADD_FILME_MENSAGEM_JAVI,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun addFilmeQueroVer() {
        //verificar se filme já está na lista ou se está na outra lista
        viewModel.salvaListaQueroVer(filme)
        Toast.makeText(
            requireContext(),
            ADD_FILME_MENSAGEM_QUEROVER,
            Toast.LENGTH_SHORT
        ).show()
    }
}