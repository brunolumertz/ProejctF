package lul.myapplication.ui.detalhes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import kotlinx.android.synthetic.main.fragment_filme_detalhes.*
import lul.myapplication.MyApplication
import lul.myapplication.R
import lul.myapplication.models.Filme
import lul.myapplication.ui.filme.FilmeViewModel
import lul.myapplication.ui.filme.FilmeViewModelFactory

class DetalhesFilmeFragment(val filme: Filme) : Fragment(){

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
    }

    private fun configuraDetalhes() {
        viewModel.getDetalhes(filme.id)
        viewModel.fResponse.observe(viewLifecycleOwner,{
            if(it.isSuccessful){
                nome_filme_detalhes.text = it.body()?.tittle
            }
        })
    }
}