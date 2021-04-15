package lul.myapplication.ui.minhalista.tabelas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_ja_vi.*
import lul.myapplication.MyApplication
import lul.myapplication.NÃO
import lul.myapplication.R
import lul.myapplication.SIM
import lul.myapplication.models.Filme
import lul.myapplication.ui.adapter.FilmeLocalAdapter
import lul.myapplication.ui.minhalista.MinhaListaViewModel
import lul.myapplication.ui.minhalista.MinhaListaViewModelFactory

class JaViFragment: Fragment() {

    private val viewModel: MinhaListaViewModel by viewModels {
        MinhaListaViewModelFactory((activity?.application as MyApplication).repository)
    }

    private val adapter by lazy {
        context?.let {
            FilmeLocalAdapter(context = it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getListaJaVi()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_ja_vi,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configuraJaVi()
    }

    //configura a lista dos filmes completos
    private fun configuraJaVi() {
        lista_javi.adapter = adapter
        lista_javi.layoutManager = LinearLayoutManager(context)
        adapter?.onClickDeleta = {
            deletaFilmesJaVi(it)
        }
        adapter?.onClickMarca = {
            vaiParaQueroVer(it)
        }
    }

    //pega os filmes da lista salvos localmente
    private fun getListaJaVi() {
        viewModel.filmesJaVi.observe(this, {
            it?.let {
                it.sortedBy { it.tittle }
                adapter?.append(it)
            }
        })
    }

    private fun deletaFilmesJaVi(filme: Filme){
        MaterialAlertDialogBuilder(requireContext(), R.style.CoresFilme)
            .setTitle("Remover ${filme.tittle} ?")
            .setMessage("Deseja remover '${filme.tittle}' da sua lista?")
            .setPositiveButton("SIM") { dialog, _ ->
                viewModel.deletaFilmeJaVi(filme)
                adapter?.deletaFilme(filme)
                dialog.dismiss()
                Toast.makeText(requireContext(),
                    "'${filme.tittle}' removido.",
                    Toast.LENGTH_SHORT).show()
            }
            .setNeutralButton("NÃO") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    private fun vaiParaQueroVer(filme: Filme){
        MaterialAlertDialogBuilder(requireContext(), R.style.CoresFilme)
            .setTitle("Mover ${filme.tittle}?")
            .setMessage("Deseja mover '${filme.tittle}' para Quero Ver?")
            .setPositiveButton(SIM) { dialog, _ ->
                viewModel.salvaListaQueroVer(filme)
                adapter?.deletaFilme(filme)
                dialog.dismiss()
                Toast.makeText(requireContext(),
                    "'${filme.tittle}' movido para Quero Ver.",
                    Toast.LENGTH_SHORT).show()
            }
            .setNeutralButton(NÃO) { dialog, _ ->
                dialog.dismiss()
            }.show()
    }
}