package lul.myapplication.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.filme_item.view.*
import kotlinx.android.synthetic.main.filme_item.view.filme_poster
import kotlinx.android.synthetic.main.fragment_pesquisa.view.*
import kotlinx.android.synthetic.main.pesquisa_item.view.*
import lul.myapplication.R
import lul.myapplication.models.Filme

class PesquisaAdapter(private val context: Context,
                      private val filmes: MutableList<Filme> = mutableListOf(),
                      var onItemClickListener: (filme: Filme) -> Unit = {}
) : RecyclerView.Adapter<PesquisaAdapter.PesquisaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PesquisaViewHolder {
        val view = LayoutInflater
            .from(context)
            .inflate(R.layout.pesquisa_item, parent, false)
        return PesquisaViewHolder(view)
    }

    override fun onBindViewHolder(holder: PesquisaViewHolder, position: Int) {
        holder.bind(filmes[position])
    }

    override fun getItemCount(): Int = filmes.size

    fun append(filme: List<Filme>) {
        this.filmes.clear()
        this.filmes.addAll(filme)
        notifyDataSetChanged()
    }

    inner class PesquisaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private lateinit var filme: Filme

        private val moviePoster by lazy {
            view.filme_poster
        }

        private val movieTitle by lazy {
            view.title_movie_search
        }

        init {
            view.setOnClickListener {
                if(::filme.isInitialized) {
                    onItemClickListener(filme)
                }
            }
        }

        fun bind(filme: Filme) {
            this.filme = filme
            movieTitle.text = filme.tittle
            Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500"+filme.poster)
                .placeholder(R.drawable.ic_error)
                .into(moviePoster)
        }
    }
}