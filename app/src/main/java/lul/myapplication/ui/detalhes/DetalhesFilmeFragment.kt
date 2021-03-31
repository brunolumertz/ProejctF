package lul.myapplication.ui.detalhes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import kotlinx.android.synthetic.main.fragment_filme_detalhes.*
import lul.myapplication.MyApplication
import lul.myapplication.R
import lul.myapplication.models.Filme
import lul.myapplication.models.FilmeDetalhes
import retrofit2.Response


class DetalhesFilmeFragment(val filme: Filme) : Fragment() {

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
}